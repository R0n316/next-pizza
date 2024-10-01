'use client';

import {Dialog} from "@/components/ui";
import {Product} from "@/services/model";
import {cn} from "@/lib/utils";
import {DialogContent} from "@/components/ui/dialog";
import {ProductForm} from "@/components/shared";
import {useRouter} from "next/navigation";
import React from "react";

interface Props {
    product: Product;
    className?: string;
}

export const ChooseProductModal: React.FC<Props> = ({ product, className }) => {
    const router = useRouter();

    return (
        <Dialog open={Boolean(product)} onOpenChange={router.back}>
            <DialogContent
                className={cn(
                'p-0 w-[1060px] max-w-[1060px] min-h-[500px] bg-white overflow-hidden',
                className
            )}>
                <ProductForm product={product} onSubmit={router.back}/>
            </DialogContent>
        </Dialog>
    );
}