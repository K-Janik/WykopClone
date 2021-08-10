package pl.springboot2.karoljanik.wykopclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WykopCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(WykopCloneApplication.class, args);
    }

}
