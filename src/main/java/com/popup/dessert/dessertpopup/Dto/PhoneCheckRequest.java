package com.popup.dessert.dessertpopup.Dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PhoneCheckRequest {
  @NotNull
  private String token;
  @NotNull
  private String code;
  @NotNull
  private String name;
  @NotNull
  @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
  private String phone;
  @NotNull
  private Long numberOfPeople;
  @NotNull
  private ReservationTime reservationTime;
  @NotNull
  private Long numberOfDrink1;
  @NotNull
  private Long numberOfDrink2;
  @NotNull
  private Boolean winePairing;

  public PhoneCheckRequest(String token, ReservationRequest reservationRequest) {
    this.token = token;
    this.name = reservationRequest.getName();
    this.phone = reservationRequest.getPhone();
    this.numberOfPeople = reservationRequest.getNumberOfPeople();
    this.reservationTime = reservationRequest.getReservationTime();
    this.numberOfDrink1 = reservationRequest.getNumberOfDrink1();
    this.numberOfDrink2 = reservationRequest.getNumberOfDrink2();
    this.winePairing = reservationRequest.getWinePairing();
  }
}
