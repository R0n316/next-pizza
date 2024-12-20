import {ErrorResponse, UserLoginDto, UserCreateEditDto} from "@/services/model";
import {axiosInstance} from "@/services/instance";
import {ApiRoutes} from "@/services/constants";
import axios, {AxiosResponse} from "axios";

export const signIn = async (data: UserLoginDto): Promise<AxiosResponse<void | ErrorResponse>> => {
    try {
        return await axiosInstance.post<void>(
            `${ApiRoutes.AUTH}/login`,
            data,
            {withCredentials: true, headers: {}}
        );
    } catch (error) {
        if (axios.isAxiosError(error)) {
            return error.response as AxiosResponse<ErrorResponse>;
        }
        throw error;
    }
};

export const register = async (data: UserCreateEditDto): Promise<AxiosResponse<void | ErrorResponse>> => {
    try {
        return await axiosInstance.post<void>(
            `${ApiRoutes.AUTH}/register`,
            data,
            {withCredentials: true, headers: {}}
        );
    } catch (error) {
        if (axios.isAxiosError(error)) {
            return error.response as AxiosResponse<ErrorResponse>;
        }
        throw error;
    }
}

export const validateJwt = async (jwt: string): Promise<AxiosResponse<void | ErrorResponse>> => {
    try {
        return await axiosInstance.patch<void>(`${ApiRoutes.AUTH}/refresh-token`, {jwt});
    } catch (error) {
        if (axios.isAxiosError(error)) {
            return error.response as AxiosResponse<ErrorResponse>;
        }
        throw error;
    }

}