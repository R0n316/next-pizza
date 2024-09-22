export interface Ingredient {
    id: number;
    name: string;
    price: number;
    imageUrl: string;

}

export interface ProductItem {
    id: number;
    price: number;
    size: number;
    pizzaType: number;
}

export interface Product {
    id: number;
    name: string;
    imageUrl: string;
    items?: ProductItem[];
    ingredients: Ingredient[];
}

export interface Category {
    id: number;
    name: string;
    products: Product[];
}