
import {Button, Dialog} from "@/components/ui";
import {DialogContent, DialogTitle} from "@/components/ui/dialog";
import {LoginForm} from "@/components/shared/modals/auth-modal/forms/login-form";
import React from "react";
import {RegisterForm} from "@/components/shared/modals/auth-modal/forms/register-form";

interface Props {
    open: boolean;
    onClose: () => void;
}

export const AuthModal: React.FC<Props> = ({open, onClose}) => {
    const [type, setType] = React.useState<'login' | 'register'>('login');

    const onSwitchType = () => {
        setType(type === 'login' ? 'register' : 'login');
    }

    const handleClose = () => {
        onClose();
    };

    return (
        <Dialog open={open} onOpenChange={handleClose} >
            <DialogTitle/>
            <DialogContent className={'w-[450px] bg-white p-10'}>
                {
                    type === 'login' ?
                        <LoginForm onClose={handleClose}/> :
                        <RegisterForm onClose={handleClose} />
                }
                <hr/>

                <Button
                    variant={'outline'}
                    onClick={onSwitchType}
                    type={'button'}
                    className={'h-12'}
                >
                    {type !== 'login' ? 'Войти' : 'Регистрация'}
                </Button>
            </DialogContent>
        </Dialog>
    )
}