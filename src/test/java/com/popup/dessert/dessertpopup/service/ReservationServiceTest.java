package com.popup.dessert.dessertpopup.service;

import com.popup.dessert.dessertpopup.Dto.Drink;
import com.popup.dessert.dessertpopup.Dto.ReservationRequest;
import com.popup.dessert.dessertpopup.Dto.ReservationResponse;
import com.popup.dessert.dessertpopup.Dto.ReservationTime;
import com.popup.dessert.dessertpopup.respository.ReservationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReservationServiceTest {

  @Autowired
  private ReservationService reservationService;

  @Test
  @DisplayName(value = "create reservation")
  public void createReservation() {
    // given
    ReservationRequest request = new ReservationRequest(
        "가나다",
        "010-0000-0000",
        1L,
        ReservationTime.H15M00,
        Drink.ADE,
        true
    );
    // when
    ReservationResponse reservation = reservationService.saveReservation(request);

    // then
    Assertions.assertThat(reservation).isNotNull();
  }

}