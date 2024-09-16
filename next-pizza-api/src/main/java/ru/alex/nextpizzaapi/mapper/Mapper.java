package ru.alex.nextpizzaapi.mapper;

public interface Mapper<E, D> {
    default D toDto(E entity) {
        return null;
    }
    default E toEntity(D dto) {
        return null;
    }
}