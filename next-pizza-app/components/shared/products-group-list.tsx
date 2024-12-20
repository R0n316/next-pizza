'use client';
import React, {useEffect, useRef} from 'react';
import {useIntersection} from 'react-use';
import {Title} from "@/components/shared/title";
import {cn} from "@/lib/utils";
import {ProductCard} from "@/components/shared/product-card";
import {useCategoryStore} from "@/store/category";
import {Product} from "@/services/model";

interface Props {
    title: string;
    items: Product[];
    categoryId: number;
    className?: string;
    listClassName?: string;
}

export const ProductsGroupList: React.FC<Props> = (
    {
        title,
        items,
        categoryId,
        className,
        listClassName
    }
) => {
    const setActiveCategoryId = useCategoryStore(state => state.setActiveId);
    const intersectionRef = useRef(null);
    const intersection = useIntersection(intersectionRef, {
        threshold: 0.4
    });

    useEffect(() => {
        if(intersection?.isIntersecting) {
            setActiveCategoryId(categoryId);
        }
    }, [categoryId, intersection?.isIntersecting, setActiveCategoryId, title]);

    return (
        <div className={className} id={title} ref={intersectionRef}>
            <Title text={title} size={'lg'} className={'font-extrabold mb-5'}/>
            <div className={cn('grid grid-cols-3 gap-[50px]', listClassName)}>
                {items.map((product) => (
                    <ProductCard
                        key={product.id}
                        id={product.id}
                        name={product.name}
                        price={product.items[0].price}
                        imageUrl={product.imageUrl}
                        ingredients={product.ingredients}
                    />
                ))}
            </div>
        </div>
    );
}