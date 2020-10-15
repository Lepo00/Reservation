package it.anoki.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.anoki.spring.model.Group;

@Repository("groupRepository")
public interface GroupRepository extends JpaRepository<Group, Long> {

}