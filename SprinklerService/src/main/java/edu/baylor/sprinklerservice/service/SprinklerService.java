package edu.baylor.sprinklerservice.service;

import edu.baylor.sprinklerservice.client.NotificationServiceDiscoveryClient;
import edu.baylor.sprinklerservice.model.Sprinkler;
import edu.baylor.sprinklerservice.model.SprinklerEvent;
import edu.baylor.sprinklerservice.model.SprinklerRule;
import edu.baylor.sprinklerservice.model.SprinklerStatus;
import edu.baylor.sprinklerservice.repository.SprinklerRepository;
import edu.baylor.sprinklerservice.repository.SprinklerRuleRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SprinklerService {
    private final SprinklerRuleRepository sprinklerRuleRepository;
    private final SprinklerRepository sprinklerRepository;
    private final NotificationServiceDiscoveryClient notificationServiceDiscoveryClient;
    private final StreamBridge streamBridge;

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

    @CircuitBreaker(name = "notificationCB", fallbackMethod = "notificationFallback")
    public Sprinkler addSprinkler(Sprinkler sprinkler, String authToken) {
        Sprinkler savedSprinkler = sprinklerRepository.save(sprinkler);
        log.info(notificationServiceDiscoveryClient
                .sendNotification(authToken, "New Sprinkler Added: " + savedSprinkler.getDeviceId()));

        return savedSprinkler;
    }
    private Sprinkler notificationFallback(Sprinkler sprinkler, String authToken, Throwable throwable) {
        log.info("Triggered notificationFallback when adding a sprinkler");
        return getSprinklerByDeviceId(sprinkler.getDeviceId());
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

    public void deleteSprinkler(String deviceId) {
        Sprinkler sprinkler = getSprinklerByDeviceId(deviceId);
        sprinklerRepository.deleteById(sprinkler.getId());
        log.info("Deleted Sprinkler with id: {}", deviceId);
        streamBridge.send("sprinklerEventSupplier-out-0",
                SprinklerEvent.builder()
                        .eventName(SprinklerEvent.class.getTypeName())
                        .action("Sprinkler Deleted")
                        .sprinklerId(deviceId)
                        .build());
        log.info("Produced Sprinkler:{} Deleted event to sprinkler-event-topic!", deviceId);
    }

    public Sprinkler updateSprinkler(String deviceId, Sprinkler sprinkler) {
        String locationAlias = sprinkler.getLocationAlias();
        String locationCoordinates = sprinkler.getLocationCoordinates();

        Sprinkler updatedSprinkler = getSprinklerByDeviceId(deviceId);

        if(locationAlias != null) updatedSprinkler.setLocationAlias(locationAlias);
        if(locationCoordinates != null) updatedSprinkler.setLocationCoordinates(locationCoordinates);
        log.info("Updating Sprinkler with id: {}", deviceId);
        Sprinkler updated = sprinklerRepository.save(updatedSprinkler);
        streamBridge.send("sprinklerEventSupplier-out-0",
                SprinklerEvent.builder()
                        .eventName(SprinklerEvent.class.getTypeName())
                        .action("Sprinkler Updated")
                        .sprinklerId(deviceId)
                        .build());
        log.info("Produced Sprinkler:{} Updated event to sprinkler-event-topic!", deviceId);
        return updated;
    }
}
