package com.popup.dessert.dessertpopup.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
  private Long id;
  private String reservationNumber;
  private String name;
  private String phone;
  private Long numberOfPeople;
  private ReservationTime reservationTime;
  private Long numberOfDrink1;
  private Long numberOfDrink2;
  private Long winePairing;
  private Boolean complete;
  private Long price;
}
