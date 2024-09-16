package ru.alex.nextpizzaapi.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_item_ingredient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_item_id", referencedColumnName = "id")
    private CartItem cartItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    private Ingredient ingredient;
}
