package com.popup.dessert.dessertpopup.Config;

import com.popup.dessert.dessertpopup.Dto.Drink;
import com.popup.dessert.dessertpopup.Dto.ReservationRequest;
import com.popup.dessert.dessertpopup.Dto.ReservationTime;
import com.popup.dessert.dessertpopup.Dto.UserRequest;
import com.popup.dessert.dessertpopup.Entity.Member;
import com.popup.dessert.dessertpopup.Entity.Reservation;
import com.popup.dessert.dessertpopup.Respository.ReservationRepository;
import com.popup.dessert.dessertpopup.Respository.UserRepository;
import com.popup.dessert.dessertpopup.Service.ReservationService;
import com.popup.dessert.dessertpopup.Service.UserServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import software.amazon.awssdk.services.sns.SnsClient;

@Configuration
@RequiredArgsConstructor
public class AdminConfig {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ReservationService reservationService;
  private final SnsClient snsClient;

  @PostConstruct
  private void init() {
    userRepository.save(new Member(
        "admin",
        passwordEncoder.encode("admin"),
        Collections.singletonList("ADMIN")
    ));

    reservationService.saveReservation(new ReservationRequest(
        "윤세영",
        "010-9628-3350",
        4L,
        ReservationTime.H15M00,
        1L,
        2L,
        false
    ));
  }
}
