import {ErrorResponse, UserReadDto} from "@/services/model";
import {axiosInstance} from "@/services/instance";
import {ApiRoutes} from "@/services/constants";
import axios, {AxiosResponse} from "axios";
import {ReadonlyRequestCookies} from "next/dist/server/web/spec-extension/adapters/request-cookies";

export const getByEmail = async (): Promise<AxiosResponse<UserReadDto | ErrorResponse>> => {
    const allCookies: ReadonlyRequestCookies = require('next/headers').cookies();
    try {
        // const allCookies = cookies().getAll();
        const cookieString = allCookies.getAll().reduce((acc, cookie) => {
            return acc + (acc ? '; ' : '') + cookie.name + '=' + cookie.value;
        }, '');


        // Установка заголовка Cookie в объекте конфигурации запроса
        const config = {
            withCredentials: true,
            headers: {
                'Cookie': cookieString
            }
        };

        // Отправка запроса
        return await axiosInstance.get<UserReadDto>(ApiRoutes.USERS, config);
    } catch (error) {
        if (axios.isAxiosError(error)) {
            return error.response as AxiosResponse<ErrorResponse>;
        }
        throw error;
    }
}