package com.joaopedroafluz.devfood.core.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TipoArquivoValidator implements ConstraintValidator<TipoArquivo, MultipartFile> {

    private List<String> formatosAceitos;

    @Override
    public void initialize(TipoArquivo constraintAnnotation) {
        this.formatosAceitos = Arrays.asList(constraintAnnotation.formatosAceitos());
    }

    @Override
    public boolean isValid(MultipartFile objetoValidacao, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.isNull(objetoValidacao) || formatosAceitos.contains(objetoValidacao.getContentType());
    }

}
