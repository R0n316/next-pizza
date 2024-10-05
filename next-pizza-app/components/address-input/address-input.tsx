'use client';

import { AddressSuggestions } from 'react-dadata';
import 'react-dadata/dist/react-dadata.css';
import './AddressInput.css'
import React from "react";


interface Props {
    onChange?: (value?: string) => void;
}

export const AddressInput: React.FC<Props> = ({onChange}) => {
    const apiKey = process.env.NEXT_PUBLIC_DADATA_API_KEY;
    return (
        <AddressSuggestions
            token={apiKey || ''}
            onChange={data => onChange?.(data?.value)}
            inputProps={{
                className: 'flex w-full rounded-md border border-input bg-background px-3 py-2' +
                    ' ring-offset-background placeholder:text-muted-foreground focus-visible:outline-none' +
                    ' focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 ' +
                    'disabled:cursor-not-allowed disabled:opacity-50 h-12 text-md'
            }}
        />
    )
}