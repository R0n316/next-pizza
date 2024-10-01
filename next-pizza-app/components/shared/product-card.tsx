import React from 'react';
import Link from "next/link";
import Image from "next/image";
import {Title} from "@/components/shared/title";
import {Button} from "@/components/ui";
import {Plus} from "lucide-react";
import {Ingredient} from "@/services/model";

interface Props {
    id: number;
    name: string;
    price: number;
    imageUrl: string;
    ingredients: Ingredient[];
    className?: string;
}

export const ProductCard: React.FC<Props> = ({ id, name, price, imageUrl, ingredients, className }) => {
    return (
        <div className={className}>
            <Link href={`/products/${id}`}>
                <div className={'flex justify-center p-6 bg-secondary rounded-lg h-[260px]'}>
                    <Image src={imageUrl} alt={name} className={'w-[215px] h-[215px]'} width={215} height={215}/>
                </div>
                <Title text={name} size={'sm'} className={'mb-1 mt-3 font-bold'}/>
                <p className={'text-sm text-gray-400'}>
                    {
                        ingredients.map(ingredient => ingredient.name).join(', ')
                    }
                </p>
                <div className={'flex justify-between items-center mt-4'}>
                    <span className={'text-[20px]'}>
                        от <b>{price} ₽</b>
                    </span>
                    <Button variant={'secondary'} className={'text-base font-bold'}>
                        <Plus size={20} className={'mr-1'}/>
                        Добавить
                    </Button>
                </div>
            </Link>
        </div>
    );
}