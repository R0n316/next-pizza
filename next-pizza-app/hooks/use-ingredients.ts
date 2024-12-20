import {useEffect, useState} from "react";
import {Ingredient} from "@/services/model";
import {Api} from "@/services/api-client";

export const useIngredients = () => {
    const [ingredients, setIngredients] = useState<Ingredient[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function fetchIngredients() {
            try {
                setLoading(true);
                const fetchedIngredients = await Api.ingredients.getAll();
                setIngredients(fetchedIngredients);
            } catch (error) {
                console.error(error);
            } finally {
                setLoading(false);
            }
        }
        fetchIngredients();
    }, []);
    return {
        ingredients,
        loading
    };
}