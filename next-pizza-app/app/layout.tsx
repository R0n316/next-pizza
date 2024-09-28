import { Nunito } from "next/font/google";
import "./globals.css";
import React from "react";
import {Toaster} from "react-hot-toast";

const inter = Nunito({
    subsets: ['cyrillic'],
    variable: '--font-nunito',
    weight: ['400', '500', '600', '700', '800', '900'],
});

export default function RootLayout({
                                       children,
                                   }: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <html lang="en">
            <body className={inter.className}>
            {children}
            <Toaster/>
            </body>
        </html>
    );
}
