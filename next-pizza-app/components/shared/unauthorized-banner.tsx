import React from 'react';
import {InfoBlock} from "@/components/shared";
import {cn} from "@/lib/utils";

interface Props {
    className?: string;
}
export const UnauthorizedBanner: React.FC<Props> = ({className}) => {
    return (
        <div className={cn('flex flex-col items-center justify-center mt-40', className)}>
            <InfoBlock
                title="Доступ запрещён"
                text="Данную страницу могут просматривать только авторизованные пользователи"
                imageUrl="/assets/images/lock.png"
            />
        </div>
    );
}