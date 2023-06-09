package com.popup.dessert.dessertpopup.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
  private String name;
  private String phone;
  private Long numberOfPeople;
  private ReservationTime reservationTime;
  private Drink drink;
  private Boolean winePairing;
  private Boolean complete;
}
