package com.twitter;

import com.twitter.models.ApplicationUser;
import com.twitter.models.Role;
import com.twitter.repositories.RoleRepository;
import com.twitter.repositories.UserRepository;
import com.twitter.services.UserService;
import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;

@SpringBootApplication
public class TwitterBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepo, UserService userService){

		return args -> {
           roleRepo.save(new Role(1,"USER"));
		   /* ApplicationUser u = new ApplicationUser();

			u.setFirstName("Prachi");
			u.setLastName("Pushkar");

			userService.registerUser(u);
		    */

		   /*ApplicationUser u = new ApplicationUser();
			u.setFirstName("Prachi");
			u.setLastName("Pushkar");
			HashSet<Role> roles = new HashSet<>();
			roles.add(roleRepo.findByAuthority("USER").get());
			u.setAuthorities(roles);
			userRepo.save(u);
		    */
		};
	}
}
