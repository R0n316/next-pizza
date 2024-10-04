import React from 'react';
import {FormInput, WhiteBlock} from "@/components/shared";

interface Props {
    className?: string;
}

export const CheckoutPersonalInfo: React.FC<Props> = ({ className }) => {
    return (
        <WhiteBlock title={'2. Персональная информация'} className={className}>
            <div className={'grid grid-cols-2 gap-5'}>
                <FormInput name={'firstName'} className={'text-base'} placeholder={'Имя'}/>
                <FormInput name={'lastName'} className={'text-base'} placeholder={'Фамилия'}/>
                <FormInput name={'email'} className={'text-base'} placeholder={'Email'}/>
                <FormInput name={'phone'} className={'text-base'} placeholder={'Телефон'}/>
            </div>
        </WhiteBlock>
    );
}