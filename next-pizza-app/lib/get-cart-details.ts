import {Cart} from "@/services/model";
import {calcCartItemPrice} from "@/lib/calc-cart-item-price";

export type CartStateItem = {
    id: number;
    quantity: number;
    name: string;
    imageUrl: string;
    price: number;
    disabled?: boolean;
    pizzaSize?: number | null;
    pizzaType?: number | null;
    ingredients: Array<{name: string; price: number}>
}

interface ReturnProps {
    items: CartStateItem[];
    totalAmount: number;
}

export const getCartDetails = (data: Cart): ReturnProps => {
    const items: CartStateItem[] = data.items.map(item => ({
        id: item.id,
        quantity: item.quantity,
        name: item.productItem.product.name,
        imageUrl: item.productItem.product.imageUrl,
        disabled: false,
        price: calcCartItemPrice(item),
        pizzaSize: item.productItem.size,
        pizzaType: item.productItem.pizzaType,
        ingredients: item.ingredients.map(ingredient => ({
            name: ingredient.name,
            price: ingredient.price
        }))
    }));
    return {
        items,
        totalAmount: data.totalAmount
    }
}