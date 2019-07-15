package com.kodoku.matjip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@PropertySources({
    @PropertySource("classpath:constants.yml")
})
@SpringBootApplication
public class MatjipApplication {

//    @Value("server.host.name")
//    private String SERVER_HOST_NAME;

    public static void main(String[] args) {
        try {
            String IpAddr = InetAddress.getLocalHost().getHostAddress();
            log.info("Current Host IP Address : {}", IpAddr);
//            log.info("Current active host name : {}");
        } catch (UnknownHostException e) {
            log.error(e.toString());
        }
        SpringApplication.run(MatjipApplication.class, args);
    }
}
