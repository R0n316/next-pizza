'use client';

import React from 'react';
import {useCartStore} from "@/store";
import toast from "react-hot-toast";
import {Product} from "@/services/model";
import {ChoosePizzaForm} from "@/components/shared/choose-pizza-form";
import {ChooseProductForm} from "@/components/shared/choose-product-form";

interface Props {
    product: Product;
    onSubmit?: VoidFunction;
}

export const ProductForm: React.FC<Props> = ({product, onSubmit: _onSubmit}) => {
    const addCartItem = useCartStore(state => state.addCartItem);
    const loading = useCartStore(state => state.loading);
    const firstItem = product.items[0]!;
    const isPizzaForm = Boolean(product.items && product.items.length > 1);

    const onSubmit = async (productItemId?: number, ingredientIds: number[] = []) => {
        try {
            const itemId = productItemId || firstItem.id;
            await addCartItem({
                productItemId: itemId,
                ingredientIds
            });
            toast.success('Продукт добавлен в корзину');
            _onSubmit?.();
        } catch (err) {
            toast.error('Ошибка при добавлении продукта в корзину');
            console.error(err);
        }
    }
    if(isPizzaForm) {
        return (
            <ChoosePizzaForm
                name={product.name}
                imageUrl={product.imageUrl}
                ingredients={product.ingredients}
                items={product.items}
                onSubmit={onSubmit}
                loading={loading}
            />
        )
    }
    return (
        <ChooseProductForm
            imageUrl={product.imageUrl}
            name={product.name}
            onSubmit={onSubmit}
            price={firstItem.price}
            loading={loading}
        />
    )
}