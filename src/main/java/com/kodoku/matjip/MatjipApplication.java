package com.kodoku.matjip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class MatjipApplication {
    public static void main(String[] args) {
        try {
            String ipAddr = InetAddress.getLocalHost().getHostAddress();
            log.debug("Current Host IP Address : {}", ipAddr);
        } catch (UnknownHostException e) {
            log.error(e.toString());
        }
        SpringApplication.run(MatjipApplication.class, args);
    }
}
