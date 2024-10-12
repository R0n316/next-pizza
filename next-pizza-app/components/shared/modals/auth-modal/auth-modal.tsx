
import {Button, Dialog} from "@/components/ui";
import {DialogContent} from "@/components/ui/dialog";
import Image from "next/image";
import {LoginForm} from "@/components/shared/modals/auth-modal/forms/login-form";
import React from "react";

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
            <DialogContent className={'w-[450px] bg-white p-10'}>
                {
                    type === 'login' ?
                        <LoginForm onClose={handleClose}/> :
                        <h1>REGISTER</h1>
                }
                <hr/>
                <div className={'flex gap-2'}>
                    <Button
                        variant={'secondary'}
                        onClick={() => console.log('github sign in')}
                        type={'button'}
                        className={'gap-2 h-12 p-2 flex-1'}
                    >
                        <Image
                            width={24} height={24}
                            src={'https://github.githubassets.com/favicons/favicon.svg'}
                            alt={'github'}
                        />
                        Github
                    </Button>

                    <Button
                        variant={'secondary'}
                        onClick={() => console.log('google sign in')}
                        type={'button'}
                        className={'gap-2 h-12 p-2 flex-1'}
                    >
                        <Image
                            width={24} height={24}
                            src={'https://fonts.gstatic.com/s/i/productlogos/googleg/v6/24px.svg'}
                            alt={'google'}
                        />
                        Google
                    </Button>
                </div>

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