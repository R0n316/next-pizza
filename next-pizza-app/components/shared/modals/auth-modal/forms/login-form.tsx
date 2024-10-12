import React from 'react';
import {FormProvider, useForm} from "react-hook-form";
import {FormLoginData, formLoginSchema} from "@/components/shared/modals/auth-modal/forms/schemas";
import {zodResolver} from "@hookform/resolvers/zod";
import {FormInput, Title} from "@/components/shared";
import Image from "next/image";
import {Button} from "@/components/ui";
import toast from "react-hot-toast";

interface Props {
    onClose?: () => void;
}

export const LoginForm: React.FC<Props> = ({onClose}) => {
    const form = useForm<FormLoginData>({
        resolver: zodResolver(formLoginSchema),
        defaultValues: {
            email: '',
            password: ''
        }
    });

    const onSubmit = async (data: FormLoginData) => {
        try {
            /*
                const response = await Api.auth.singIn(data);
                if (!response.ok) {
                    throw Error();
                }
             */
            onClose?.();
            toast.success('Вы успешно вошли в аккаунт', {
                icon: '✅'
            });
        } catch (err) {
            console.error('error [LOGIN]', err);
            toast.error('Не удалось войти в аккаунт', {
                icon: '❌'
            });
        }
    }

    return (
        <FormProvider {...form}>
            <form className={'flex flex-col gap-5'} onSubmit={form.handleSubmit(onSubmit)}>
                <div className={'mr-2'}>
                    <Title text={'Вход в аккаунт'} size={'md'} className={'font-bold'} />
                    <p className={'text-gray-400'}>Введите свою почту, чтобы войти в аккаунт</p>
                </div>
                
                <FormInput name={'email'} label={'E-mail'} required={true} />
                <FormInput name={'password'} label={'Пароль'} required={true} />

                <Button
                    loading={form.formState.isSubmitting}
                    className={'h-12 text-base'}
                    type={'submit'}
                >
                    Войти
                </Button>
            </form>
        </FormProvider>
    );
}