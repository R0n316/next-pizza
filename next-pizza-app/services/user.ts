import {ErrorResponse, UserCreateEditDto, UserReadDto} from "@/services/model";
import {axiosInstance} from "@/services/instance";
import {ApiRoutes} from "@/services/constants";
import axios, {AxiosResponse} from "axios";
import {ReadonlyRequestCookies} from "next/dist/server/web/spec-extension/adapters/request-cookies";

const getServerCookiesAxiosConfig = () => {
    const allCookies: ReadonlyRequestCookies = require('next/headers').cookies();
    const cookieString = allCookies.getAll().reduce((acc, cookie) => {
        return acc + (acc ? '; ' : '') + cookie.name + '=' + cookie.value;
    }, '');

    return {
        withCredentials: true,
        headers: {
            'Cookie': cookieString
        }
    };
}

export const getByEmail = async (): Promise<AxiosResponse<UserReadDto | ErrorResponse>> => {
    try {
        const config = getServerCookiesAxiosConfig();

        return await axiosInstance.get<UserReadDto>(ApiRoutes.USERS, config);
    } catch (error) {
        if (axios.isAxiosError(error)) {
            return error.response as AxiosResponse<ErrorResponse>;
        }
        throw error;
    }
}

export const updateUserInfo = async (userId: number, user: UserCreateEditDto): Promise<UserReadDto> => {
    return (await axiosInstance.patch<UserReadDto>(`${ApiRoutes.USERS}/${userId}`, user, {withCredentials: true})).data;
}