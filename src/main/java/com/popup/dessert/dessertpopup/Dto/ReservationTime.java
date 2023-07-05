package com.popup.dessert.dessertpopup.Dto;

public enum ReservationTime {
  D19H12M00("8월19일 12:00"),
  D19H13M30("8월19일 13:30"),
  D19H15M00("8월19일 15:00"),
  D19H16M30("8월19일 16:30"),
  D19H18M00("8월19일 18:00"),
  D19H19M30("8월19일 19:30"),
  D20H12M00("8월20일 12:00"),
  D20H13M30("8월20일 13:30"),
  D20H15M00("8월20일 15:00"),
  D20H16M30("8월20일 16:30"),
  D20H18M00("8월20일 18:00"),
  D20H19M30("8월20일 19:30");

  private final String code;
  ReservationTime(String code) {
    this.code = code;
  }
  public String getCode() {
    return code;
  }
}
