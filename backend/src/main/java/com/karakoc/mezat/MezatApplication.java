package com.karakoc.mezat;

import com.karakoc.mezat.user.User;
import com.karakoc.mezat.user.UserRepository;
import com.karakoc.mezat.user.roles.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

@SpringBootApplication
public class MezatApplication {

	public static void main(String[] args) {
		SpringApplication.run(MezatApplication.class, args);
	}


	@Component
	@AllArgsConstructor
	public class MyCommandLineRunner implements CommandLineRunner {

		private final UserRepository userRepository;
		@Override
		public void run(String... args) throws Exception {

			User user = new User();
			user.setId("emirhan");
			user.setUserRole(UserRole.ROLE_ADMIN);
			user.setToken("1");
			user.setUsername("admin");
			user.setLastname("KARAKOC");
			user.setMail("admin@muzayede.com");
			user.setPassword("admin");
			userRepository.save(user);

			User user2 = new User();
			user2.setId("emirhan2");
			user2.setUserRole(UserRole.ROLE_USER);
			user2.setToken("5");
			user2.setUsername("user");
			user2.setLastname("KARAKOC");
			user2.setMail("user@muzayede.com");
			user2.setPassword("user");
			userRepository.save(user);

			}
		}
}
