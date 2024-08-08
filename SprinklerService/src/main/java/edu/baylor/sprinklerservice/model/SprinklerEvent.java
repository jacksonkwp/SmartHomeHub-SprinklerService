package edu.baylor.sprinklerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class SprinklerEvent {
    private String eventName;
    private String action;
    private String sprinklerId;
}
