'use client';

import {Container, FormTitleInput, Title} from "@/components/shared";
import {FormProvider, useForm} from "react-hook-form";
import {OrderConfirmFormData, orderConfirmFormSchema} from "@/constants";
import {zodResolver} from "@hookform/resolvers/zod";
import {Button} from "@/components/ui";
import toast from "react-hot-toast";

export default function OrderConfirmPage({params: {id}}: {params: {id: string}}) {
    const form = useForm<OrderConfirmFormData>({
        resolver: zodResolver(orderConfirmFormSchema),
        defaultValues: {
            cardNumber: '',
            cardExpirationDate: '',
            cardCvc: ''
        }
    });

    const onSubmit = () => {
        try {
            toast.success('Заказ успешно оплачен', {
                icon: '✅'
            });
        } catch (err) {
            toast.error('Не удалось оплатить заказ', {
                icon: '❌'
            })
        }
    }
    return (
        <Container className={'mt-5'}>
            <Title text={`Оплата заказа #${id} на сумму 1234 ₽`} className={'font-extrabold mb-8 text-[36px]'}/>
            <div className={'flex justify-center items-center'}>
                <div className={'bg-white flex flex-col rounded-3xl w-[674px] min-h-[490px] pb-10'}>
                    <div className={'flex items-center justify-center pt-7 pr-5 pl-5 border-b border-gray-100'}>
                        <Title text={'Реквизиты оплаты'} className={'font-extrabold'}/>
                    </div>
                    <div className={'px-9 pt-10'}>
                        <FormProvider {...form}>
                            <form onSubmit={form.handleSubmit(onSubmit)}>
                                <FormTitleInput name={'cardNumber'} title={'Номер карты'} titleClassName={'mb-1'}/>
                                <div className={'flex justify-between mt-10'}>
                                    <FormTitleInput name={'cardExpirationDate'} title={'Срок действия'}/>
                                    <FormTitleInput name={'cardCvc'} title={'CVC / CVV код'}/>
                                </div>
                                <div className={'flex flex-col gap-5 mt-[40px]'}>
                                    <Button className={'w-full h-[50px]'} type={'submit'}>
                                        Перейти к оплате
                                    </Button>
                                    <Button className={'w-full h-[50px]'} variant={'outline'} type={'submit'}>
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