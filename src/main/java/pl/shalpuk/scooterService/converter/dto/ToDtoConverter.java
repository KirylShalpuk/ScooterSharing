package pl.shalpuk.scooterService.converter.dto;

public interface ToDtoConverter<T, O> {

    O convertToDto(final T entity);

}
