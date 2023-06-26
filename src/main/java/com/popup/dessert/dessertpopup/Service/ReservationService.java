package com.popup.dessert.dessertpopup.Service;

import com.popup.dessert.dessertpopup.Dto.ReservationRequest;
import com.popup.dessert.dessertpopup.Dto.ReservationResponse;
import com.popup.dessert.dessertpopup.Entity.Reservation;
import com.popup.dessert.dessertpopup.Respository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;

  public ReservationResponse saveReservation(ReservationRequest reservationRequest) {
    return getReservationResponse(reservationRepository.save(getReservation(reservationRequest)));
  }

  public ReservationResponse getReservationByPhone(String phone) throws Exception {
    return getReservationResponse(
        reservationRepository.findByPhone(phone)
            .orElseThrow(() -> new Exception("Cannot find reservation."))
    );
  }

  public Page<ReservationResponse> getReservationWithPaging(Pageable pageable) {
    return reservationRepository.findAll(pageable)
        .map(this::getReservationResponse);
  }

  public void completeReservation(Long id) throws Exception {
    Reservation reservation = reservationRepository.findById(id)
        .orElseThrow(() -> new Exception("reservation is not exists"));

    reservation.complete();
  }

  private ReservationResponse getReservationResponse(Reservation reservation) {
    return new ReservationResponse(
        reservation.getId(),
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
