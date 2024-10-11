import {axiosInstance} from "@/services/instance";
import {ApiRoutes} from "@/services/constants";
import {OrderData, OrderPayment} from "@/services/model";

export const create = async (data: OrderData): Promise<string> => {
    return (await axiosInstance.post<string>(ApiRoutes.ORDERS, data, {withCredentials: true, headers: {}})).data;
}

export const get = async (secret: string): Promise<OrderPayment> => {
    return (await axiosInstance.get<OrderPayment>(`${ApiRoutes.ORDERS}/${secret}`, {withCredentials: true, headers: {}})).data;
}