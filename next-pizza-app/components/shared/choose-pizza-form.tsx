import React from 'react';
import {cn} from "@/lib/utils";
import {GroupVariants, IngredientItem, PizzaImage, Title} from "@/components/shared";
import {Button} from "@/components/ui";
import {Ingredient, ProductItem} from "@/services/model";
import {PizzaSize, PizzaType, pizzaTypes} from "@/constants/pizza";
import {getPizzaDetails} from "@/lib";
import {usePizzaOptions} from "@/hooks";

interface Props {
    name: string;
    imageUrl: string;
    ingredients: Ingredient[];
    items: ProductItem[];
    loading?: boolean;
    onSubmit: (itemId: number, ingredients: number[]) => void;
    className?: string;
}

export const ChoosePizzaForm: React.FC<Props> = (
    {
        name,
        items,
        ingredients,
        imageUrl,
        loading,
        onSubmit,
        className
    }
) => {
    const {
        size,
        type,
        selectedIngredients,
        availableSizes,
        currentItemId,
        setSize,
        setType,
        addIngredient
    } = usePizzaOptions(items);

    const {
        totalPrice,
        textDetails
    } = getPizzaDetails(type, size, items, ingredients, selectedIngredients);

    const handleClickAdd = () => {
        if(currentItemId) {
            onSubmit(currentItemId, Array.from(selectedIngredients));
        }
    };
    return (
        <div className={cn(className, 'flex flex-1')}>
            <PizzaImage
                imageUrl={imageUrl}
                size={size}
            />
            <div className={'w-[490px] bg-[#F7F6F5] p-7'}>
                <Title text={name} size={'md'} className={'font-extrabold mb-1'}/>
                <p className={'text-gray-400'}>{textDetails}</p>
                <div className={'flex flex-col gap-5 my-5'}>
                    <GroupVariants
                        items={availableSizes}
                        value={String(size)}
                        onClick={value => setSize(Number(value) as PizzaSize)}
                    />
                    <GroupVariants
                        items={pizzaTypes}
                        value={String(type)}
                        onClick={value => setType(Number(value) as PizzaType)}
                    />
                </div>
                <div className={'bg-gray-50 p-5 rounded-md h-[420px] overflow-auto scrollbar'}>
                    <div className={'grid grid-cols-3 gap-3'}>
                        {ingredients.map(ingredient => (
                            <IngredientItem
                                key={ingredient.id}
                                imageUrl={ingredient.imageUrl}
                                name={ingredient.name}
                                price={ingredient.price}
                                onClick={() => addIngredient(ingredient.id)}
                                active={selectedIngredients.has(ingredient.id)}
                            />
                        ))}
                    </div>
                </div>
                <Button
                    loading={loading}
                    onClick={handleClickAdd}
                    className={'h-[55px] px-10 text-base rounded-[18px] w-full mt-10'}>
                    Добавить в корзину за {totalPrice} ₽
                </Button>
            </div>
        </div>
    );
}