import {axiosInstance} from "@/services/instance";
import {ApiRoutes} from "@/services/constants";
import {Story} from "@/services/model";

export const getAll = async (): Promise<Story[]> => {
    return (await axiosInstance.get<Story[]>(ApiRoutes.STORIES)).data;
}