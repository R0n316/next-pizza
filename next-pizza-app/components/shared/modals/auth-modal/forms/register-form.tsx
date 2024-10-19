import React from 'react';
import {FormInput, Title} from "@/components/shared";
import {Button} from "@/components/ui";
import {FormProvider, useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {FormRegisterData, formRegisterSchema} from "@/components/shared/modals/auth-modal/forms/schemas";
import {Api} from "@/services/api-client";
import toast from "react-hot-toast";

interface Props {
    onClose?: () => void;
}

export const RegisterForm: React.FC<Props> = ({onClose}) => {
    const form = useForm<FormRegisterData>({
        resolver: zodResolver(formRegisterSchema),
        defaultValues: {
            email: '',
            password: '',
            fullName: '',
            confirmPassword: ''
        }
    });

    const onSubmit = async (data: FormRegisterData) => {
        const response = await Api.auth.register(data);
        try {
            if (response.status !== 201) {
                throw new Error('Failed to sign in');
            }
            onClose?.();
            toast.success('Вы успешно вошли в аккаунт', {
                icon: '✅'
            });
        } catch (err) {
            const errorMessage = response.data !== undefined ? response.data.message : '';
            console.error('error [LOGIN]', err);
            toast.error(`Не удалось войти в аккаунт. (${errorMessage}).`, {
                icon: '❌'
            });
        }
    }

    return (
        <FormProvider {...form}>
            <form className={'flex flex-col gap-5'} onSubmit={form.handleSubmit(onSubmit)}>
                <div className={'mr-2'}>
                    <Title text={'Регистрация'} size={'md'} className={'font-bold'} />
                    <p className={'text-gray-400'}>Заполните форму для создании аккаунта</p>
                </div>

                <FormInput name={'fullName'} label={'ФИО'} required={true} />
                <FormInput name={'email'} label={'E-mail'} required={true} />
                <FormInput type={'password'} name={'password'} label={'Пароль'} required={true} />
                <FormInput type={'password'} name={'confirmPassword'} label={'Повторите пароль'} required={true} />

                <Button
                    loading={form.formState.isSubmitting}
                    className={'h-12 text-base'}
                    type={'submit'}
                >
                    Зарегистрироваться
                </Button>
            </form>
        </FormProvider>
    );
}