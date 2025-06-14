package com.imshiivam.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import kong.unirest.CookieSpecs;
import kong.unirest.Unirest;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
    	
    	Unirest.config().cookieSpec(CookieSpecs.STANDARD);
    	Unirest.config().concurrency(1000, 100);
        SpringApplication.run(MainApplication.class, args);
    }
}
