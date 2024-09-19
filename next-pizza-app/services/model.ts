export interface IngredientItem {
    id: number,
    name: string
}

export interface Item {
    id: number,
    price: number,
    size: number,
    pizzaType: string,
}

export interface Product {
    id: number,
    name: string,
    imageUrl: string,
    items?: Item[],
    ingredients?: IngredientItem[]
}

export interface Category {
    id: number,
    name: string,
    products: Product[]
}