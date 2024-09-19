package ru.alex.nextpizzaapi.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = false, exclude = {"productItems", "ingredientProducts"})
@ToString(exclude = {"productItems", "ingredientProducts"})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductItem> productItems;

    @OneToMany(mappedBy = "product")
    private List<IngredientProduct> ingredientProducts;
}