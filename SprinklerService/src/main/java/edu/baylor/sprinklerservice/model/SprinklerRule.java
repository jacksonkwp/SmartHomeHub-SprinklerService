package edu.baylor.sprinklerservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class SprinklerRule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String userId;

    @NotNull
    @Column(nullable = false)
    private String sprinklerDeviceId;

    @NotNull
    @Column(nullable = false)
    private String startTime;

    @NotNull
    @Column(nullable = false)
    private int durationMinutes;

    @ManyToOne
    private Sprinkler sprinkler;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedAt;
}
