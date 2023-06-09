package com.popup.dessert.dessertpopup.Controller;

import com.popup.dessert.dessertpopup.Dto.ReservationResponse;
import com.popup.dessert.dessertpopup.Entity.Reservation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class PageController {

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/reservation")
  public String reservation(Model model) {
    model.addAttribute("reservation", new ReservationResponse());
    return "reservation";
  }

  @PostMapping("/reservation")
  public String postReservation(@ModelAttribute("reservation") ReservationResponse reservation) {
    System.out.println("reservation = " + reservation);
    return "redirect:/";
  }
}
