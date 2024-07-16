package edu.baylor.sprinklerservice.repository;

import edu.baylor.sprinklerservice.model.SprinklerRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprinklerRuleRepository extends JpaRepository<SprinklerRule, Long> {
}
