'use client';

import React, {useEffect, useState} from 'react';
import {Button, Skeleton} from "@/components/ui";
import {CircleUser, User} from "lucide-react";
import {useCookies} from "react-cookie";
import Link from "next/link";
import jwt_decode from 'jsonwebtoken';
import {Api} from "@/services/api-client";

interface Props {
    onClickSignIn?: () => void;
    className?: string;
}

export const ProfileButton: React.FC<Props> = ({className, onClickSignIn}) => {
    const [cookies] = useCookies(['jwt']);
    const [isClient, setIsClient] = useState(false);
    const [isTokenValid, setIsTokenValid] = useState(false);

    const refreshToken = async () => {
        const response = await Api.auth.validateJwt(cookies['jwt']);
        if (response.status === 200) {
            setIsTokenValid(true);
        } else {
            setIsTokenValid(false);
            document.cookie = "jwt=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        }
    }

    useEffect(() => {
        setIsClient(true);

        if(cookies['jwt']) {
            try {
                const decodedToken: any = jwt_decode.decode(cookies['jwt']);
                if(decodedToken.exp && decodedToken.exp * 1000 > Date.now()) {
                    setIsTokenValid(true);
                } else {
                    refreshToken();
                }
            } catch (err) {
                console.error('Invalid token', err);
                setIsTokenValid(false);
            }
        }
    }, [cookies]);

    if (!isClient) {
        return <Skeleton className={'w-[120px] h-10'}/>
    }
    return (
        <div className={className}>
            {
                !isTokenValid ? (
                    <Button onClick={onClickSignIn} variant="outline" className="flex items-center gap-1">
                        <User size={16} />
                        Войти
                    </Button>
                ) : (
                    <Link href={'/profile'}>
                        <Button variant="secondary" className="flex items-center gap-2">
                            <CircleUser size={18} />
                            Профиль
                        </Button>
                    </Link>
                )
            }

        </div>
    );
}