package com.popup.dessert.dessertpopup.respository;

import com.popup.dessert.dessertpopup.Entity.Reservation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
  Optional<Reservation> findByPhone(String phone);
}
