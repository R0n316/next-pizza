package ru.alex.nextpizzaapi.dto.story;

import ru.alex.nextpizzaapi.dto.storyItem.StoryItemReadDto;

import java.util.List;

public record StoryReadDto(
        Integer id,
        String previewImage,
        List<StoryItemReadDto> items
) {
}
