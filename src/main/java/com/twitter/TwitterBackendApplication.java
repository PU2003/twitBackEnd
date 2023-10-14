package com.twitter;

import com.twitter.config.RSAKeyProperties;
import com.twitter.models.ApplicationUser;
import com.twitter.models.Role;
import com.twitter.repositories.RoleRepository;
import com.twitter.repositories.UserRepository;
import com.twitter.services.UserService;
import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableConfigurationProperties(RSAKeyProperties.class)
public class TwitterBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepo, UserService userService,UserRepository userRepository, PasswordEncoder encoder){

		return args -> {
           Role r = roleRepo.save(new Role(1,"USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(r);

			ApplicationUser u = new ApplicationUser();
			u.setAuthorities(roles);
			u.setFirstName("gavox");
			u.setLastName("mugadget");
			u.setEmail("gavox10671@mugadget.com");
            u.setUsername("gavox10671@mugadget");
			u.setPhone("2534545454");
			u.setPassword(encoder.encode("password"));
			u.setEnabled(true);

			userRepository.save(u);
		};
	}
}
