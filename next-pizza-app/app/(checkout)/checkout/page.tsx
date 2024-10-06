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
import {useEffect, useState} from "react";
import toast from "react-hot-toast";
import {OrderData} from "@/services/model";
import {Api} from "@/services/api-client";

export default function CheckoutPage() {
    const {
        totalAmount,
        items,
        updateItemQuantity,
        removeCartItem,
        loading
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

    const [initialLoading, setInitialLoading] = useState(true);
    useEffect(() => {
        if(!loading) {
            setInitialLoading(false);
        }
    }, [loading]);

    const [submitting, setSubmitting] = useState(false);

    const onSubmit: SubmitHandler<CheckoutFormData> = async (data) => {
        try {
            setSubmitting(true);
            const orderData: OrderData = {
                fullName: data.firstName + ' ' + data.lastName,                email: data.email,
                phone: data.phone,
                comment: data.comment,
                address: data.address
            }
            const url = await Api.order.create(orderData);
            toast.success('Заказ успешно оформлен! 📝 Переход на оплату...', {
                icon: '✅'
            });
            if(url) {
                location.href = url;
            }
        } catch (err) {
            setSubmitting(false);
            console.error(err);
            toast.error('Не удалось создать заказ', {
                icon: '❌'
            })
        }
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
                                loading={initialLoading}
                            />

                            <CheckoutPersonalInfo className={initialLoading ? 'opacity-40 pointer-events-none' : undefined}/>

                            <CheckoutDeliveryAddress className={initialLoading ? 'opacity-40 pointer-events-none' : undefined}/>
                        </div>

                        {/*Правая часть*/}
                        <div className={'w-[450px]'}>
                            <CheckoutSidebar
                                totalAmount={totalAmount}
                                loading={initialLoading || submitting}
                            />
                        </div>
                    </div>
                </form>
            </FormProvider>

        </Container>
    )
}