import {useEffect} from "react";
import {useCartStore} from "@/store";
import {CartItemCreateDto} from "@/services/model";
import {CartStateItem} from "@/lib/get-cart-details";

type ReturnProps = {
    totalAmount: number;
    items: CartStateItem[];
    loading: boolean;
    updateItemQuantity: (id: number, quantity: number) => void;
    removeCartItem: (id: number) => void;
    addCartItem: (values: CartItemCreateDto) => void;
}

export const useCart = (): ReturnProps => {
    const totalAmount = useCartStore(state => state.totalAmount);
    const items = useCartStore(state => state.items);
    const fetchCartItems = useCartStore(state => state.fetchCartItems);
    const updateItemQuantity = useCartStore(state => state.updateItemQuantity);
    const removeCartItem = useCartStore(state => state.removeCartItem);
    const loading = useCartStore(state => state.loading);
    const addCartItem = useCartStore(state => state.addCartItem);
    useEffect(() => {
        fetchCartItems();
    }, []);

    return {
        totalAmount,
        items,
        loading,
        updateItemQuantity,
        removeCartItem,
        addCartItem
    }
}