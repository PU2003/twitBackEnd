package com.twitter.services;

import com.twitter.exceptions.EmailAlreadyTakenException;
import com.twitter.models.ApplicationUser;
import com.twitter.models.RegistrationObject;
import com.twitter.models.Role;
import com.twitter.repositories.RoleRepository;
import com.twitter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;



    public ResponseEntity<String> registerUser(RegistrationObject ro){

        ApplicationUser user = new ApplicationUser();
        String name = null;

        boolean nameTaken = true;

        String tempName = "";
        while (nameTaken){
            tempName = generateUsername(name);

            if(userRepo.findByUsername(tempName).isEmpty()){
                nameTaken = false;
            }
        }

        user.setUsername(tempName);
        user.setDateofBirth(ro.getDob());
        user.setEmail(ro.getEmail());
        user.setFirstName(ro.getFirstName());
        user.setLastName(ro.getLastName());
        Set<Role> roles = new HashSet<>();
        if(roleRepo.findByAuthority("USER").isPresent()){
            roles.add(roleRepo.findByAuthority("USER").get());
        }else{
            return new ResponseEntity<String>("User can't be created with this role", HttpStatus.BAD_REQUEST);
        }

        user.setAuthorities(roles);

        try {
            ApplicationUser save = userRepo.save(user);
            if(save.getUserId()!=null){
                return new ResponseEntity<String>("User created successfully", HttpStatus.OK);
            }else{
                return new ResponseEntity<String>("User not saved", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new EmailAlreadyTakenException();
        }
    }
    private String generateUsername(String name){
        long generatedNumber = (long) Math.floor(Math.random() * 1_000_000_000);
        return name+generatedNumber;
    }
}
