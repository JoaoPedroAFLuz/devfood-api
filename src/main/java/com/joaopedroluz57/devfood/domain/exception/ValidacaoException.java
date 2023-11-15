package com.joaopedroluz57.devfood.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Getter
public class ValidacaoException extends RuntimeException {

    private final BindingResult bindingResult;

}
