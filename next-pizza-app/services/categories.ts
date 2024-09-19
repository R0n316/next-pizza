import {Category} from "@/services/model";
import {axiosInstance} from "@/services/instance";
import {ApiRoutes} from "@/services/constants";

export const getAll = async (): Promise<Category[]> => {
    return (await axiosInstance.get<Category[]>(ApiRoutes.CATEGORIES)).data;
}