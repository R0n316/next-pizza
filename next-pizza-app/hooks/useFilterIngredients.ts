import { useEffect, useState } from "react";
import { Api } from "@/services/api-client";
import { IngredientItem } from "@/services/model";

interface ReturnProps {
    ingredients: IngredientItem[];
    loading: boolean;
}

export const useFilterIngredients = (): ReturnProps => {
    const [ingredients, setIngredients] = useState<IngredientItem[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function fetchIngredients() {
            try {
                setLoading(true);
                const fetchedIngredients = await Api.ingredients.getAll(); // ждем завершения промиса
                setIngredients(fetchedIngredients); // обновляем состояние
            } catch (error) {
                console.error(error);
            } finally {
                setLoading(false);
            }
        }
        fetchIngredients();
    }, []);

    return { ingredients, loading };
}
