import {axiosInstance} from "@/services/instance";
import {ApiRoutes} from "@/services/constants";
import {Product} from "@/services/model";

export const search = async (name: string): Promise<Product[]> => {
    return (await axiosInstance.get<Product[]>(ApiRoutes.SEARCH_PRODUCTS, {params: {name}})).data;
}