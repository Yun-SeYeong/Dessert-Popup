package com.popup.dessert.dessertpopup.Dto;

import lombok.Data;

@Data
public class ReservationResponse {
  private String name;
  private String phone;
  private Long numberOfPeople;
  private ReservationTime reservationTime;
  private Drink drink;
  private Boolean winePairing;
  private Boolean complete;
}
