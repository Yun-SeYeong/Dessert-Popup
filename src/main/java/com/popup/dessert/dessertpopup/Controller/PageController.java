package com.popup.dessert.dessertpopup.Controller;

import com.popup.dessert.dessertpopup.Dto.PhoneCheckRequest;
import com.popup.dessert.dessertpopup.Dto.ReservationFindRequest;
import com.popup.dessert.dessertpopup.Dto.ReservationRequest;
import com.popup.dessert.dessertpopup.Dto.ReservationResponse;
import com.popup.dessert.dessertpopup.Entity.Reservation;
import com.popup.dessert.dessertpopup.Service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
  public String reservationPage(Model model) {
    model.addAttribute("reservation", new ReservationRequest());
    return "reservation";
  }

  @GetMapping("/reservation-fail")
  public String reservationFailPage() {
    return "reservation-fail";
  }

  @GetMapping("/reservation-find")
  public String findReservationPage(Model model) {
    model.addAttribute("reservationFind", new ReservationFindRequest());
    return "reservation-find";
  }

  @PostMapping("/reservation-find")
  public String findReservation(
      @ModelAttribute
      @Validated
      ReservationFindRequest request,
      Model model,
      RedirectAttributes attributes
  ) {
    ReservationResponse reservation = reservationService.findReservation(
        request.getReservationNumber(), request.getName(), request.getPhone());
    attributes.addFlashAttribute("reservation", reservation);
    return "redirect:/reservation-check";
  }

  @GetMapping("/phone-check")
  public String phoneCheck(Model model) {
    ReservationRequest request = (ReservationRequest) model.getAttribute("reservation");

    if (request == null || request.getPhone() == null) {
      return "redirect:/";
    }

    model.addAttribute("phoneCheckRequest", new PhoneCheckRequest(
        reservationService.generateSMSToken(request.getPhone()),
        request
    ));

    return "phone-check";
  }

  @PostMapping("/phone-check")
  public String postPhoneCheck(
      @ModelAttribute("phoneCheckRequest")
      @Validated
      PhoneCheckRequest phoneCheckRequest,
      BindingResult bindingResult,
      Model model,
      RedirectAttributes attributes
  ) {
    System.out.println("phoneCheckRequest = " + phoneCheckRequest);
    if (bindingResult.hasErrors()) {
      return "phone-check";
    }
    Boolean checkSMSToken = reservationService.checkSMSToken(
        phoneCheckRequest.getPhone(),
        phoneCheckRequest.getCode(),
        phoneCheckRequest.getToken()
    );

    if (checkSMSToken) {
      ReservationResponse reservation = null;
      try {
        reservation = reservationService.saveReservation(phoneCheckRequest);
      } catch (Exception e) {
        return "redirect:/reservation-fail";
      }
      attributes.addFlashAttribute("reservation", reservation);
      return "redirect:/reservation-check";
    } else {
      bindingResult.addError(new FieldError("phoneCheckRequest", "code", "코드를 다시 확인해주세요."));
      return "phone-check";
    }
  }

  @GetMapping("/reservation-check")
  public String reservationCheckPage(Model model) {
    ReservationResponse reservation = (ReservationResponse) model.getAttribute("reservation");
    System.out.println("reservation = " + reservation);
    model.addAttribute("reservationExists", reservation != null);
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
    attributes.addFlashAttribute("reservation", request);
    return "redirect:/phone-check";
  }
}
