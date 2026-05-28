package br.com.antonio.league_manger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AutoclipsLolApplication {

    public static void main(String[] args)  {
        SpringApplication.run(AutoclipsLolApplication.class, args);

    }

}
