package ru.alex.nextpizzaapi.mapper.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.Story;
import ru.alex.nextpizzaapi.dto.story.StoryReadDto;
import ru.alex.nextpizzaapi.dto.storyItem.StoryItemReadDto;
import ru.alex.nextpizzaapi.mapper.Mapper;
import ru.alex.nextpizzaapi.mapper.storyItem.StoryItemReadMapper;

import java.util.List;

@Component
public class StoryReadMapper implements Mapper<Story, StoryReadDto> {
    private final StoryItemReadMapper storyItemReadMapper;

    @Autowired
    public StoryReadMapper(StoryItemReadMapper storyItemReadMapper) {
        this.storyItemReadMapper = storyItemReadMapper;
    }

    @Override
    public StoryReadDto toDto(Story entity) {
        List<StoryItemReadDto> items = entity.getStoryItems()
                .stream()
                .map(storyItemReadMapper::toDto)
                .toList();
        return new StoryReadDto(
                entity.getId(),
                entity.getPreviewImage(),
                items
        );
    }
}
