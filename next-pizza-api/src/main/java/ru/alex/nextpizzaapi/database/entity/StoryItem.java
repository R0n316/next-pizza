package ru.alex.nextpizzaapi.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "story_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id", referencedColumnName = "id")
    private Story story;

    @Column(name = "source_url")
    private String sourceUrl;
}
