package it.anoki.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.anoki.spring.model.Venue;

@Repository("venueRepository")
public interface VenueRepository extends JpaRepository<Venue, Long> {

}