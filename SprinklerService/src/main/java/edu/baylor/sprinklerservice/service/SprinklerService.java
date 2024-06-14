package edu.baylor.sprinklerservice.service;

import edu.baylor.sprinklerservice.model.Sprinkler;
import edu.baylor.sprinklerservice.model.SprinklerRule;
import edu.baylor.sprinklerservice.model.SprinklerStatus;
import edu.baylor.sprinklerservice.repository.SprinklerRepository;
import edu.baylor.sprinklerservice.repository.SprinklerRuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SprinklerService {
    private final SprinklerRuleRepository sprinklerRuleRepository;
    private final SprinklerRepository sprinklerRepository;

    public SprinklerRule createRule(SprinklerRule sprinklerRule) {
        return sprinklerRuleRepository.save(sprinklerRule);
    }

    public List<SprinklerRule> getAllRules() {
        log.info("Fetching all sprinkler rules from DB!");
        return sprinklerRuleRepository.findAll();
    }

    public SprinklerStatus toggleOnOff(String sprinklerId) {
        // Mock a call to Sprinkle hardware controller
        // Check status & toggle
        // Send notification (Use notification service)
            // Create a stream
            // send notification
        return SprinklerStatus.ON;
    }

    public SprinklerStatus getSprinklerStatus(String sprinklerId) {
        return SprinklerStatus.ON;
    }

    public SprinklerRule updateRule(Long ruleId, SprinklerRule sprinklerRule) {
        String startTime = sprinklerRule.getStartTime();
        int duration = sprinklerRule.getDurationMinutes();
        SprinklerRule updatedRule = sprinklerRuleRepository.findById(ruleId).orElse(null);
        if(null != updatedRule) {
            updatedRule.setStartTime(!startTime.isBlank() ? startTime : updatedRule.getStartTime());
            updatedRule.setDurationMinutes(duration > 0 ? duration : updatedRule.getDurationMinutes());
            return sprinklerRuleRepository.save(updatedRule);
        } else {
           return null;
        }
    }

    public List<Sprinkler> getAllSprinklers() {
        return sprinklerRepository.findAll();
    }

    public Sprinkler getSprinklerByDeviceId(String deviceId) {
        return sprinklerRepository.findAllByDeviceId(deviceId).getFirst();
    }

    public Sprinkler addSprinkler(Sprinkler sprinkler) {
        return sprinklerRepository.save(sprinkler);
    }

    public void deleteSprinkler(String deviceId) {
        Sprinkler sprinkler = getSprinklerByDeviceId(deviceId);
        sprinklerRepository.deleteById(sprinkler.getId());
    }

    public Sprinkler updateSprinkler(String deviceId, Sprinkler sprinkler) {
        String locationAlias = sprinkler.getLocationAlias();
        String locationCoordinates = sprinkler.getLocationCoordinates();

        Sprinkler updatedSprinkler = getSprinklerByDeviceId(deviceId);

        if(locationAlias != null) updatedSprinkler.setLocationAlias(locationAlias);
        if(locationCoordinates != null) updatedSprinkler.setLocationCoordinates(locationCoordinates);

        return sprinklerRepository.save(updatedSprinkler);
    }
}
