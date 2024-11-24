package com.example.demo.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ThreadUtil {

    public void performTask() {
        // 간단한 작업 시뮬레이션
        try {
            Thread.sleep(10); // 10ms 대기
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void printMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long allocatedMemory = runtime.totalMemory() - runtime.freeMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long maxMemory = runtime.maxMemory();

        System.out.printf("Allocated Memory = %d KiB, Total Memory = %d KiB, Free Memory = %d KiB, Max Memory = %d KiB%n",
                allocatedMemory / 1024, totalMemory / 1024, freeMemory / 1024, maxMemory / 1024);
    }
}