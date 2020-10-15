package it.anoki.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.anoki.spring.model.Seat;

@Repository("SeatRepository")
public interface SeatRepository extends JpaRepository<Seat, Long> {

}