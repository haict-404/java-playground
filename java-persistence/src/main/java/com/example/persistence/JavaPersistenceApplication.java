package com.example.persistence;

import com.example.persistence.service.AccountService;
import com.example.persistence.service.PhoneService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JavaPersistenceApplication {

  public static void main(String[] args) {
    SpringApplication.run(JavaPersistenceApplication.class, args);
  }

  @Bean
  CommandLineRunner runner(AccountService accountService, PhoneService phoneService) {
    return args -> {
      System.out.println("Java Persistence Application started successfully!");
      //test AOP, transaction propagation
//      accountService.updateAccount();
      //test N+1 problem
//      testNPlus1Problem(phoneService);
      //test audit entity

    };
  }

  /**
   * This method demonstrates the N+1 problem in a multithreaded environment.
   * It fetches phones with and without join using CompletableFuture.
   * The first fetch is expected to be fast, while the second one is expected to be slow due to the N+1 problem.
   * Sample with 600.000 phones in the database.
   * @param phoneService the PhoneService instance
   */
  public void testNPlus1Problem(PhoneService phoneService) {
    try (ExecutorService executorService = Executors.newCachedThreadPool()) {
      /* Fetch phones with join
       This will fetch all phones and account info in one query
       -> total queries: 1
      */
      var f1 = CompletableFuture.runAsync(() -> {
        System.out.println(
            "Fetching phones with join... in thread " + Thread.currentThread().getName());
        long startTime = System.currentTimeMillis();
        System.out.println(phoneService.getPhonesWithJoin().size());
        System.out.println(
            "Time taken to fetch with join: " + (System.currentTimeMillis() - startTime) + "ms");
      }, executorService);

      /* Fetch phones without join -> N+1 problem
      This will fetch all phones in one query -> 1 query
      Then for each phone, it will fetch the account info in a separate query -> N queries (600.000 phones)
      -> total queries = 1 + N = 600.001 queries
       */
      var f2 = CompletableFuture.runAsync(() -> {
        System.out.println(
            "Fetching phones without join... in thread " + Thread.currentThread().getName());
        long startTime = System.currentTimeMillis();
        System.out.println(phoneService.getPhonesWithoutJoin().size());
        System.out.println(
            "Time taken to fetch without join: " + (System.currentTimeMillis() - startTime) + "ms");
      }, executorService);

      try {
        f1.get(20, TimeUnit.SECONDS);
        f2.get(20, TimeUnit.SECONDS);
      } catch (TimeoutException e) {
        //timeout will occur if the task takes more than 20 seconds, most likely due to N+1 problem
        System.out.println("Timeout occurred: " + e.getMessage());
        f1.cancel(true);
        f2.cancel(true);
      }  catch (Exception e) {
        throw new RuntimeException(e);
      } finally {
        //shutdown the executor service
        executorService.shutdownNow();
        System.out.println("Executor service shutdown.");
      }
    }
  }
}
