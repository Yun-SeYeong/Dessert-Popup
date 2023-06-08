package com.popup.dessert.dessertpopup.Dto;

public enum Drink {
  ADE("자두에이드"),
  COFFEE("콜드브루커피");

  private final String code;
  Drink(String code) {
    this.code = code;
  }
  public String getCode() {
    return code;
  }
}
