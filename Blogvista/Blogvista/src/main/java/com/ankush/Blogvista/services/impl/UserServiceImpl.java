package com.ankush.Blogvista.services.impl;

import com.ankush.Blogvista.entities.User;
import com.ankush.Blogvista.payloads.UserDto;
import com.ankush.Blogvista.repositories.UserRepo;
import com.ankush.Blogvista.services.JWTService;
import com.ankush.Blogvista.services.UserService;
import com.ankush.Blogvista.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

@Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

@Autowired
private ModelMapper modelMapper;

private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public UserDto createUser(UserDto userDto) {

        User user=this.dtoToUser(userDto);
        user.setPassword(encoder.encode(user.getPassword()));
        User savedUser=this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setAbout(userDto.getAbout());
        User updatedUser =this.userRepo.save(user);

        UserDto userDto1=this.userToDto(updatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        UserDto userDto=this.userToDto(user);
        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users=this.userRepo.findAll();
        List<UserDto> userDtos =users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        this.userRepo.delete(user);
        return;
    }

    @Override
    public String verify(UserDto userDto) {
        User user=this.dtoToUser(userDto);
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getName()) ;
        } else {
            return "fail";
        }
    }


    private User dtoToUser(UserDto userDto){
        User user=this.modelMapper.map(userDto,User.class);

        return user;
    }

    public UserDto userToDto(User user){
        UserDto userDto=this.modelMapper.map(user,UserDto.class);
        return userDto;
    }

}
