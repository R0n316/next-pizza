import React, {useEffect} from "react";
import {PizzaSize, PizzaType} from "@/constants/pizza";
import {useSet} from "react-use";
import {getAvailablePizzaSizes} from "@/lib";
import {ProductItem} from "@/services/model";
import {Variant} from "@/components/shared/group-variants";

interface ReturnProps {
    size: PizzaSize;
    type: PizzaType;
    selectedIngredients: Set<number>;
    availableSizes: Variant[];
    currentItemId?: number;
    setSize: (size:PizzaSize) => void;
    setType: (type: PizzaType) => void;
    addIngredient: (id: number) => void;
}

export const usePizzaOptions = (items: ProductItem[]): ReturnProps => {
    const [size, setSize] = React.useState<PizzaSize>(20);
    const [type, setType] = React.useState<PizzaType>(0);
    const [selectedIngredients, {toggle: addIngredient}] = useSet(new Set<number>([]));

    const currentItemId = items.find(item => item.pizzaType === type && item.size === size)?.id;

    const availableSizes = getAvailablePizzaSizes(items, type);
    useEffect(() => {
        const isAvailableSize = availableSizes.find(item => !item.disabled && Number(item.value) === size);
        if(!isAvailableSize) {
            const availableSize = availableSizes?.find(item => !item.disabled);
            if(availableSize) {
                setSize(Number(availableSize.value) as PizzaSize);
            }
        }
    }, [type]);
    return {
        size,
        type,
        selectedIngredients,
        availableSizes,
        currentItemId,
        setSize,
        setType,
        addIngredient
    }
}