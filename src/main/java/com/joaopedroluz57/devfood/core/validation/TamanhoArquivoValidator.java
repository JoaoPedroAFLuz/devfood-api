package com.joaopedroluz57.devfood.core.validation;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class TamanhoArquivoValidator implements ConstraintValidator<TamanhoArquivo, MultipartFile> {

    private DataSize max;

    @Override
    public void initialize(TamanhoArquivo constraintAnnotation) {
        this.max = DataSize.parse(constraintAnnotation.max());
    }

    @Override
    public boolean isValid(MultipartFile objetoValidacao, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.isNull(objetoValidacao) || objetoValidacao.getSize() <= this.max.toBytes();
    }

}
