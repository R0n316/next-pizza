import {axiosInstance} from "@/services/instance";
import {ApiRoutes} from "@/services/constants";
import {IngredientItem} from "@/services/model";


export const getAll = async (): Promise<IngredientItem[]> => {
    return (await axiosInstance.get<IngredientItem[]>(ApiRoutes.INGREDIENTS)).data;
}