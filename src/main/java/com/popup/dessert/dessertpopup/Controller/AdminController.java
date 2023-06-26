package com.popup.dessert.dessertpopup.Controller;

import com.popup.dessert.dessertpopup.Dto.ReservationResponse;
import com.popup.dessert.dessertpopup.Service.ReservationService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

  private final ReservationService reservationService;

  @GetMapping
  public String index(Model model) {
    return "admin";
  }

  @GetMapping("/reservation-table")
  public String reservationTable(
      @RequestParam(value = "page", defaultValue = "1", required = false) int page,
      @RequestParam(value = "size", defaultValue = "15", required = false) int size,
      Model model
  ) {
    Page<ReservationResponse> reservations = reservationService.getReservationWithPaging(
        PageRequest.of(page - 1, size));
    model.addAttribute("reservations", reservations);

    int totalPages = reservations.getTotalPages();
    if (totalPages > 0) {
      List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
          .boxed()
          .collect(Collectors.toList());
      model.addAttribute("pageNumbers", pageNumbers);
    }

    return "reservation-table";
  }

  @GetMapping("/metric-panel")
  public String metricPanel(
      Model model
  ) {
    Page<ReservationResponse> reservations = reservationService.getReservationWithPaging(
        Pageable.unpaged());

    long total = reservations.stream()
        .count();

    model.addAttribute("totalReservation", reservations.getTotalElements());

    long peopleTotal = reservations.stream()
        .map(ReservationResponse::getNumberOfPeople)
        .mapToLong(i -> i)
        .sum();

    model.addAttribute("peopleTotal", peopleTotal);

    long completeTotal = reservations.stream()
        .filter(ReservationResponse::getComplete)
        .count();

    model.addAttribute("completeTotal", completeTotal);
    model.addAttribute("notCompleteTotal", total - completeTotal);

    return "dashboard-panel :: dashboard-panel-fragment";
  }

  @PostMapping("/reservation/complete")
  public ResponseEntity<?> reservationComplete(
      @RequestParam Long id
  ) throws Exception {
    reservationService.completeReservation(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
