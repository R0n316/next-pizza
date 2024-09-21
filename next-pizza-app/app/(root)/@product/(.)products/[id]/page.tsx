import {Api} from "@/services/api-client";
import {ChooseProductModal} from "@/components/shared";

export default async function ProductModalPage({params: {id}} : {params: {id: string}}) {
    const product = await Api.products.getById(Number(id));
    return <ChooseProductModal product={product} />
}