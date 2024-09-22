import React, {useEffect} from 'react';
import {cn} from "@/lib/utils";
import {GroupVariants, IngredientItem, PizzaImage, Title} from "@/components/shared";
import {Button} from "@/components/ui";
import {Ingredient, ProductItem} from "@/services/model";
import {mapPizzaType, PizzaSize, pizzaSizes, PizzaType, pizzaTypes} from "@/constants/pizza";
import {useSet} from "react-use";

interface Props {
    name: string;
    imageUrl: string;
    ingredients: Ingredient[];
    items: ProductItem[];
    onClickAddCart?: VoidFunction;
    className?: string;
}

export const ChoosePizzaForm: React.FC<Props> = (
    {
        name,
        items,
        ingredients,
        imageUrl,
        onClickAddCart,
        className
    }
) => {
    const [size, setSize] = React.useState<PizzaSize>(20);
    const [type, setType] = React.useState<PizzaType>(0);

    const [selectedIngredients, {toggle: addIngredient}] = useSet(new Set<number>([]));

    const textDetails = `${size} см, ${mapPizzaType[type]} тесто`;

    const pizzaPrice = items.find(item => item.pizzaType === type && item.size === size)?.price || 0;
    const totalIngredientsPrice = ingredients
        .filter(ingredient => selectedIngredients.has(ingredient.id))
        .reduce((acc, ingredient) => acc + ingredient.price, 0);
    const totalPrice = pizzaPrice + totalIngredientsPrice;

    const handleClickAdd = () => {
        onClickAddCart?.();
        console.log(
            size,
            type,
            selectedIngredients
        );
    };

    const availablePizzas = items.filter(item => item.pizzaType === type);
    const availablePizzaSizes = pizzaSizes.map(item => ({
        name: item.name,
        value: item.value,
        disabled: !availablePizzas.some(pizza => pizza!.size === Number(item.value))
    }));

    useEffect(() => {
        const isAvailableSize = availablePizzaSizes.find(item => !item.disabled && Number(item.value) === size);
        if(!isAvailableSize) {
            const availableSize = availablePizzaSizes?.find(item => !item.disabled);
            if(availableSize) {
                setSize(Number(availableSize.value) as PizzaSize);
            }
        }
    }, [type])
    console.log(availablePizzas);
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
                        items={availablePizzaSizes}
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
                    onClick={handleClickAdd}
                    className={'h-[55px] px-10 text-base rounded-[18px] w-full mt-10'}>
                    Добавить в корзину за {totalPrice} ₽
                </Button>
            </div>
        </div>
    );
}