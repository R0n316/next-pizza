import {z} from "zod";

export const orderConfirmFormSchema = z.object({
    cardNumber: z.string()
        .length(16, 'номер карты должен быть длиной 16 символов')
        .refine(val => refineCardNumber(val), 'некорректный номер карты'),
    cardExpirationDate: z.string()
        .min(3, 'введите в формате М / Г')
        .max(5, 'введите в формате М / Г')
        .refine(val => refineExpirationDate(val), 'некорректный срок действия'),
    cardCvc: z.string()
        .length(3, 'некорректный CVC / CVV')
        .refine(val => refineCvc(val), 'некорректный CVC / CVV')
});

const refineCardNumber = (cardNumber: string): boolean => {
    const numbers = cardNumber.split('');
    let sum = 0;
    let alternate = false;
    for (let i = cardNumber.length - 1; i >= 0; i--) {
        let num = parseInt(numbers[i]);
        if (alternate) {
            num *= 2;
            if (num > 9) {
                num = (num % 10) + 1;
            }
        }
        sum += num;
        alternate = !alternate;
    }
    return (sum % 10 == 0);
}

const refineExpirationDate = (expirationDate: string): boolean => {
    const numbers: number[] = expirationDate.replace(/\s/g, '').split('/').map(Number);
    const currentYear = new Date().getFullYear() % 100;
    return (numbers[1] === currentYear ? numbers[0] >= new Date().getMonth() : numbers[0] >= 1) &&
        numbers[0] <= 12 && numbers[1] >= currentYear;
}

const refineCvc = (cvc: string): boolean => {
    const number = parseInt(cvc);
    return !isNaN(number) && number >= 0
}

export type OrderConfirmFormData = z.infer<typeof orderConfirmFormSchema>;