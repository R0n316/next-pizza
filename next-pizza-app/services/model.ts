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
    product: Product;
}

export interface Product {
    id: number;
    name: string;
    imageUrl: string;
    items: ProductItem[];
    ingredients: Ingredient[];
}

export interface Category {
    id: number;
    name: string;
    products: Product[];
}

export interface Cart {
    id: number;
    userId: number;
    token: string;
    totalAmount: number;
    items: CartItem[];
}

export interface CartItem {
    id: number;
    productItem: ProductItem;
    ingredients: Ingredient[];
    quantity: number;
    createdAt: string
}