import React from 'react';
import {cn} from "@/lib/utils";
import {Title} from "@/components/shared/title";
import {Button} from "@/components/ui";
import Image from "next/image";
import {Product} from "@/services/model";

interface Props {
    name: string;
    imageUrl: string;
    onClickAdd?: VoidFunction;
    className?: string;
}

export const ChooseProductForm: React.FC<Props> = (
    {
        name,
        imageUrl,
        onClickAdd,
        className
    }
) => {
    const textDetails = '30 см, традиционное тесто 30';
    const totalPrice = 350;
    return (
        <div className={cn(className, 'flex flex-1')}>
            <div className={'flex items-center justify-center flex-1 relative w-full'}>
                <Image
                    src={imageUrl}
                    alt={name}
                    className={'relative left-2 top-2 transition-all z-10 duration-300 w-[350px] h-[350px]'}
                    width={584}
                    height={584}
                />
            </div>
            <div className={'w-[490px] bg-[#F7F6F5] p-7'}>
                <Title text={name} size={'md'} className={'font-extrabold mb-1'}/>
                <p className={'text-gray-400'}>{textDetails}</p>

                <Button
                    className={'h-[55px] px-10 text-base rounded-[18px] w-full mt-10'}>
                    Добавить в корзину за {totalPrice} ₽
                </Button>
            </div>
        </div>
    );
}