import React from 'react';
import {Input} from "@/components/ui";
import {WhiteBlock, FormTextarea} from "@/components/shared";
interface Props {
    className?: string;
}

export const CheckoutDeliveryAddress: React.FC<Props> = ({ className }) => {
    return (
        <WhiteBlock title={'3. Адрес доставки'} className={className}>
            <div className={'flex flex-col gap-5'}>
                <Input name={'firstName'} className={'text-base'} placeholder={'Адрес'}/>
                <FormTextarea
                    name={'comment'}
                    className={'text-base'}
                    placeholder={'Комментарий к заказу'}
                    rows={5}
                />
            </div>
        </WhiteBlock>
    );
}