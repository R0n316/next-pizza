package ru.alex.nextpizzaapi.listener;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import ru.alex.nextpizzaapi.database.entity.AuditableEntity;

import java.time.Instant;

public class AuditListener {
    @PrePersist
    public void prePersist(AuditableEntity entity) {
        entity.setCreatedAt(Instant.now());
    }
    @PreUpdate
    public void preUpdate(AuditableEntity entity) {
        entity.setUpdatedAt(Instant.now());
    }
}
