import {Api} from "@/services/api-client";

export interface GetSearchParams {
    query?: string;
    sortBy?: string;
    sizes?: string;
    pizzaTypes?: string;
    ingredients?: string;
    priceFrom?: string;
    priceTo?: string;
}

export interface QueryParams {
    sizes: string;
    pizzaTypes: string;
    ingredients: string;
    priceFrom: number;
    priceTo: number;
}

const DEFAULT_MIN_PRICE = 0;
const DEFAULT_MAX_PRICE = 1000;

export const findPizzas = async (params: GetSearchParams) => {
    const sizes = params.sizes || '';
    const pizzaTypes = params.pizzaTypes || '';
    const ingredients = params.ingredients || '';
    const priceFrom = Number(params.priceFrom) || DEFAULT_MIN_PRICE;
    const priceTo = Number(params.priceTo) || DEFAULT_MAX_PRICE;

    const queryParams: QueryParams = {
        sizes,
        pizzaTypes,
        ingredients,
        priceFrom,
        priceTo
    }

    return Api.categories.getAll(queryParams);
}