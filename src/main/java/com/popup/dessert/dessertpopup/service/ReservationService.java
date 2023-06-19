package com.popup.dessert.dessertpopup.service;

import com.popup.dessert.dessertpopup.Dto.ReservationRequest;
import com.popup.dessert.dessertpopup.Dto.ReservationResponse;
import com.popup.dessert.dessertpopup.Entity.Reservation;
import com.popup.dessert.dessertpopup.respository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;

  public ReservationResponse saveReservation(ReservationRequest reservationRequest) {
    return getReservationResponse(reservationRepository.save(getReservation(reservationRequest)));
  }

  public ReservationResponse getReservation(String phone) throws Exception {
    return getReservationResponse(
        reservationRepository.findByPhone(phone)
            .orElseThrow(() -> new Exception("Cannot find reservation."))
    );
  }


  private static ReservationResponse getReservationResponse(Reservation reservation) {
    return new ReservationResponse(
        reservation.getCode(),
        reservation.getName(),
        reservation.getPhone(),
        reservation.getNumberOfPeople(),
        reservation.getReservationTime(),
        reservation.getDrink(),
        reservation.getWinePairing(),
        reservation.getComplete()
    );
  }

  private static Reservation getReservation(ReservationRequest reservationRequest) {
    return new Reservation(
        reservationRequest.getName(),
        reservationRequest.getPhone(),
        reservationRequest.getNumberOfPeople(),
        reservationRequest.getReservationTime(),
        reservationRequest.getDrink(),
        reservationRequest.getWinePairing()
    );
  }
}
