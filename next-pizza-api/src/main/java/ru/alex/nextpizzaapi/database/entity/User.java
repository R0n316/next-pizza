package ru.alex.nextpizzaapi.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name")
    private String fullName;

    private String email;
    private String password;
    private String provider;
    private LocalDate verified;

    @Column(name = "provider_id")
    private String providerId;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}