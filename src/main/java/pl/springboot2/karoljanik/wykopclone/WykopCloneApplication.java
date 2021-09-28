package pl.springboot2.karoljanik.wykopclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import pl.springboot2.karoljanik.wykopclone.configurations.SwaggerConfiguration;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class WykopCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(WykopCloneApplication.class, args);
    }

}
