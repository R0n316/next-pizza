import {Api} from "@/services/api-client";
import {notFound} from "next/navigation";
import {Container, ProductForm} from "@/components/shared";
import React from "react";

export default async function ProductPage({params: {id}}: {params: {id: string}}) {
    const numId = Number(id);
    const product = await Api.products.getById(numId);
    const recommendedProducts = Api.categories.getRecommendedProducts(numId);

    if(!product) {
        return notFound();
    }


    return (
        <Container className={'flex flex-col my-10'}>
            <ProductForm product={product}/>
        </Container>
    )
}