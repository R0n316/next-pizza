import {Category} from "@/services/model";
import {axiosInstance} from "@/services/instance";
import {ApiRoutes} from "@/services/constants";

export const getAll = async (): Promise<Category[]> => {
    try {
        const response = await axiosInstance.get<Category[]>(ApiRoutes.CATEGORIES);
        return response.data;
    } catch (error) {
        console.error('Error fetching data:', error);
        return [];
    }
}
