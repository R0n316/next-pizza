import React from 'react';
import {FormInput} from "@/components/shared";
import {cn} from "@/lib/utils";

interface Props {
    name: string;
    title: string;
    titleClassName?: string;
    className?: string;
}

export const FormTitleInput: React.FC<Props> = ({name, title, titleClassName, className}) => {
    return (
        <div>
            <div className={cn('pl-1 font-extrabold', titleClassName)}>{title}</div>
            <FormInput name={name} className={cn('w-full', className)}/>
        </div>
    );
}