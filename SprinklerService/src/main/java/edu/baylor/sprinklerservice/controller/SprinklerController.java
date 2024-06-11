package edu.baylor.sprinklerservice.controller;

import edu.baylor.sprinklerservice.model.SprinklerRule;
import edu.baylor.sprinklerservice.service.SprinklerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/smart-home-hub/sprinkler")
public class SprinklerController {
    private final SprinklerService sprinklerService;

    @GetMapping("/rules")
    public ResponseEntity<List<SprinklerRule>> getAllSprinklerRules() {
        log.info("Request received: getAllSprinklerRules");

        List<SprinklerRule> allRules = sprinklerService.getAllRules();
        log.info("{} Sprinkler rules retrieved from DB.", allRules.size());

        return ResponseEntity.ok(allRules);
    }

    @PostMapping("/rule")
    public ResponseEntity<SprinklerRule> createSprinklerRule(@RequestBody SprinklerRule sprinklerRule) {
        return ResponseEntity.ok(sprinklerService.createRule(sprinklerRule));
    }
}
