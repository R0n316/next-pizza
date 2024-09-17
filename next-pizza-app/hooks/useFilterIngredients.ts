import { useEffect, useState } from "react";
import { Api } from "@/services/api-client";
import { IngredientItem } from "@/services/model";
import {useSet} from "react-use";

interface ReturnProps {
    ingredients: IngredientItem[];
    loading: boolean;
    selectedIngredients: Set<string>;
    onAddId: (id: string) => void;
}

export const useFilterIngredients = (): ReturnProps => {
    const [ingredients, setIngredients] = useState<IngredientItem[]>([]);
    const [loading, setLoading] = useState(true);

    const [selectedIds, {toggle}] = useSet(new Set<string>([]));

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

    return { ingredients, loading, onAddId: toggle, selectedIngredients: selectedIds };
}
