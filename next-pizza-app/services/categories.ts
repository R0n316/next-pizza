import {Category} from "@/services/model";
import {axiosInstance} from "@/services/instance";
import {ApiRoutes} from "@/services/constants";
import {QueryParams} from "@/lib/find-pizzas";

export const getAll = async (queryParams: QueryParams): Promise<Category[]> => {
    try {
        const response = await axiosInstance.get<Category[]>(ApiRoutes.CATEGORIES, {params: queryParams});
        return response.data;
    } catch (error) {
        console.error('Error fetching data:', error);
        return [];
    }
}

export const getRecommendedProducts = async (id: number): Promise<Category> => {
    return (await axiosInstance.get<Category>(`${ApiRoutes.CATEGORIES}/${id}/recommended`, {params: {id}})).data;
}