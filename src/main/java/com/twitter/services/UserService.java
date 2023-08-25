package com.twitter.services;

import com.twitter.exceptions.EmailAlreadyTakenException;
import com.twitter.exceptions.UserDoesNotExistException;
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

    public ApplicationUser getUserByUsername(String username){

        return userRepo.findByUsername(username).orElseThrow(UserDoesNotExistException::new);
    }
    public ApplicationUser updateUser(ApplicationUser user){
        try{
            return userRepo.save(user);
        } catch(Exception e){
            throw new EmailAlreadyTakenException();
        }
    }

    public ResponseEntity<String> registerUser(RegistrationObject ro){

        ApplicationUser user = new ApplicationUser();

        user.setFirstName(ro.getFirstName());
        user.setLastName(ro.getLastName());
        user.setEmail(ro.getEmail());
        user.setDateofBirth(ro.getDob());

        String name = user.getFirstName() + user.getLastName();

        boolean nameTaken = true;

        String tempName = "";
        while (nameTaken){
            tempName = generateUsername(name);

            if(userRepo.findByUsername(tempName).isEmpty()){
                nameTaken = false;
            }
        }


        Set<Role> roles = user.getAuthorities();
        if(roleRepo.findByAuthority("USER").isPresent()){
            roles.add(roleRepo.findByAuthority("USER").get());
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

    public void generateEmailVerification(String username) {

        ApplicationUser user = userRepo.findByUsername(username).orElseThrow(UserDoesNotExistException::new);

        user.setVerification(generateVerificationNumber());
        userRepo.save(user);
    }

    private String generateUsername(String name){
        long generatedNumber = (long) Math.floor(Math.random() * 1_000_000_000);
        return name+generatedNumber;
    }

}
