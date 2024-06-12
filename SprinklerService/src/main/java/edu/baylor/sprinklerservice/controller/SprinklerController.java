package edu.baylor.sprinklerservice.controller;

import edu.baylor.sprinklerservice.model.SprinklerRule;
import edu.baylor.sprinklerservice.service.SprinklerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/smart-home-hub/sprinkler")
public class SprinklerController {
    private final SprinklerService sprinklerService;

    @PostMapping("/{sprinklerId}")
    public void toggleOnOff(@PathVariable String sprinklerId) {
        sprinklerService.toggleOnOff(sprinklerId);
    }

    @GetMapping("/rules")
    public ResponseEntity<List<SprinklerRule>> getAllSprinklerRules() {
        log.info("GET Request received: getAllSprinklerRules");

        List<SprinklerRule> allRules = sprinklerService.getAllRules();
        log.info("{} Sprinkler rules retrieved from DB.", allRules.size());

        return ResponseEntity.ok(allRules);
    }

    @PostMapping("/rule")
    public ResponseEntity<SprinklerRule> createSprinklerRule(@RequestBody SprinklerRule sprinklerRule) {
        log.info("POST Request received: createSprinklerRule");
        return ResponseEntity.ok(sprinklerService.createRule(sprinklerRule));
    }

    @PostMapping("/valve")
    public ResponseEntity<Object> alterValveParams() { //TODO
        return null;
    }

}
