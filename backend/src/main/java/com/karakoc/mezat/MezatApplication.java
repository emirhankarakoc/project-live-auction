package com.karakoc.mezat;

 import com.karakoc.mezat.user.User;
import com.karakoc.mezat.user.UserRepository;
import com.karakoc.mezat.user.roles.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.TimeZone;
import java.util.UUID;

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
			user.setFirstname("EMIRHAN");
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
			userRepository.save(user2);

			User user3 = new User();
			user3.setId("emirhan3");
			user3.setUserRole(UserRole.ROLE_USER);
			user3.setToken("6");
			user3.setUsername("user2");
			user3.setLastname("SOFTWARE_ENG_DERS");
			user3.setMail("user2@muzayede.com");
			user3.setPassword("user2");
			userRepository.save(user3);

			}
		}
}
