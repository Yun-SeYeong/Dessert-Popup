package com.popup.dessert.dessertpopup.Config;

import com.popup.dessert.dessertpopup.Dto.Drink;
import com.popup.dessert.dessertpopup.Dto.ReservationTime;
import com.popup.dessert.dessertpopup.Dto.UserRequest;
import com.popup.dessert.dessertpopup.Entity.Member;
import com.popup.dessert.dessertpopup.Entity.Reservation;
import com.popup.dessert.dessertpopup.Respository.ReservationRepository;
import com.popup.dessert.dessertpopup.Respository.UserRepository;
import com.popup.dessert.dessertpopup.Service.UserServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminConfig {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ReservationRepository reservationRepository;

  @PostConstruct
  private void init() {
    userRepository.save(new Member(
        "admin",
        passwordEncoder.encode("admin"),
        Collections.singletonList("ADMIN")
    ));

    for (int i = 0; i < 50; i++) {
      reservationRepository.save(new Reservation(
          "test" + i,
          "test" + i,
          4L,
          ReservationTime.H15M00,
          Drink.ADE,
          false
      ));
    }
  }
}
