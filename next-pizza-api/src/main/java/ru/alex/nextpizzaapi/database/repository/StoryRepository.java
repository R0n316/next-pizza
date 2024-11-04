package ru.alex.nextpizzaapi.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.nextpizzaapi.database.entity.Story;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer> {
    @Query("SELECT s FROM Story s LEFT JOIN FETCH s.storyItems")
    List<Story> findAllWithItems();
}
