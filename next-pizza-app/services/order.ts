import {axiosInstance} from "@/services/instance";
import {ApiRoutes} from "@/services/constants";
import {OrderData} from "@/services/model";

export const create = async (data: OrderData): Promise<string> => {
    return (await axiosInstance.post<string>(ApiRoutes.ORDERS, data, {withCredentials: true, headers: {}})).data;
}