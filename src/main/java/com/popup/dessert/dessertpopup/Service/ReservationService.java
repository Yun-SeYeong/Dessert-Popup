package com.popup.dessert.dessertpopup.Service;

import com.popup.dessert.dessertpopup.Dto.PhoneCheckRequest;
import com.popup.dessert.dessertpopup.Dto.ReservationRequest;
import com.popup.dessert.dessertpopup.Dto.ReservationResponse;
import com.popup.dessert.dessertpopup.Dto.ReservationSearchCondition;
import com.popup.dessert.dessertpopup.Dto.ReservationTime;
import com.popup.dessert.dessertpopup.Entity.Reservation;
import com.popup.dessert.dessertpopup.Respository.ReservationRepository;
import com.popup.dessert.dessertpopup.filter.JwtFilter;
import com.popup.dessert.dessertpopup.filter.JwtTokenProvider;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final SMSService smsService;
  private final Long TIME_LIMIT = 15L;

  public ReservationResponse saveReservation(ReservationRequest reservationRequest) {
    Reservation reservation = reservationRepository.save(getReservation(reservationRequest));
    reservation.generateCode();
    return getReservationResponse(reservation);
  }

  public ReservationResponse saveReservation(PhoneCheckRequest reservationRequest)
      throws Exception {
    Long countedNumberOfPeople = reservationRepository.countNumberOfPeople(
        reservationRequest.getReservationTime());
    if (countedNumberOfPeople + reservationRequest.getNumberOfPeople() > TIME_LIMIT) {
      throw new Exception("예약 가능 인원을 초과하였습니다.");
    }

    List<Reservation> content = reservationRepository.search(
        new ReservationSearchCondition(
            null,
            reservationRequest.getName(),
            reservationRequest.getPhone(),
            null,
            null
        ),
        PageRequest.of(0, 1)
    ).getContent();

    if (content.size() > 0) {
      return getReservationResponse(content.get(0));
    }

    Reservation reservation = reservationRepository.save(getReservation(reservationRequest));
    reservation.generateCode();

    smsService.publishSMS(
        reservation.getPhone().replaceAll("-", ""),
        "<예약 등록 완료>\n"
            + "예약 번호: [" + reservation.getCode() + "]\n"
            + "예약 시간: " + reservation.getReservationTime().getCode() + "\n"
            + "위치: 서울 마포구 고산2길 6 1층"

    );
    return getReservationResponse(reservation);
  }

  public List<ReservationTime> getAvailableReservationTime() {
    Map<ReservationTime, List<Reservation>> collect = reservationRepository.findAll()
        .stream()
        .collect(Collectors.groupingBy(Reservation::getReservationTime));

    for (ReservationTime value : ReservationTime.values()) {
      collect.put(value, collect.getOrDefault(value, new ArrayList<>()));
    }

    ArrayList<ReservationTime> result = new ArrayList<>();

    collect.forEach((reservationTime, reservations) -> {
      if (reservations.stream()
          .map(Reservation::getNumberOfPeople)
          .reduce(Long::sum)
          .orElse(0L) < TIME_LIMIT
      ) {
        result.add(reservationTime);
      }
    });

    return result.stream()
        .sorted(Comparator.comparing(ReservationTime::getCode))
        .collect(Collectors.toList());
  }

  public ReservationResponse findReservation(String code, String name, String phone) {
    Reservation reservation = reservationRepository.findByCodeAndNameAndPhone(code, name, phone)
        .orElse(null);

    if (reservation == null) {
      return null;
    } else {
      return getReservationResponse(reservation);
    }

  }

  public Page<ReservationResponse> getReservationWithPaging(Pageable pageable) {
    return reservationRepository.findAll(pageable)
        .map(this::getReservationResponse);
  }

  public Page<ReservationResponse> search(ReservationSearchCondition condition, Pageable pageable) {
    return reservationRepository.search(condition, pageable)
        .map(this::getReservationResponse);
  }

  public void completeReservation(Long id) throws Exception {
    Reservation reservation = reservationRepository.findById(id)
        .orElseThrow(() -> new Exception("reservation is not exists"));

    reservation.convertComplete();
  }

  public void deleteReservation(Long id) {
    reservationRepository.deleteById(id);
  }

  public String generateSMSToken(String phone) {
    String code = String.format("%04d", (int) (Math.random() * 10000));
    System.out.println("code = " + code);
    String token = jwtTokenProvider.generateSMSToken(phone, code);
    smsService.publishSMS(phone.replaceAll("-", ""), "code:[" + code + "]");
    return token;
  }

  public Boolean checkSMSToken(String phone, String code, String accessToken) {
    Boolean result;
    try {
      result = jwtTokenProvider.checkSMSToken(phone, code, JwtFilter.getToken(accessToken));
    } catch (Exception e) {
      return false;
    }
    return result;
  }

  public ReservationResponse getReservationResponse(Reservation reservation) {
    return new ReservationResponse(
        reservation.getId(),
        reservation.getCode(),
        reservation.getName(),
        reservation.getPhone(),
        reservation.getNumberOfPeople(),
        reservation.getReservationTime(),
        reservation.getNumberOfDrink1(),
        reservation.getNumberOfDrink2(),
        reservation.getWinePairing(),
        reservation.getComplete(),
        reservation.getWinePairing() * 15000L + reservation.getNumberOfPeople() * 39000L
    );
  }

  public Reservation getReservation(ReservationRequest reservationRequest) {
    return new Reservation(
        reservationRequest.getName(),
        reservationRequest.getPhone(),
        reservationRequest.getNumberOfPeople(),
        reservationRequest.getReservationTime(),
        reservationRequest.getNumberOfDrink1(),
        reservationRequest.getNumberOfDrink2(),
        reservationRequest.getWinePairing()
    );
  }

  public Reservation getReservation(PhoneCheckRequest reservationRequest) {
    return new Reservation(
        reservationRequest.getName(),
        reservationRequest.getPhone(),
        reservationRequest.getNumberOfPeople(),
        reservationRequest.getReservationTime(),
        reservationRequest.getNumberOfDrink1(),
        reservationRequest.getNumberOfDrink2(),
        reservationRequest.getWinePairing()
    );
  }
}
