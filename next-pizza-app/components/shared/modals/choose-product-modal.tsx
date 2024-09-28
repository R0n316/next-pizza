'use client';

import React from 'react';
import {Dialog} from "@/components/ui";
import {Product} from "@/services/model";
import {cn} from "@/lib/utils";
import {DialogContent, DialogTitle} from "@/components/ui/dialog";
import {ChooseProductForm} from "@/components/shared";
import {useRouter} from "next/navigation";
import {ChoosePizzaForm} from "@/components/shared/choose-pizza-form";
import {useCartStore} from "@/store";

interface Props {
    product: Product;
    className?: string;
}

export const ChooseProductModal: React.FC<Props> = ({ product, className }) => {
    const router = useRouter();
    const firstItem = product.items[0]!;
    const isPizzaForm = Boolean(product.items && product.items.length > 1);
    const addCartItem = useCartStore(state => state.addCartItem);

    const onAddProduct = () => {
        addCartItem({
            productItemId: firstItem.id,
            ingredientIds: []
        });
    };
    const onAddPizza = (productItemId: number, ingredients: number[]) => {
        addCartItem({
            productItemId,
            ingredientIds: ingredients
        });
    };

    return (
        <Dialog open={Boolean(product)} onOpenChange={() => router.back()}>
            <DialogTitle/>
            <DialogContent
                className={cn(
                'p-0 w-[1060px] max-w-[1060px] min-h-[500px] bg-white overflow-hidden',
                className
            )}>
                {
                    isPizzaForm ? (
                        <ChoosePizzaForm
                            name={product.name}
                            imageUrl={product.imageUrl}
                            ingredients={product.ingredients}
                            items={product.items}
                            onSubmit={onAddPizza}
                        />
                    ) : (
                        <ChooseProductForm
                            imageUrl={product.imageUrl}
                            name={product.name}
                            onSubmit={onAddProduct}
                            price={firstItem.price}
                        />
                    )
                }
            </DialogContent>
        </Dialog>
    );
}