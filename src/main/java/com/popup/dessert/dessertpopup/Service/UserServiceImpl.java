package com.popup.dessert.dessertpopup.Service;

import com.popup.dessert.dessertpopup.Dto.LoginRequest;
import com.popup.dessert.dessertpopup.Dto.TokenResponse;
import com.popup.dessert.dessertpopup.Dto.UserPasswordRequest;
import com.popup.dessert.dessertpopup.Dto.UserRequest;
import com.popup.dessert.dessertpopup.Dto.UserResponse;
import com.popup.dessert.dessertpopup.Entity.Member;
import com.popup.dessert.dessertpopup.Respository.UserRepository;
import com.popup.dessert.dessertpopup.filter.JwtTokenProvider;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl {

  private final UserRepository userRepository;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;

  public TokenResponse signIn(LoginRequest loginRequest) {
    UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

    authenticationManagerBuilder.getObject().authenticate(token);

    return jwtTokenProvider.generateToken(token);
  }

  public UserResponse signUp(UserRequest userRequest) {
    return userToUserResponse(
        userRepository.save(userRequestToUser(userRequest, Collections.singletonList("USER")))
    );
  }

  public void updateUserPassword(UserPasswordRequest userPasswordRequest) {
    Member member = (Member) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    member.setPassword(passwordEncoder.encode(userPasswordRequest.getPassword()));
  }

  public void deleteUser() {
    Member member = (Member) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    userRepository.deleteByEmail(member.getEmail());
  }

  @Transactional(readOnly = true)
  public UserResponse getUser() throws Exception {
    Member member = (Member) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    return userToUserResponse(userRepository.findById(member.getId())
        .orElseThrow(() -> new Exception("cannot find user.")));
  }

  private static UserResponse userToUserResponse(Member member) {
    return new UserResponse(
        member.getId(),
        member.getEmail()
    );
  }

  private Member userRequestToUser(UserRequest userRequest, List<String> roles) {
    return new Member(
        userRequest.getEmail(),
        passwordEncoder.encode(userRequest.getPassword()),
        roles
    );
  }
}
