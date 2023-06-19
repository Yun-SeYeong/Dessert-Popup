package com.popup.dessert.dessertpopup.Dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
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
  private Drink drink;
  @NotNull
  private Boolean winePairing;
}
