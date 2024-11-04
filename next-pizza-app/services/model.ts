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

export interface CartItemCreateDto {
    productItemId: number;
    ingredientIds?: number[];
}

export interface OrderData {
    fullName: string;
    email: string;
    phone: string;
    comment?: string;
    address: string;
    totalAmount: number;
}

export interface OrderPayment {
    id: number;
    totalAmount: number;
    status: string;
}

export interface Payment {
    cardNumber: string;
}

export interface UserLoginDto {
    email: string;
    password: string;
}

export interface UserCreateEditDto extends UserLoginDto{
    fullName: string;
}

export interface UserReadDto {
    id: number;
    fullName: string;
    email: string;
}

export interface ErrorResponse {
    timestamp: number;
    message: string;
}

export interface Story {
    id: number;
    previewImage: string;
    items: StoryItem[];
}

export interface StoryItem {
    id: number;
    sourceUrl: string;
}