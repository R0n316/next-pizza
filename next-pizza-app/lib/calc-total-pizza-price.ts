import {Ingredient, ProductItem} from "@/services/model";
import {PizzaSize, PizzaType} from "@/constants/pizza";

/**
 * function to calculate the total price of pizza
 * @param type - type of dough of the selected pizza
 * @param size - size of the selected pizza
 * @param items - list of variations
 * @param ingredients - list of ingredients
 * @param selectedIngredients - selected ingredients (Set of ingredient ids)
 *
 * @returns total price
 */

export const calcTotalPizzaPrice = (
    type: PizzaType,
    size: PizzaSize,
    items: ProductItem[],
    ingredients: Ingredient[],
    selectedIngredients: Set<number>
) => {
    const pizzaPrice = items.find(item => item.pizzaType === type && item.size === size)?.price || 0;
    const totalIngredientsPrice = ingredients
        .filter(ingredient => selectedIngredients.has(ingredient.id))
        .reduce((acc, ingredient) => acc + ingredient.price, 0);
    return pizzaPrice + totalIngredientsPrice;
}