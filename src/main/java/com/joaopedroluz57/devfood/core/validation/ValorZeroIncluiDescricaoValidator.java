package com.joaopedroluz57.devfood.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Objects;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
        this.valorField = constraintAnnotation.valorField();
        this.descricaoField = constraintAnnotation.descricaoField();
        this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object objetoValidacao, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValido = true;

        try {
            BigDecimal valor = (BigDecimal) Objects.requireNonNull(BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), valorField))
                    .getReadMethod()
                    .invoke(objetoValidacao);

            String descricao = (String) Objects.requireNonNull(BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), descricaoField))
                    .getReadMethod()
                    .invoke(objetoValidacao);

            if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
                isValido = descricao.toLowerCase().contains(descricaoObrigatoria.toLowerCase());
            }

            return isValido;
        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }

}
