package com.joaopedroafluz.devfood.infrastructure.service.storage;

import com.joaopedroafluz.devfood.core.storage.StorageProperties;
import com.joaopedroafluz.devfood.domain.model.FotoRecuperada;
import com.joaopedroafluz.devfood.domain.model.NovaFoto;
import com.joaopedroafluz.devfood.domain.service.ArmazenamentoFotoService;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ArmazenamentoLocalService implements ArmazenamentoFotoService {

    private final StorageProperties storageProperties;

    public ArmazenamentoLocalService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }


    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            Path caminhoDoArquivo = getCaminhoDoArquivo(nomeArquivo);

            return FotoRecuperada.builder()
                    .inputStream(Files.newInputStream(caminhoDoArquivo))
                    .build();
        } catch (IOException e) {
            throw new ArmazenamentoException("Não foi possível recuperar o arquivo", e);
        }
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            Path caminhoDoArquivo = getCaminhoDoArquivo(novaFoto.getNomeArquivo());

            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(caminhoDoArquivo));
        } catch (IOException e) {
            throw new ArmazenamentoException("Não foi possível armazenar o arquivo.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            Path caminhoDoArquivo = getCaminhoDoArquivo(nomeArquivo);

            Files.deleteIfExists(caminhoDoArquivo);
        } catch (Exception e) {
            throw new ArmazenamentoException("Não foi possível armazenar o arquivo", e);
        }
    }

    private Path getCaminhoDoArquivo(String nomeArquivo) {
        return storageProperties.getLocal().getDiretorioFotos().resolve(nomeArquivo);
    }

}
