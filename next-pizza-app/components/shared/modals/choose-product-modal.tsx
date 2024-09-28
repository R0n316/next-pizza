'use client';

import {Dialog} from "@/components/ui";
import {Product} from "@/services/model";
import {cn} from "@/lib/utils";
import {DialogContent, DialogTitle} from "@/components/ui/dialog";
import {ChooseProductForm} from "@/components/shared";
import {useRouter} from "next/navigation";
import {ChoosePizzaForm} from "@/components/shared/choose-pizza-form";
import {useCartStore} from "@/store";
import toast from "react-hot-toast";

interface Props {
    product: Product;
    className?: string;
}

export const ChooseProductModal: React.FC<Props> = ({ product, className }) => {
    const router = useRouter();
    const firstItem = product.items[0]!;
    const isPizzaForm = Boolean(product.items && product.items.length > 1);
    const addCartItem = useCartStore(state => state.addCartItem);
    const loading = useCartStore(state => state.loading);

    const onSubmit = async (productItemId?: number, ingredientIds: number[] = []) => {
        try {
            const itemId = productItemId || firstItem.id;
            await addCartItem({
                productItemId: itemId,
                ingredientIds
            });
            toast.success('Продукт добавлен в корзину');
            router.back();
        } catch (err) {
            toast.error('Ошибка при добавлении продукта в корзину');
            console.error(err);
        }
    }

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
                            onSubmit={onSubmit}
                            loading={loading}
                        />
                    ) : (
                        <ChooseProductForm
                            imageUrl={product.imageUrl}
                            name={product.name}
                            onSubmit={onSubmit}
                            price={firstItem.price}
                            loading={loading}
                        />
                    )
                }
            </DialogContent>
        </Dialog>
    );
}