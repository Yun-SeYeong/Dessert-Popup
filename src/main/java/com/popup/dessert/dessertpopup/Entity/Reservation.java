package com.popup.dessert.dessertpopup.Entity;

import com.popup.dessert.dessertpopup.Dto.Drink;
import com.popup.dessert.dessertpopup.Dto.ReservationTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "reservation_id")
  private Long id;

  private String name;
  @Column(unique = true)
  private String phone;
  private Long numberOfPeople;
  private ReservationTime reservationTime;
  private Drink drink;
  private Boolean winePairing;
  private Boolean complete;

}
