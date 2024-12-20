package ru.alex.nextpizzaapi.utils;

import lombok.experimental.UtilityClass;
import ru.alex.nextpizzaapi.database.entity.PizzaType;
import ru.alex.nextpizzaapi.dto.category.CategoryReadDto;
import ru.alex.nextpizzaapi.dto.category.ProductFilter;
import ru.alex.nextpizzaapi.dto.ingredient.IngredientReadDto;
import ru.alex.nextpizzaapi.dto.product.ProductReadDto;
import ru.alex.nextpizzaapi.dto.productItem.ProductItemReadDto;

import java.util.List;

@UtilityClass
public class CategoryUtils {

    public List<CategoryReadDto> filterCategories(List<CategoryReadDto> categories, ProductFilter filter) {
        return categories.stream()
                .map(category -> new CategoryReadDto(
                        category.id(),
                        category.name(),
                        filterProducts(category.products(), filter)
                ))
                .filter(category -> !category.products().isEmpty())
                .toList();
    }

    private List<ProductReadDto> filterProducts(List<ProductReadDto> products, ProductFilter filter) {
        return products.stream()
                .map(product -> new ProductReadDto(
                        product.id(),
                        product.name(),
                        product.imageUrl(),
                        product.categoryId(),
                        filterProductItems(product.items(), filter),
                        product.ingredients()
                ))
                .filter(product -> filterIngredients(product.ingredients(), filter))
                .filter(product -> !product.items().isEmpty())
                .toList();
    }

    private List<ProductItemReadDto> filterProductItems(List<ProductItemReadDto> items, ProductFilter filter) {
        return items.stream()
                .filter(item -> (filter.pizzaTypes() == null || filter.pizzaTypes().isEmpty() ||
                        filter.pizzaTypes().stream()
                                .map(index -> PizzaType.values()[index])
                                .toList()
                                .contains(item.pizzaType())))
                .filter(item -> (filter.sizes() == null || filter.sizes().isEmpty() || filter.sizes().contains(item.size())))
                .filter(item -> (filter.priceFrom() == null || item.price() >= filter.priceFrom()))
                .filter(item -> (filter.priceTo() == null || item.price() <= filter.priceTo()))
                .toList();
    }

    private boolean filterIngredients(List<IngredientReadDto> ingredients, ProductFilter filter) {
        if (filter.ingredients() == null || filter.ingredients().isEmpty()) {
            return true;
        }
        return ingredients.stream()
                .map(IngredientReadDto::id)
                .anyMatch(filter.ingredients()::contains);
    }
}

