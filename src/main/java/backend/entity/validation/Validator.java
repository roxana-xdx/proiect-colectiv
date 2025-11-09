package backend.entity.validation;

import jakarta.validation.ValidationException;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
