package edu.baylor.sprinklerservice.repository;

import edu.baylor.sprinklerservice.model.Sprinkler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprinklerRepository extends JpaRepository<Sprinkler, Long> {
    List<Sprinkler> findAllByDeviceId(String deviceId);
}
