package com.datazuul.eazy.bookscanner;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class App {

  public static void main(String[] args) {
    new SpringApplicationBuilder(App.class)
            .headless(false)
            .web(WebApplicationType.NONE)
            .run(args);
  }
}
