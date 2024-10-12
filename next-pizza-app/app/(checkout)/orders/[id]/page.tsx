'use client';

import {Container, FormTitleInput, Title} from "@/components/shared";
import {FormProvider, useForm} from "react-hook-form";
import {OrderConfirmFormData, orderConfirmFormSchema} from "@/constants";
import {zodResolver} from "@hookform/resolvers/zod";
import {Button, Skeleton} from "@/components/ui";
import toast from "react-hot-toast";
import {useOrder} from "@/hooks";
import {Api} from "@/services/api-client";

export default function OrderConfirmPage({params: {id}}: {params: {id: string}}) {
    const {order, loading} = useOrder(id);

    const form = useForm<OrderConfirmFormData>({
        resolver: zodResolver(orderConfirmFormSchema),
        defaultValues: {
            cardNumber: '',
            cardExpirationDate: '',
            cardCvc: ''
        }
    });

    const onSubmit = async (data: OrderConfirmFormData, buttonId: number) => {
        try {
            if (buttonId === 0) {
                await Api.order.payForOrder({
                    cardNumber: data.cardNumber
                }, id);
                toast.success('Заказ успешно оплачен! Информация отправлена на почту.', {
                    icon: '✅'
                });
            } else {
                await Api.order.cancelOrder(id);
                toast.success('Заказ успешно отменён. Информация отправлена на почту.', {
                    icon: '✅'
                });
            }

            setTimeout(() => {
                window.location.href = '/';
            }, 2000);
        } catch (err) {
            toast.error('Не удалось выполнить операцию', {
                icon: '❌'
            });
        }
    };

    if (order?.status !== 'PENDING') {
        return (
            <Container className={'mt-5'}>
                {
                    loading ?
                        <Skeleton className={'h-[54px] w-full mb-8'}/> :
                        <Title text={`Заказ #${order?.id} уже обработан`} className={'font-extrabold mb-8 text-[36px]'} />
                }

            </Container>
        );
    }

    return (
        <Container className={'mt-5'}>
            {
                loading ?
                    <Skeleton className={'h-[54px] w-full mb-8'}/> :
                    <Title text={`Оплата заказа #${order?.id} на сумму ${order?.totalAmount} ₽`} className={'font-extrabold mb-8 text-[36px]'}/>
            }

            <div className={'flex justify-center items-center'}>
                <div className={'bg-white flex flex-col rounded-3xl w-[674px] min-h-[490px] pb-10'}>
                    <div className={'flex items-center justify-center pt-7 pr-5 pl-5 border-b border-gray-100'}>
                        <Title text={'Реквизиты оплаты'} className={'font-extrabold'}/>
                    </div>
                    <div className={'px-9 pt-10'}>
                        <FormProvider {...form}>
                            <form>
                                <FormTitleInput name={'cardNumber'} title={'Номер карты'} titleClassName={'mb-1'}/>
                                <div className={'flex justify-between mt-10'}>
                                    <FormTitleInput name={'cardExpirationDate'} title={'Срок действия'}/>
                                    <FormTitleInput name={'cardCvc'} title={'CVC / CVV код'}/>
                                </div>
                                <div className={'flex flex-col gap-5 mt-[40px]'}>
                                    <Button
                                        className={'w-full h-[50px]'}
                                        type={'submit'}
                                        onClick={form.handleSubmit(data => onSubmit(data, 0))}
                                    >
                                        Перейти к оплате
                                    </Button>
                                    <Button
                                        className={'w-full h-[50px]'}
                                        variant={'outline'}
                                        type={'submit'}
                                        onClick={form.handleSubmit(data => onSubmit(data, 1))}
                                    >
                                        Отменить заказ
                                    </Button>
                                </div>
                            </form>
                        </FormProvider>
                    </div>
                </div>
            </div>
        </Container>
    )
}