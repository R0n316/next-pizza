'use client';

import React from 'react';
import {FormProvider, useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {FormRegisterData, formRegisterSchema} from "@/components/shared/modals/auth-modal/forms/schemas";
import {UserReadDto} from "@/services/model";
import toast from "react-hot-toast";
import {Container} from "@/components/shared/container";
import {Title} from "@/components/shared/title";
import {FormInput} from "@/components/shared/form";
import {Button} from "@/components/ui";
import {Api} from "@/services/api-client";

interface Props {
    user: UserReadDto
}

export const ProfileForm: React.FC<Props> = ({user}) => {
    const form = useForm({
        resolver: zodResolver(formRegisterSchema),
        defaultValues: {
            fullName: user.fullName || '',
            email: user.email || '',
            password: '',
            confirmPassword: ''
        }
    });

    const onSubmit = async (data: FormRegisterData) => {
        try {
            await Api.user.updateUserInfo(user.id ,{
                email: data.email,
                fullName: data.fullName,
                password: data.password
            });
            toast.success('Данные обновлены 📝', {
                icon: '✅'
            });
        } catch (error) {
            return toast.error('Ошибка при обновлении данных', {
                icon: '❌'
            });
        }
    }

    const onCLickSignOut = () => {
        document.cookie = "jwt=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        window.location.href = '/';
    };

    return (
        <Container className={'my-10'}>
            <Title text={`Личные данные | #${user.id}`} size={'md'} className={'font-bold'}/>
            <FormProvider {...form}>
                <form className={'flex flex-col gap-5 w-96 mt-10'} onSubmit={form.handleSubmit(onSubmit)}>
                    <FormInput name={'email'} label={'E-mail'} required={true}/>
                    <FormInput name={'fullName'} label={'ФИО'} required={true}/>

                    <FormInput type={'password'} name={'password'} label={'Новый пароль'} required={true}/>
                    <FormInput type={'password'} name={'confirmPassword'} label={'Повторите пароль'} required={true}/>

                    <Button
                        disabled={form.formState.isSubmitting}
                        className={'text-base mt-10'}
                        type={'submit'}
                    >
                        Сохранить
                    </Button>

                    <Button
                        onClick={onCLickSignOut}
                        variant={'secondary'}
                        disabled={form.formState.isSubmitting}
                        className={'text-base'}
                        type={'button'}
                    >
                        Выйти
                    </Button>
                </form>
            </FormProvider>
        </Container>
    );
}