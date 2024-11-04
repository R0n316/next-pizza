package ru.alex.nextpizzaapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.nextpizzaapi.database.repository.StoryRepository;
import ru.alex.nextpizzaapi.dto.story.StoryReadDto;
import ru.alex.nextpizzaapi.mapper.story.StoryReadMapper;

import java.util.List;

@Service
public class StoryService {
    private final StoryRepository storyRepository;
    private final StoryReadMapper storyReadMapper;

    @Autowired
    public StoryService(StoryRepository storyRepository,
                        StoryReadMapper storyReadMapper) {
        this.storyRepository = storyRepository;
        this.storyReadMapper = storyReadMapper;
    }

    public List<StoryReadDto> findAll() {
        return storyRepository.findAllWithItems()
                .stream()
                .map(storyReadMapper::toDto)
                .toList();
    }
}
