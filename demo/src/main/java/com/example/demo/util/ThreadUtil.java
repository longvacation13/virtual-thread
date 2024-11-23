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
}