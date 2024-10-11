import {useEffect, useState} from "react";
import {Api} from "@/services/api-client";
import {OrderPayment} from "@/services/model";

export const useOrder = (secret: string) => {
    const [order, setOrder] = useState<OrderPayment | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchOrder = async () => {
            try {
                const orderData = await Api.order.get(secret);
                setOrder(orderData);
                setLoading(false);
            } finally {
                setLoading(false);
            }
        };

        fetchOrder();
    }, [secret]);

    return { order, loading };
};