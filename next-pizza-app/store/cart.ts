import {create} from "zustand";
import {Api} from "@/services/api-client";
import {CartStateItem, getCartDetails} from "@/lib/get-cart-details";

export interface CartState {
    loading: boolean;
    error: boolean;
    totalAmount: number;
    items: CartStateItem[];

    fetchCartItems: () => Promise<void>;
    updateItemQuantity: (id: number, quantity: number) => Promise<void>;
    addCartItem: (values: any) => Promise<void>;
    removeCartItem: (id: number) => Promise<void>;
}

export const useCartStore = create<CartState>((set, get) => ({
    items: [],
    error: false,
    loading: true,
    totalAmount: 0,
    fetchCartItems: async () => {
        try {
            set({loading: true, error: false});
            const cart = await Api.cart.get();
            set(getCartDetails(cart))
        } catch (error) {
            console.error(error);
            set({error: true});
        } finally {
            set({loading: false});
        }
    },
    removeCartItem: async (id: number) => {
        try {
            set({loading: true, error: false});
            const cart = await Api.cart.removeCartItem(id);
            set(getCartDetails(cart))
        } catch (error) {
            console.error(error);
            set({error: true});
        } finally {
            set({loading: false});
        }
    },
    updateItemQuantity: async (id: number, quantity: number) => {
        try {
            set({loading: true, error: false});
            const cart = await Api.cart.updateItemQuantity(id, quantity);
            set(getCartDetails(cart))
        } catch (error) {
            console.error(error);
            set({error: true});
        } finally {
            set({loading: false});
        }
    },
    addCartItem: async (values: any) => {}
}))