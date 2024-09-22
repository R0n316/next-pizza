import React from 'react';
import {cn} from "@/lib/utils";
import {CircleCheck} from "lucide-react";
import Image from "next/image";

interface Props {
    imageUrl: string;
    name: string;
    price: number;
    active?: boolean;
    onClick?: VoidFunction;
    className?: string;
}

export const IngredientItem: React.FC<Props> = (
    {
        imageUrl,
        name,
        price,
        active,
        onClick,
        className
    }
) => {
    return (
        <div className={cn(
            'flex items-center border border-transparent flex-col p-1 rounded-md w-32',
            'text-center relative cursor-pointer shadow-md bg-white transition-all duration-300',
            {'border border-primary': active},
            className
        )} onClick={onClick}>
            <CircleCheck className={`absolute top-2 right-2 transition-all duration-300 
            ${active ? 'text-primary' : 'text-transparent'}`}/>

            <Image src={imageUrl} alt={name} width={110} height={110}/>
            <span className={'text-xs mb-1'}>{name}</span>
            <span className={'font-bold'}>{price} ₽</span>
        </div>
    );
}