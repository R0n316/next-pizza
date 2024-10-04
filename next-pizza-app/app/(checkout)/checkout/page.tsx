'use client';

import {
    CheckoutCart,
    CheckoutDeliveryAddress,
    CheckoutPersonalInfo,
    CheckoutSidebar,
    Container,
    Title
} from "@/components/shared";
import {useCart} from "@/hooks";
import {FormProvider, SubmitHandler, useForm} from "react-hook-form";
import {zodResolver} from '@hookform/resolvers/zod';
import {CheckoutFormData, checkoutFormSchema} from "@/constants";

export default function CheckoutPage() {
    const {
        totalAmount,
        items,
        updateItemQuantity,
        removeCartItem
    } = useCart();

    const form = useForm<CheckoutFormData>({
        resolver: zodResolver(checkoutFormSchema),
        defaultValues: {
            email: '',
            firstName: '',
            lastName: '',
            phone: '',
            address: '',
            comment: ''
        }
    });

    const onSubmit: SubmitHandler<CheckoutFormData> = (data) => {
        console.log(data);
    }

    const onClickCountButton = (id: number, quantity: number, type: 'plus' | 'minus') => {
        const newQuantity = type === 'plus'? quantity + 1 : quantity - 1;
        updateItemQuantity(id, newQuantity);
    };

    return (
        <Container className={'mt-5'}>
            <Title text={'Оформление заказа'} className={'font-extrabold mb-8 text-[36px]'}/>

            <FormProvider {...form}>
                <form onSubmit={form.handleSubmit(onSubmit)}>
                    <div className={'flex gap-10'}>
                        {/*Левая часть*/}
                        <div className={'flex flex-col gap-10 flex-1 mb-20'}>
                            <CheckoutCart
                                items={items}
                                onClickCountButton={onClickCountButton}
                                onClickRemoveCartItem={removeCartItem}
                            />

                            <CheckoutPersonalInfo/>

                            <CheckoutDeliveryAddress/>
                        </div>

                        {/*Правая часть*/}
                        <div className={'w-[450px]'}>
                            <CheckoutSidebar totalAmount={totalAmount}/>
                        </div>
                    </div>
                </form>
            </FormProvider>

        </Container>
    )
}