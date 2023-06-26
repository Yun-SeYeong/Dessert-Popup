package com.popup.dessert.dessertpopup.Entity;

import com.popup.dessert.dessertpopup.Dto.Drink;
import com.popup.dessert.dessertpopup.Dto.ReservationTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

  private final long CODE = 84932L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "reservation_id")
  private Long id;

  private String name;
  @Column(unique = true)
  private String phone;
  private Long numberOfPeople;
  @Enumerated(value = EnumType.STRING)
  private ReservationTime reservationTime;
  @Enumerated(value = EnumType.STRING)
  private Drink drink;
  private Boolean winePairing;
  private Boolean complete;

  public Reservation(String name, String phone, Long numberOfPeople,
      ReservationTime reservationTime,
      Drink drink, Boolean winePairing) {
    this.name = name;
    this.phone = phone;
    this.numberOfPeople = numberOfPeople;
    this.reservationTime = reservationTime;
    this.drink = drink;
    this.winePairing = winePairing;
    this.complete = false;
  }

  public String getCode() {
    return String.valueOf(this.CODE + this.id);
  }

  public void complete() {
    this.complete = true;
  }
}
