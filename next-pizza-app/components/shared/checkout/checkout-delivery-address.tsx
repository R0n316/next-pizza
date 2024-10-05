import React from 'react';
import {WhiteBlock, FormTextarea, AddressInput, ErrorText} from "@/components/shared";
import {Controller, useFormContext} from "react-hook-form";

interface Props {
    className?: string;
}

export const CheckoutDeliveryAddress: React.FC<Props> = ({ className }) => {
    const {control} = useFormContext();

    return (
        <WhiteBlock title={'3. Адрес доставки'} className={className}>
            <div className={'flex flex-col gap-3'}>
                <Controller
                    control={control}
                    render={({field, fieldState}) => (
                        <>
                            <AddressInput onChange={field.onChange}/>
                            {fieldState.error?.message && <ErrorText text={fieldState.error.message}/>}
                        </>
                    )}
                    name={'address'}
                />

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