import React from 'react';
import {cn} from "@/lib/utils";
import {Title} from "@/components/shared/title";
import {Button} from "@/components/ui";
import Image from "next/image";

interface Props {
    name: string;
    price: number;
    loading?: boolean;
    imageUrl: string;
    onSubmit?: VoidFunction;
    className?: string;
}

export const ChooseProductForm: React.FC<Props> = (
    {
        name,
        imageUrl,
        price,
        loading,
        onSubmit,
        className
    }
) => {
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

                <Button
                    loading={loading}
                    onClick={() => onSubmit?.()}
                    className={'h-[55px] px-10 text-base rounded-[18px] w-full mt-10'}>
                    Добавить в корзину за {price} ₽
                </Button>
            </div>
        </div>
    );
}