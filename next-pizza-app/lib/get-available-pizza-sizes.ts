import {pizzaSizes, PizzaType} from "@/constants/pizza";
import {ProductItem} from "@/services/model";
import {Variant} from "@/components/shared/group-variants";

export const getAvailablePizzaSizes = (
    items: ProductItem[],
    type: PizzaType
): Variant[] => {
    const filteredPizzasByType = items.filter(item => item.pizzaType === type);
    return pizzaSizes.map(item => ({
        name: item.name,
        value: item.value,
        disabled: !filteredPizzasByType.some(pizza => pizza!.size === Number(item.value))
    }));
}