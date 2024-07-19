package edu.baylor.sprinklerservice.controller;

import edu.baylor.sprinklerservice.model.Sprinkler;
import edu.baylor.sprinklerservice.model.SprinklerRule;
import edu.baylor.sprinklerservice.model.SprinklerStatus;
import edu.baylor.sprinklerservice.service.SprinklerService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/smart-home-hub/sprinkler")
public class SprinklerController {
    private final SprinklerService sprinklerService;

    @GetMapping("/all")
    @RolesAllowed({"USER", "ADMIN"})
    public ResponseEntity<List<Sprinkler>> getAllSprinklers() {
        log.info("GET Request received: getAllSprinklers");
        return ResponseEntity.ok(sprinklerService.getAllSprinklers());
    }

    @GetMapping("/{deviceId}")
    @RolesAllowed({"USER", "ADMIN"})
    public ResponseEntity<Sprinkler> getSprinkler(@PathVariable String deviceId) {
        return ResponseEntity.ok(sprinklerService.getSprinklerByDeviceId(deviceId));
    }

    @PostMapping("/add")
    @RolesAllowed({"USER", "ADMIN"})
    public ResponseEntity<Sprinkler> addSprinkler(@RequestBody Sprinkler sprinkler,
                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        return ResponseEntity.ok(sprinklerService.addSprinkler(sprinkler, authToken));
    }

    @PatchMapping("/update/{deviceId}")
    @RolesAllowed({"USER", "ADMIN"})
    public ResponseEntity<Sprinkler> updateSprinkler(@PathVariable String deviceId, @RequestBody Sprinkler sprinkler) {
        return ResponseEntity.ok(sprinklerService.updateSprinkler(deviceId, sprinkler));
    }

    @DeleteMapping("/{deviceId}")
    @RolesAllowed({"ADMIN"})
    public ResponseEntity<Object> deleteSprinkler(@PathVariable String deviceId) {
        sprinklerService.deleteSprinkler(deviceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{sprinklerId}")
    @RolesAllowed({"USER", "ADMIN"})
    public SprinklerStatus getSprinklerStatus(@PathVariable String sprinklerId) {
        return sprinklerService.getSprinklerStatus(sprinklerId);
    }

    @PostMapping("/toggle/{sprinklerId}")
    @RolesAllowed({"USER", "ADMIN"})
    public SprinklerStatus toggleOnOff(@PathVariable String sprinklerId) {
        return sprinklerService.toggleOnOff(sprinklerId);
    }

    @GetMapping("/rules")
    @RolesAllowed({"USER", "ADMIN"})
    public ResponseEntity<List<SprinklerRule>> getAllSprinklerRules() {
        log.info("GET Request received: getAllSprinklerRules");

        List<SprinklerRule> allRules = sprinklerService.getAllRules();
        log.info("{} Sprinkler rules retrieved from DB.", allRules.size());

        return ResponseEntity.ok(allRules);
    }

    @PostMapping("/rule")
    @RolesAllowed({"USER", "ADMIN"})
    public ResponseEntity<SprinklerRule> createSprinklerRule(@RequestBody SprinklerRule sprinklerRule) {
        log.info("POST Request received: createSprinklerRule");
        return ResponseEntity.ok(sprinklerService.createRule(sprinklerRule));
    }

    @PatchMapping("/rule/{ruleId}")
    @RolesAllowed({"USER", "ADMIN"})
    public ResponseEntity<SprinklerRule> updateSprinklerRule(@PathVariable Long ruleId, @RequestBody SprinklerRule sprinklerRule) {
        log.info("Patch Request received: updateSprinklerRule");
        return ResponseEntity.ok(sprinklerService.updateRule(ruleId, sprinklerRule));
    }

    @PostMapping("/valve")
    @RolesAllowed({"USER", "ADMIN"})
    public ResponseEntity<Object> alterValveParams() { //TODO
        return null;
    }
}
