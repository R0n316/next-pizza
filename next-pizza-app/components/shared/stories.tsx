'use client';

import React, {useEffect, useState} from 'react';
import {Api} from "@/services/api-client";
import {Story} from "@/services/model";
import {Container} from "@/components/shared/container";
import {cn} from "@/lib/utils";
import Image from "next/image";
import {X} from "lucide-react";
import ReactInstaStories from "react-insta-stories";

interface Props {
    className?: string;
}

export const Stories: React.FC<Props> = ({ className }) => {
    const [stories, setStories] = useState<Story[]>([]);
    const [open, setOpen] = useState(false);
    const [selectedStory, setSelectedStory] = useState<Story>();
    useEffect(() => {
        async function fetchStories() {
            const data = await Api.stories.getAll();
            setStories(data);
        }
        fetchStories();
    }, []);

    const onClickStory = (story: Story) => {
        setSelectedStory(story);
        if(story.items.length > 0) {
            setOpen(true);
        }
    }

    return (
        <>
            <Container className={cn('flex items-center justify-between gap-2 my-10', className)}>
                {stories.length === 0 && [...Array(6)].map((_, index) => (
                    <div key={index} className={'w-[200px] h-[250px] bg-gray-200 rounded-md animate-pulse'}></div>
                ))}


                {stories.map(story => (
                    <Image
                        className={'rounded-2xl'}
                        key={story.id}
                        onClick={() => onClickStory(story)}
                        src={story.previewImage}
                        alt={'story image'}
                        height={250}
                        width={200}
                     />
                ))}
                {
                    open &&
                    <div className={'absolute left-0 top-0 w-full h-full bg-black/80 flex items-center justify-center z-30'}>
                        <div className={'relative w-[520px]'}>
                            <button
                                className={'absolute -right-10 -top-5 z-30'}
                                onClick={() => setOpen(false)}
                            >
                                <X className={'absolute top-0 right-0 w-8 h-8 text-white/50'} onClick={() => setOpen(false)}/>
                            </button>
                            <ReactInstaStories
                                stories={selectedStory?.items.map(item => ({url: item.sourceUrl})) || []}
                                defaultInterval={3000}
                                width={520}
                                height={800}
                            />
                        </div>
                    </div>
                }
            </Container>
        </>
    );
}