package ru.alex.nextpizzaapi.mapper.storyItem;

import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.StoryItem;
import ru.alex.nextpizzaapi.dto.storyItem.StoryItemReadDto;
import ru.alex.nextpizzaapi.mapper.Mapper;

@Component
public class StoryItemReadMapper implements Mapper<StoryItem, StoryItemReadDto> {
    @Override
    public StoryItemReadDto toDto(StoryItem entity) {
        return new StoryItemReadDto(
                entity.getId(),
                entity.getSourceUrl()
        );
    }
}
