import {Ingredient, ProductItem} from "@/services/model";
import {mapPizzaType, PizzaSize, PizzaType} from "@/constants/pizza";
import {calcTotalPizzaPrice} from "@/lib/calc-total-pizza-price";

export const getPizzaDetails = (
    type: PizzaType,
    size: PizzaSize,
    items: ProductItem[],
    ingredients: Ingredient[],
    selectedIngredients: Set<number>
) => {
    const textDetails = `${size} см, ${mapPizzaType[type]} тесто`;
    const totalPrice = calcTotalPizzaPrice(type, size, items, ingredients, selectedIngredients);

    return {totalPrice, textDetails}
}