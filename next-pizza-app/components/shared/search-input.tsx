'use client';
import React, {useEffect, useRef, useState} from 'react';
import {Search} from "lucide-react";
import {Input} from "@/components/ui";
import {cn} from "@/lib/utils";
import {useClickAway, useDebounce} from "react-use";
import Link from "next/link";
import Image from "next/image";
import {Api} from "@/services/api-client";
import {Product} from "@/services/model";

interface Props {
    className?: string;
}

export const SearchInput: React.FC<Props> = ({ className }) => {
    const [searchQuery, setSearchQuery] = useState('');
    const [focused, setFocused] = useState(false);
    const [products, setProducts] = useState<Product[]>([]);
    const ref = useRef(null);

    useClickAway(ref, () => {
        setFocused(false);
    });

    useDebounce(() => {
        Api.products.search(searchQuery).then(items => {
            setProducts(items);
        })},
        250,
        [searchQuery]);

    return (
        <>
            {focused && <div className={'fixed top-0 left-0 bottom-0 right-0 bg-black/50 z-30'}/>}
            <div ref={ref} className={cn(className, 'flex rounded-2xl flex-1 justify-between relative h-11 z-30')}>
                <Search className={'absolute top-1/2 translate-y-[-50%] left-3 h-5 text-gray-400'}/>
                <Input
                    className={'rounded-2xl border-none outline-none w-full bg-gray-100 pl-11'}
                    type={'text'}
                    placeholder={'Найти пиццу...'}
                    onFocus={() => setFocused(true)}
                    value={searchQuery}
                    onChange={e => setSearchQuery(e.target.value)}
                />

                {products.length > 0 && <div className={cn(
                    'absolute w-full bg-white rounded-xl py-2 top-14 shadow-md transition-all duration-200 invisible opacity-0 z-30',
                    focused && 'visible opacity-100 top-12',
                )}>
                    {
                        products.map(product => (
                            <Link key={product.id}
                                  href={`/product/${product.id}`}
                                  className={"flex items-center px-3 py-px hover:bg-primary/10"}>
                                <div className={'px-3 py-2 flex items-center gap-3'}>
                                    <Image
                                        className={'rounded-sm'}
                                        src={product.imageUrl}
                                        alt={product.name}
                                        width={32} height={32}
                                    />
                                    <span>{product.name}</span>
                                </div>
                            </Link>
                        ))
                    }
                </div>}
            </div>
        </>
    );
}