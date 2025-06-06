package com.gerenciarh.gerenciarh.Controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerenciarh.gerenciarh.DtosRequest.UserRequestDto;
import com.gerenciarh.gerenciarh.DtosResponse.UserResponseDto;
import com.gerenciarh.gerenciarh.Services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    @GetMapping("/payload")
    public ResponseEntity<UserResponseDto> getPayloadToRole(@RequestHeader("Authorization") String authorizationHeader) {
    	String token = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(userService.getPayload(token));
    }

    @GetMapping()
    public ResponseEntity<List<UserResponseDto>> findAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
        
    }

    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<UserResponseDto> findUserByNickname(@PathVariable(value = "nickname") String nickname) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByNickname(nickname));
    }

    @GetMapping("/department/{dptName}")
    public ResponseEntity<List<UserResponseDto>> findAllUsersByDepartment(@PathVariable(value = "dptName") String dptName) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsersByDepartment(dptName));
    }

    @PutMapping("/{nickname}")
    public ResponseEntity<Void> updateUserByNickname(@Valid @PathVariable(value = "nickname") String nickname,
                                                     @RequestBody UserRequestDto userRequestDto) {
        userService.updateUserByName(nickname,userRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    @PutMapping("/alter")
    public ResponseEntity<Void> updateUserByToken(@RequestHeader("Authorization") String bearerToken,
@RequestBody UserRequestDto userRequestDto) {
    	String token = bearerToken.replace("Bearer ", "");
		userService.updateUserByToken(token,userRequestDto);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
    
    @DeleteMapping("/{nickname}")
    public ResponseEntity<Void> deleteUserByNickname(@PathVariable(value = "nickname") String nickname) {
        userService.deleteUser(nickname);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
