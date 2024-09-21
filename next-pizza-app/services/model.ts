export interface Ingredient {
    id: number;
    name: string;
    price: number;
    imageUrl: string;

}

export interface Item {
    id: number;
    price: number;
    size: number;
    pizzaType: string;
}

export interface Product {
    id: number;
    name: string;
    imageUrl: string;
    items?: Item[];
    ingredients: Ingredient[];
}

export interface Category {
    id: number;
    name: string;
    products: Product[];
}