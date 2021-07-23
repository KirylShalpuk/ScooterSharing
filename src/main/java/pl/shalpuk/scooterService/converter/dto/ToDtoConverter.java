package pl.shalpuk.scooterService.converter.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface ToDtoConverter<T, O> {

    O convertToDto(final T entity);

    default Set<O> convertToDto(final Set<T> entities) {
        return entities.stream().map(this::convertToDto).collect(Collectors.toSet());
    }

    default List<O> convertToDto(final List<T> entities) {
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    default Page<O> convertToDto(final Page<T> page) {
        List<O> entityList = convertToDto(page.getContent());
        return new PageImpl<O>(entityList, page.getPageable(), page.getTotalElements());
    }

}
