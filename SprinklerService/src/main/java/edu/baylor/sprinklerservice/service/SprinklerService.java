package edu.baylor.sprinklerservice.service;

import edu.baylor.sprinklerservice.model.SprinklerRule;
import edu.baylor.sprinklerservice.repository.SprinklerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SprinklerService {
    private final SprinklerRepository sprinklerRepository;

    public SprinklerRule createRule(SprinklerRule sprinklerRule) {
        return sprinklerRepository.save(sprinklerRule);
    }

    public List<SprinklerRule> getAllRules() {
        log.info("Fetching all sprinkler rules from DB!");
        return sprinklerRepository.findAll();
    }


    public void toggleOnOff(String sprinklerId) {
        // Mock a call to Sprinkle hardware controller
        // Check status & toggle
        // Send notification
    }
}
