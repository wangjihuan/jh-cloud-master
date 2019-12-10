package com.jh;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class JhAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(JhAdminApplication.class, args);
    }

}
