package pl.shalpuk.scooterService.converter.entity;

import java.util.Set;
import java.util.stream.Collectors;

public interface ToEntityConverter<T, O> {

    O convertToEntity(final T dto);

    default Set<O> convertToEntity(final Set<T> dtos) {
        return dtos.stream().map(this::convertToEntity).collect(Collectors.toSet());
    }

}
