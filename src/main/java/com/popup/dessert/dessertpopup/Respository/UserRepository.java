package com.popup.dessert.dessertpopup.Respository;

import com.popup.dessert.dessertpopup.Entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Member, Long> {
  Optional<Member> findUserByEmail(String email);
  void deleteByEmail(String email);
}
