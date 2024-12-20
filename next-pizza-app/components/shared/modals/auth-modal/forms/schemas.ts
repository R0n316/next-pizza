import {z} from "zod";

export const passwordSchema = z.string().min(4, {message: 'Пароль должен содержать не менее 4 символов'})

export const formLoginSchema = z.object({
    email: z.string().email({message: 'Введите корректную почту'}),
    password: passwordSchema,
});

export const formRegisterSchema = formLoginSchema.merge(
    z.object({
        fullName: z.string().min(5, {message: 'Введите ФИО'}),
        confirmPassword: passwordSchema
    }),
).refine(data => data.password === data.confirmPassword, {
    message: 'Пароли не совпадают',
    path: ['confirmPassword']
});

export type FormLoginData = z.infer<typeof formLoginSchema>;
export type FormRegisterData = z.infer<typeof formRegisterSchema>;