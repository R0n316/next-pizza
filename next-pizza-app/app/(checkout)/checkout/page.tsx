import {CheckoutItem, CheckoutItemDetails, Container, Title, WhiteBlock} from "@/components/shared";
import {Button, Input} from "@/components/ui";
import {Textarea} from "@/components/ui/textarea";
import {ArrowRight, Package, Percent, Truck} from "lucide-react";

export default function CheckoutPage() {
    return (
        <Container className={'mt-5'}>
            <Title text={'Оформление заказа'} className={'font-extrabold mb-8 text-[36px]'}/>

            <div className={'flex gap-10'}>
                {/*Левая часть*/}
                <div className={'flex flex-col gap-10 flex-1 mb-20'}>
                    <WhiteBlock title={'1. Корзина'}>
                        <div className={'flex flex-col gap-5'}>
                            <CheckoutItem
                                id={0}
                                imageUrl={'https://media.dodostatic.net/image/r:292x292/11EE7D61706D472F9A5D71EB94149304.avif'}
                                details={'Пицца Чоризо Фреш с дополнительными добавками (маргарита, соус, капричоза)'}
                                name={'Чоризо Фреш'}
                                price={299}
                                quantity={3}
                            />
                            <CheckoutItem
                                id={1}
                                imageUrl={'https://media.dodostatic.net/image/r:292x292/11EE7D612FC7B7FCA5BE822752BEE1E5.avif'}
                                details={'Пицца Пепперони Фреш с дополнительными добавками (маргарита, соус, капричоза)'}
                                name={'Пепперони Фреш'}
                                price={399}
                                quantity={2}
                            />
                        </div>
                    </WhiteBlock>

                    <WhiteBlock title={'2. Персональная информация'}>
                        <div className={'grid grid-cols-2 gap-5'}>
                            <Input name={'firstName'} className={'text-base'} placeholder={'Имя'}/>
                            <Input name={'lastName'} className={'text-base'} placeholder={'Фамилия'}/>
                            <Input name={'email'} className={'text-base'} placeholder={'Email'}/>
                            <Input name={'phone'} className={'text-base'} placeholder={'Телефон'}/>
                        </div>
                    </WhiteBlock>

                    <WhiteBlock title={'3. Адрес доставки'}>
                        <div className={'flex flex-col gap-5'}>
                            <Input name={'firstName'} className={'text-base'} placeholder={'Адрес'}/>
                            <Textarea
                                className={'text-base'}
                                placeholder={'Комментарий к заказу'}

                                rows={5}
                            />
                        </div>
                    </WhiteBlock>
                </div>

                {/*Правая часть*/}
                <div className={'w-[450px]'}>
                    <WhiteBlock className={'p-6 sticky top-4'}>
                        <div className={'flex flex-col gap-1'}>
                            <span className={'text-xl'}>Итого:</span>
                            <span className={'text-[34px] font-extrabold'}>2365 ₽</span>
                        </div>
                        <CheckoutItemDetails
                            title={
                            <div className={'flex items-center'}>
                                <Package size={18} className={'mr-2 text-gray-300'}/>
                                Стоимость товаров:
                            </div>
                        }
                            value={'2005 ₽'}
                        />
                        <CheckoutItemDetails
                            title={
                                <div className={'flex items-center'}>
                                    <Percent size={18} className={'mr-2 text-gray-300'}/>
                                    Налоги:
                                </div>
                            }
                            value={'240 ₽'}
                        />
                        <CheckoutItemDetails
                            title={
                                <div className={'flex items-center'}>
                                    <Truck size={18} className={'mr-2 text-gray-300'}/>
                                    Налоги:
                                </div>
                            }
                            value={'120 ₽'}
                        />
                        <Button
                            type={'submit'}
                            className={'w-full h-14 rounded-2xl mt-6 text-base font-bold'}
                        >
                            Перейти к оплате
                            <ArrowRight className={'w-5 ml-2'}/>
                        </Button>
                    </WhiteBlock>
                </div>
            </div>

        </Container>
    )
}