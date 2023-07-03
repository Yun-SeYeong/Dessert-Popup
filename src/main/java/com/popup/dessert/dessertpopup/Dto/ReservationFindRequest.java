package com.popup.dessert.dessertpopup.Dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ReservationFindRequest {
  @NotNull
  private String name;
  @NotNull
  @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
  private String phone;
  @NotNull
  private String reservationNumber;
}
