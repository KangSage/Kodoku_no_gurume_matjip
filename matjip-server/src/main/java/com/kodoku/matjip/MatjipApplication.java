package com.kodoku.matjip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;

@Slf4j
@SpringBootApplication(exclude = {ThymeleafAutoConfiguration.class})
public class MatjipApplication {

  public static void main(String[] args) {

    try {
      String ipAddr = InetAddress.getLocalHost().getHostAddress();
      log.debug(
          "You can now view project in the browser.\n"
              + "  Local:            http://localhost:8080\n"
              + "  On Your Network:  http://{}:8080",
          ipAddr);
    } catch (Exception e) {
      log.error(e.toString());
    }
    SpringApplication.run(MatjipApplication.class, args);
  }
}
