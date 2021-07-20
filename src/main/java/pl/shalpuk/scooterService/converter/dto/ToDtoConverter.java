package pl.shalpuk.scooterService.converter.dto;

import java.util.Set;
import java.util.stream.Collectors;

public interface ToDtoConverter<T, O> {

    O convertToDto(final T entity);

    default Set<O> convertToDto(final Set<T> entities) {
        return entities.stream().map(this::convertToDto).collect(Collectors.toSet());
    }

}
