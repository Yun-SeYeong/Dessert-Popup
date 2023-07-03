package com.popup.dessert.dessertpopup.Dto;

import com.popup.dessert.dessertpopup.Entity.Reservation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationSearchCondition {
  private String reservationNumber;
  private String name;
  private String phone;
  private ReservationTime reservationTime;
  private Boolean winePairing;
  private Boolean complete;
}
