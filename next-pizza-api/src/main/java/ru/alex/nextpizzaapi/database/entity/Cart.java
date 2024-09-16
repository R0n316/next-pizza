package ru.alex.nextpizzaapi.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = false, exclude = "cartItems")
@ToString(exclude = "cartItems")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String token;

    @OneToMany(mappedBy = "cart")
    List<CartItem> cartItems;
}
