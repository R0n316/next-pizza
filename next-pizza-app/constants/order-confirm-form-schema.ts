import {z} from "zod";

export const orderConfirmFormSchema = z.object({
    cardNumberPart: z.string().length(4),
    cardExpirationDate: z.string().min(5).max(7),
    cardCvc: z.string().length(3)
});

export type OrderConfirmFormData = z.infer<typeof orderConfirmFormSchema>;