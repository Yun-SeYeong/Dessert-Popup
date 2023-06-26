package com.popup.dessert.dessertpopup.Controller;

import com.popup.dessert.dessertpopup.Dto.LoginRequest;
import com.popup.dessert.dessertpopup.Dto.TokenResponse;
import com.popup.dessert.dessertpopup.Dto.UserPasswordRequest;
import com.popup.dessert.dessertpopup.Dto.UserRequest;
import com.popup.dessert.dessertpopup.Dto.UserResponse;
import com.popup.dessert.dessertpopup.Service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/user")
@AllArgsConstructor
public class UserController {

  private final UserServiceImpl userService;

  @PostMapping
  private UserResponse createUser(@RequestBody UserRequest userRequest) {
    return userService.signUp(userRequest);
  }

  @GetMapping
  private UserResponse getUser() throws Exception {
    return userService.getUser();
  }

  @DeleteMapping
  private ResponseEntity<?> deleteUser() {
    userService.deleteUser();
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/login")
  private TokenResponse login(@RequestBody LoginRequest loginRequest) {
    return userService.signIn(loginRequest);
  }

  @PatchMapping("/password")
  private ResponseEntity<?> UserRequest(@RequestBody UserPasswordRequest userPasswordRequest) {
    userService.updateUserPassword(userPasswordRequest);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
