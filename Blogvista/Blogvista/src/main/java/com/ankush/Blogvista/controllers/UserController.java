package com.ankush.Blogvista.controllers;

import com.ankush.Blogvista.entities.User;
import com.ankush.Blogvista.payloads.ApiResponse;
import com.ankush.Blogvista.payloads.UserDto;
import com.ankush.Blogvista.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

@PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createUserDto=this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable int userId){
        UserDto updatedUserDto=this.userService.updateUser(userDto,userId);
        return ResponseEntity.ok(updatedUserDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@RequestBody UserDto userDto,@PathVariable("userId") int uid){
        this.userService.deleteUser(uid);
        return new ResponseEntity(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto>  getUser(@PathVariable("userId") int uid){
    UserDto getUserDto=this.userService.getUserById(uid);
    return ResponseEntity.ok(getUserDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>>  getAllUsers(){
        this.userService.getAllUsers();
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDto userDto) {
    return userService.verify(userDto);
    }
}
