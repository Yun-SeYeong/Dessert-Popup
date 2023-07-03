package com.popup.dessert.dessertpopup.Respository;

import com.popup.dessert.dessertpopup.Dto.ReservationSearchCondition;
import com.popup.dessert.dessertpopup.Entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationRepositoryCustom {
  Page<Reservation> search(ReservationSearchCondition condition, Pageable pageable);
}
