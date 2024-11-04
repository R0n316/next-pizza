package ru.alex.nextpizzaapi.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "storyItems")
@EqualsAndHashCode(exclude = "storyItems")
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "preview_image")
    private String previewImage;

    @OneToMany(mappedBy = "story")
    private List<StoryItem> storyItems;
}
