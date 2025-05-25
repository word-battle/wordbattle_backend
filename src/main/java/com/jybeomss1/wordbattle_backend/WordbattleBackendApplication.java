package com.jybeomss1.wordbattle_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WordbattleBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordbattleBackendApplication.class, args);
	}

}
