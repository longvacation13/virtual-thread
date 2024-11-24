package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import com.example.demo.util.ThreadUtil;


@RestController
@RequestMapping("/test")
public class TestController {


    private static final int TASK_COUNT = 1000;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello world");
    }

    @GetMapping("/platform")
    public ResponseEntity<String> testPlatformThreads() throws InterruptedException {
        Instant start = Instant.now();

        // 플랫폼 스레드 풀 생성
        ExecutorService executor = Executors.newFixedThreadPool(100);
        IntStream.range(0, TASK_COUNT).forEach(i -> executor.submit(ThreadUtil::performTask));

        // 스레드 종료 및 작업 완료 대기
        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.sleep(10);
        }

        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        return ResponseEntity.ok("Platform Threads completed in: " + duration.toMillis() + " ms");
    }

    @GetMapping("/virtual")
    public ResponseEntity<String> testVirtualThreads() throws InterruptedException {
        Instant start = Instant.now();

        System.out.println("\nBefore starting virtual threads:");
        ThreadUtil.printMemoryUsage(); // 작업 전 메모리 사용량

        // Virtual Thread Executor 생성
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, TASK_COUNT).forEach(i -> executor.submit(ThreadUtil::performTask));
        }

        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);

        System.out.println("\nAfter finishing virtual threads:");
        ThreadUtil.printMemoryUsage(); // 작업 후 메모리 사용량

        // 강제로 GC 호출 후 메모리 확인
        System.gc();
        System.out.println("\nAfter garbage collection:");
        ThreadUtil.printMemoryUsage();


        return ResponseEntity.ok("Virtual Threads completed in: " + duration.toMillis() + " ms");
    }



}