package com.gerenciarh.gerenciarh.Controller;


import com.gerenciarh.gerenciarh.DtosRequest.UserRequestDto;
import com.gerenciarh.gerenciarh.DtosResponse.UserResponseDto;
import com.gerenciarh.gerenciarh.Services.UserService;

import java.util.List;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
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

    @DeleteMapping("/{nickname}")
    public ResponseEntity<Void> deleteUserByNickname(@PathVariable(value = "nickname") String nickname) {
        userService.deleteUser(nickname);
        return ResponseEntity.status(HttpStatus.OK).build();
    }





}
