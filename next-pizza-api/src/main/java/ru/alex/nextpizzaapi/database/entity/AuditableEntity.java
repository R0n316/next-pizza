package ru.alex.nextpizzaapi.database.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import ru.alex.nextpizzaapi.listener.AuditListener;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditListener.class)
public abstract class AuditableEntity {
    private Instant createdAt;
    private Instant updatedAt;
}