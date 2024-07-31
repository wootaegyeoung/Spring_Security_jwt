package com.example.security_jwt.service;

import com.example.security_jwt.dto.joinDTO;
import com.example.security_jwt.entity.UserEntity;
import com.example.security_jwt.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository){
        this.userRepository=userRepository;
        this.bCryptPasswordEncoder=new BCryptPasswordEncoder();
    }
    public boolean joinProcess(joinDTO joinDTO){
        String username=joinDTO.getUsername();
        String password=joinDTO.getPassword();

        Boolean isExist=userRepository.existsByUsername(username);
        if(isExist){
            return false;
        }
        UserEntity data=new UserEntity();
        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");


        userRepository.save(data);

        return true;
    }
}
