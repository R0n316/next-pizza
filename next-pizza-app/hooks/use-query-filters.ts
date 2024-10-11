import React, {useEffect} from "react";
import qs from "qs";
import {Filters} from "@/hooks/use-filters";
import {useRouter} from "next/navigation";

export const useQueryFilters = (filters: Filters) => {
    const isMounted = React.useRef(false);
    const router = useRouter();

    useEffect(() => {
        if(isMounted.current) {
            const params = {
                ...filters.prices,
                pizzaTypes: Array.from(filters.pizzaTypes),
                sizes: Array.from(filters.sizes),
                ingredients: Array.from(filters.selectedIngredients)
            };
            const query = qs.stringify(params, {
                arrayFormat: 'comma'
            });
            router.push(`/?${query}`, {
                scroll: false
            })
        }
        isMounted.current = true;
    }, [filters.pizzaTypes, filters.prices, filters.selectedIngredients, filters.sizes, router]);
}