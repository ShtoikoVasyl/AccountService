package edu.shtoiko.accountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

@EnableDiscoveryClient
@SpringBootApplication
public class AccountServiceApp {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(AccountServiceApp.class, args);
    }
}
