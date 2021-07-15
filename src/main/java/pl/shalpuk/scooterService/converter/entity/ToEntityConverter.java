package pl.shalpuk.scooterService.converter.entity;

public interface ToEntityConverter<T, O> {

    O convertToEntity(final T dto);

}
