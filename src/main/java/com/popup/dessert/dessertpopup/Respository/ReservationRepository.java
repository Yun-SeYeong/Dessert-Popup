package com.popup.dessert.dessertpopup.Respository;

import com.popup.dessert.dessertpopup.Entity.Reservation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {
  Optional<Reservation> findByCodeAndNameAndPhone(String code, String name, String phone);
}
