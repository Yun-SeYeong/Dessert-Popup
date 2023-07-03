package com.popup.dessert.dessertpopup.Entity;

import com.popup.dessert.dessertpopup.Dto.ReservationTime;
import java.util.Random;
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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "reservation_id")
  private Long id;

  private String code;
  private String name;
  @Column(unique = true)
  private String phone;
  private Long numberOfPeople;
  @Enumerated(value = EnumType.STRING)
  private ReservationTime reservationTime;
  private Long numberOfDrink1;
  private Long numberOfDrink2;
  private Boolean winePairing;
  private Boolean complete;

  public Reservation(String name, String phone, Long numberOfPeople,
      ReservationTime reservationTime,
      Long numberOfDrink1, Long numberOfDrink2, Boolean winePairing) {
    this.name = name;
    this.phone = phone;
    this.numberOfPeople = numberOfPeople;
    this.reservationTime = reservationTime;
    this.numberOfDrink1 = numberOfDrink1;
    this.numberOfDrink2 = numberOfDrink2;
    this.winePairing = winePairing;
    this.complete = false;
  }

  public void generateCode() {
    this.code = getRandom() + getRandom() + getRandom() + this.id;
  }

  private String getRandom() {
    return String.valueOf(new Random().nextInt(9) + 1);
  }

  public void convertComplete() {
    this.complete = !this.complete;
  }
}
