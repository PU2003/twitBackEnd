package com.twitter;

import com.twitter.config.RSAKeyProperties;
import com.twitter.models.ApplicationUser;
import com.twitter.models.Role;
import com.twitter.repositories.RoleRepository;
import com.twitter.repositories.UserRepository;
import com.twitter.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
@EnableConfigurationProperties(RSAKeyProperties.class)
@Slf4j
//@EnableSwagger2
public class TwitterBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepo, UserService userService,UserRepository userRepository, PasswordEncoder encoder){

		return args -> {
			log.info("Command Line method ........");
			Optional<Role> user = roleRepo.findByAuthority("USER");
			if(user.isEmpty()){
				Role r = roleRepo.save(new Role("USER"));
			}



//			Set<Role> roles = new HashSet<>();
//			roles.add(r);
//
//			ApplicationUser u = new ApplicationUser();
//			u.setAuthorities(roles);
//			u.setFirstName("meniti");
//			u.setLastName("vinthao");
//			u.setEmail("meniti9382@vinthao.com");
//            u.setUsername("meniti9382@vinthao");
//			u.setPhone("2534545454");
//			u.setPassword(encoder.encode("password"));
//			u.setEnabled(true);
//
//			userRepository.save(u);
		};
	}
}
