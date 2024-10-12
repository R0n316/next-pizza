import React from 'react';
import {Button} from "@/components/ui";
import {User} from "lucide-react";

interface Props {
    onClickSignIn?: () => void;
    className?: string;
}

export const ProfileButton: React.FC<Props> = ({className, onClickSignIn}) => {
    return (
        <div className={className}>
            <Button
                onClick={onClickSignIn}
                variant={'outline'}
                className={'flex items-center gap-1'}
            >
                <User size={16} />
                Войти
            </Button>
        </div>
    );
}