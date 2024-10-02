import React from 'react';
import {CheckoutItemDetails} from "@/components/shared/checkout-item-details";
import {ArrowRight, Package, Percent, Truck} from "lucide-react";
import {Button} from "@/components/ui";
import {WhiteBlock} from "@/components/shared/white-block";

const VAT = 15;
const DELIVERY_PRICE = 250;

interface Props {
    totalAmount: number;
}

export const CheckoutSidebar: React.FC<Props> = ({totalAmount}) => {
    const vatPrice = (totalAmount * VAT) / 100;
    const totalPrice = totalAmount + DELIVERY_PRICE + vatPrice;
    return (
        <WhiteBlock className={'p-6 sticky top-4'}>
            <div className={'flex flex-col gap-1'}>
                <span className={'text-xl'}>Итого:</span>
                <span className={'text-[34px] font-extrabold'}>{totalPrice} ₽</span>
            </div>
            <CheckoutItemDetails
                title={
                    <div className={'flex items-center'}>
                        <Package size={18} className={'mr-2 text-gray-300'}/>
                        Стоимость корзины:
                    </div>
                }
                value={`${totalAmount} ₽`}
            />
            <CheckoutItemDetails
                title={
                    <div className={'flex items-center'}>
                        <Percent size={18} className={'mr-2 text-gray-300'}/>
                        Налоги:
                    </div>
                }
                value={`${vatPrice} ₽`}
            />
            <CheckoutItemDetails
                title={
                    <div className={'flex items-center'}>
                        <Truck size={18} className={'mr-2 text-gray-300'}/>
                        Доставка:
                    </div>
                }
                value={`${DELIVERY_PRICE} ₽`}
            />
            <Button
                type={'submit'}
                className={'w-full h-14 rounded-2xl mt-6 text-base font-bold'}
            >
                Перейти к оплате
                <ArrowRight className={'w-5 ml-2'}/>
            </Button>
        </WhiteBlock>
    );
}