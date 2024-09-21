'use client';

import React from 'react';
import {Dialog} from "@/components/ui";
import {Product} from "@/services/model";
import {cn} from "@/lib/utils";
import {DialogContent, DialogTitle} from "@/components/ui/dialog";
import {ChooseProductForm} from "@/components/shared";
import {useRouter} from "next/navigation";
import {ChoosePizzaForm} from "@/components/shared/choose-pizza-form";

interface Props {
    product: Product;
    className?: string;
}

export const ChooseProductModal: React.FC<Props> = ({ product, className }) => {
    const router = useRouter();
    const isPizzaForm = Boolean(product.items && product.items.length > 1);
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
                        />
                    ) : (
                        <ChooseProductForm imageUrl={product.imageUrl} name={product.name}/>
                    )
                }
            </DialogContent>
        </Dialog>
    );
}