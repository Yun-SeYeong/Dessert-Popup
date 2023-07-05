package com.popup.dessert.dessertpopup.Respository;

import static com.popup.dessert.dessertpopup.Entity.QReservation.*;

import com.popup.dessert.dessertpopup.Dto.ReservationSearchCondition;
import com.popup.dessert.dessertpopup.Dto.ReservationTime;
import com.popup.dessert.dessertpopup.Entity.Reservation;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public ReservationRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public Page<Reservation> search(ReservationSearchCondition condition, Pageable pageable) {
    List<Reservation> fetch = queryFactory
        .selectFrom(reservation)
        .where(
            codeLike(condition.getReservationNumber()),
            nameLike(condition.getName()),
            phoneLike(condition.getPhone()),
            reservationTimeEq(condition.getReservationTime()),
            completeEq(condition.getComplete())
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long count = queryFactory
        .select(reservation.count())
        .from(reservation)
        .where(
            codeLike(condition.getReservationNumber()),
            nameLike(condition.getName()),
            phoneLike(condition.getPhone()),
            reservationTimeEq(condition.getReservationTime()),
            completeEq(condition.getComplete())
        )
        .fetchFirst();

    return new PageImpl<>(fetch, pageable, count);
  }

  @Override
  public Long countNumberOfPeople(ReservationTime reservationTime) {
    Long count = queryFactory
        .select(reservation.numberOfPeople.sum())
        .from(reservation)
        .where(reservation.reservationTime.eq(reservationTime))
        .fetchOne();
    return count != null ? count : 0;
  }

  private static Predicate completeEq(Boolean complete) {
    return complete != null ? reservation.complete.eq(complete) : null;
  }

  private static Predicate reservationTimeEq(ReservationTime reservationTime) {
    return reservationTime != null ? reservation.reservationTime.eq(
        reservationTime) : null;
  }

  private static Predicate phoneLike(String phone) {
    return StringUtils.isNotEmpty(phone) ? reservation.phone.like(
        "%" + phone + "%") : null;
  }

  private static Predicate codeLike(String reservationNumber) {
    return StringUtils.isNotEmpty(reservationNumber) ? reservation.code.like(
        "%" + reservationNumber + "%") : null;
  }

  private static BooleanExpression nameLike(String name) {
    return StringUtils.isNotEmpty(name) ? reservation.name.like(
        "%" + name + "%") : null;
  }
}
