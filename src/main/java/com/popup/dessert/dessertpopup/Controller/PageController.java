package com.popup.dessert.dessertpopup.Controller;

import com.popup.dessert.dessertpopup.Dto.ReservationRequest;
import com.popup.dessert.dessertpopup.Dto.ReservationResponse;
import com.popup.dessert.dessertpopup.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PageController {
  private final ReservationService reservationService;

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/reservation")
  public String reservation(Model model) {
    model.addAttribute("reservation", new ReservationRequest());
    return "reservation";
  }

  @GetMapping("/reservation-check")
  public String reservationCheck(Model model) {
    ReservationResponse reservation = (ReservationResponse) model.getAttribute("reservation");
    System.out.println("reservation = " + reservation);
    return "reservation-check";
  }

  @PostMapping("/reservation")
  public String postReservation(
      @ModelAttribute("reservation")
      @Validated
      ReservationRequest request,
      BindingResult bindingResult,
      Model model,
      RedirectAttributes attributes
  ) {
    if (bindingResult.hasErrors()) {
      return "reservation";
    }
    ReservationResponse reservation = reservationService.saveReservation(request);
    attributes.addFlashAttribute("reservation", reservation);
    return "redirect:/reservation-check";
  }
}
