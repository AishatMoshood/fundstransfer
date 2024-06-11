package com.dot.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
@Slf4j
@SpringBootApplication(
        scanBasePackages = {
                "com.dot.ai"
        }
)
public class FundsTransferApplication {

    public static void main(String[] args) {
        SpringApplication.run(FundsTransferApplication.class, args);
        System.out.println("Dot Ai funds transfer application started successfully.");
    }

}
