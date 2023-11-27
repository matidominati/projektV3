package pl.matidominati.projektV3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProjektV3Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjektV3Application.class, args);
	}

}
