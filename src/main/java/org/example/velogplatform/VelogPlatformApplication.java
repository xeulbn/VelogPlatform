package org.example.velogplatform;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class VelogPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(VelogPlatformApplication.class, args);
    }

}
