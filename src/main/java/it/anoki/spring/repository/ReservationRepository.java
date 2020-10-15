package it.anoki.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.anoki.spring.model.Reservation;

@Repository("ReservationRepository")
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}