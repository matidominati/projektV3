package pl.matidominati.GitHubApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GitHubApp {

	public static void main(String[] args) {
		SpringApplication.run(GitHubApp.class, args);
	}

}
