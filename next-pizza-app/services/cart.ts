import {Cart} from "@/services/model";
import {axiosInstance} from "@/services/instance";
import {ApiRoutes} from "@/services/constants";

export const get = async (): Promise<Cart> => {
    return (await axiosInstance.get<Cart>(ApiRoutes.CART, { withCredentials: true })).data;
}

export const updateItemQuantity = async (id: number, quantity: number): Promise<Cart> => {
    return (await axiosInstance.patch<Cart>(`${ApiRoutes.CART}/${id}`, {quantity}, {withCredentials: true})).data;
}