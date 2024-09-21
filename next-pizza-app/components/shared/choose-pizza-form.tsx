import React from 'react';
import {cn} from "@/lib/utils";
import {Title} from "@/components/shared/title";
import {Button} from "@/components/ui";
import {Product} from "@/services/model";
import {PizzaImage} from "@/components/shared/pizza-image";
import {GroupVariants} from "@/components/shared/group-variants";
import {PizzaSize, pizzaSizes, PizzaType} from "@/constants/pizza";

interface Props {
    name: string;
    imageUrl: string;
    ingredients: Product[];
    items: Product[];
    onClickAdd?: VoidFunction;
    className?: string;
}

export const ChoosePizzaForm: React.FC<Props> = (
    {
        name,
        items,
        ingredients,
        imageUrl,
        onClickAdd,
        className
    }
) => {
    const [size, setSize] = React.useState<PizzaSize>(20);
    const [type, setType] = React.useState<PizzaType>(0);
    const textDetails = '30 см, традиционное тесто 30';
    const totalPrice = 350;

    return (
        <div className={cn(className, 'flex flex-1')}>
            <PizzaImage
                imageUrl={imageUrl}
                size={size}
            />
            <div className={'w-[490px] bg-[#F7F6F5] p-7'}>
                <Title text={name} size={'md'} className={'font-extrabold mb-1'}/>
                <p className={'text-gray-400'}>{textDetails}</p>
                <GroupVariants
                    items={pizzaSizes}
                    value={String(size)}
                    onClick={value => setSize(Number(value) as PizzaSize)}
                />
                <Button
                    className={'h-[55px] px-10 text-base rounded-[18px] w-full mt-10'}>
                    Добавить в корзину за {totalPrice} ₽
                </Button>
            </div>
        </div>
    );
}