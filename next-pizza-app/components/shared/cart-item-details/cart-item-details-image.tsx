import React from "react";
import Image from "next/image";
import {cn} from "@/lib/utils";

interface Props {
  src: string;
  className?: string;
}

export const CartItemDetailsImage: React.FC<Props> = ({ src, className }) => {
  return <Image src={src} alt={'cart item'} width={60} height={60} className={cn(className, 'h-[60px]')}/>
};
