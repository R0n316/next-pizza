import {create} from "zustand";
import {Api} from "@/services/api-client";
import {CartStateItem, getCartDetails} from "@/lib/get-cart-details";
import {CartItemCreateDto} from "@/services/model";

export interface CartState {
    loading: boolean;
    error: boolean;
    totalAmount: number;
    items: CartStateItem[];

    fetchCartItems: () => Promise<void>;
    updateItemQuantity: (id: number, quantity: number) => Promise<void>;
    addCartItem: (values: CartItemCreateDto) => Promise<void>;
    removeCartItem: (id: number) => Promise<void>;
}

export const useCartStore = create<CartState>((set) => ({
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
            set(state => ({
                loading: true,
                error: false,
                items: state.items.map(item => item.id === id ? {...item, disabled: true} : item)
            }));
            const cart = await Api.cart.removeCartItem(id);
            set(getCartDetails(cart))
        } catch (error) {
            console.error(error);
            set({error: true});
        } finally {
            set(state => ({
                loading: false,
                items: state.items.map(item => ({...item, disabled: false}))
            }))
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
    addCartItem: async (values: CartItemCreateDto) => {
        try {
            set({loading: true, error: false});
            const cart = await Api.cart.addCartItem(values);
            set(getCartDetails(cart))
        } catch (error) {
            console.error(error);
            set({error: true});
        } finally {
            set({loading: false});
        }
    }
}))