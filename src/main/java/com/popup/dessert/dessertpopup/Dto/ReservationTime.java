package com.popup.dessert.dessertpopup.Dto;

public enum ReservationTime {
  H12M00("12:00"),
  H13M30("13:30"),
  H15M00("15:00"),
  H16M30("16:30"),
  H18M00("18:00"),
  H19M30("19:30");

  private final String code;
  ReservationTime(String code) {
    this.code = code;
  }
  public String getCode() {
    return code;
  }
}
