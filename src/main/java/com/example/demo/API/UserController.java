package com.example.demo.API;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Entity.UserRegistration;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
//save the user
@PostMapping("/saveUser")
public ResponseEntity<String> saveUser(@RequestBody UserRegistration userRegistration) {
    String savedUser = userService.saveUser(userRegistration);  // Fix variable name & type
    return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
}

    //Get the user byId
    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return userService.getUser(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + id));
    }
//Get the user by using UserName
    @GetMapping("/getUserByName/{name}")
    public ResponseEntity<UserDTO> getUserByName(@PathVariable String name) {
        return userService.getUserByName(name)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchElementException("User not found with name: " + name));
    }
//Delete data using UserId
    @DeleteMapping("/deleteUserById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        String deleteByID = userService.deleteByID(id);
        return ResponseEntity.ok(deleteByID);
    }
//DeleteUser byName
    @DeleteMapping("/deleteUserByName/{userName}")
    public ResponseEntity<String> deleteByName(@PathVariable String userName) {
        String deleteByName = userService.deleteByName(userName);
        return ResponseEntity.ok(deleteByName);
    }
//Update data ById
    @PutMapping("/updateById/{id}")
    public ResponseEntity<String> updateById(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        String updateById = userService.updateById(id, userDTO);
        return ResponseEntity.ok(updateById);
    }
//Get All Users
    @GetMapping("/getUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
//Exception handler
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> noDataFound(NoSuchElementException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
