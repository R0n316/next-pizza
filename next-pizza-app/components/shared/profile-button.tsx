'use client';

import React, {useEffect, useState} from 'react';
import {Button, Skeleton} from "@/components/ui";
import {CircleUser, User} from "lucide-react";
import {useCookies} from "react-cookie";

interface Props {
    onClickSignIn?: () => void;
    className?: string;
}

export const ProfileButton: React.FC<Props> = ({className, onClickSignIn}) => {
    const [cookies] = useCookies(['jwt-token']);

    const [isClient, setIsClient] = useState(false);

    useEffect(() => {
        setIsClient(true);
    }, []);

    if (!isClient) {
        return <Skeleton className={'w-[120px] h-10'}/>
    }
    return (
        <div className={className}>
            {
                cookies["jwt-token"] === undefined ? (
                    <Button onClick={onClickSignIn} variant="outline" className="flex items-center gap-1">
                        <User size={16} />
                        Войти
                    </Button>
                ) : (
                    <Button variant="secondary" className="flex items-center gap-2">
                        <CircleUser size={18} />
                        Профиль
                    </Button>
                )
            }

        </div>
    );
}