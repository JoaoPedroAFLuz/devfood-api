package com.joaopedroluz57.devfood.infrastructure.service.storage;

import com.joaopedroluz57.devfood.domain.service.ArmazenamentoFotoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ArmazenamentoLocalService implements ArmazenamentoFotoService {

    @Value("${devfood.storage.local.diretorio-fotos}")
    private Path diretorioFotos;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        Path caminhoDoArquivo = getCaminhoDoArquivo(novaFoto.getNomeArquivo());

        try {
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(caminhoDoArquivo));
        } catch (IOException e) {
            throw new ArmazenamentoException("Não foi possível armazenar o arquivo.", e);
        }
    }

    private Path getCaminhoDoArquivo(String nomeArquivo) {
        return diretorioFotos.resolve(nomeArquivo);
    }

}
