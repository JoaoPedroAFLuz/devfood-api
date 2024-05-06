package com.joaopedroluz57.devfood.api.model.input;

import com.joaopedroluz57.devfood.core.validation.TamanhoArquivo;
import com.joaopedroluz57.devfood.core.validation.TipoArquivo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoInput {

    @NotNull
    @TamanhoArquivo(max = "500KB")
    @TipoArquivo(formatosAceitos = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    private MultipartFile arquivo;

    @NotBlank
    private String descricao;

}
