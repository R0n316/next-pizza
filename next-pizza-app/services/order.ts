import {axiosInstance} from "@/services/instance";
import {ApiRoutes} from "@/services/constants";
import {OrderData, OrderPayment, Payment} from "@/services/model";

export const create = async (data: OrderData): Promise<string> => {
    return (await axiosInstance.post<string>(ApiRoutes.ORDERS, data, {withCredentials: true, headers: {}})).data;
}

export const get = async (secret: string): Promise<OrderPayment> => {
    return (await axiosInstance.get<OrderPayment>(`${ApiRoutes.ORDERS}/${secret}`, {withCredentials: true, headers: {}})).data;
}

export const payForOrder = async (payment: Payment, secret: string): Promise<void> => {
    return (await axiosInstance.patch<void>(`${ApiRoutes.ORDERS}/${secret}`, payment, {withCredentials: true, headers: {}})).data;
}

export const cancelOrder = async (secret: string): Promise<void> => {
    return (await axiosInstance.delete<void>(`${ApiRoutes.ORDERS}/${secret}`, {withCredentials: true, headers: {}})).data;
}