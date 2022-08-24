package com.comarchtask.moneyexchangecalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MoneyExchangeCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyExchangeCalculatorApplication.class, args);
    }

}
