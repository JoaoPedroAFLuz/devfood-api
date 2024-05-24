package com.joaopedroluz57.devfood.infrastructure.service.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.joaopedroluz57.devfood.core.storage.StorageProperties;
import com.joaopedroluz57.devfood.domain.model.NovaFoto;
import com.joaopedroluz57.devfood.domain.service.ArmazenamentoFotoService;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class ArmazenamentoAmazonS3Service implements ArmazenamentoFotoService {

    private final AmazonS3 amazonS3;
    private final StorageProperties storageProperties;

    public ArmazenamentoAmazonS3Service(AmazonS3 amazonS3,
                                        StorageProperties storageProperties) {
        this.amazonS3 = amazonS3;
        this.storageProperties = storageProperties;
    }

    @Override
    public InputStream recuperar(String nomeArquivo) {
        return null;
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            String caminhoArquivo = getCaminho(novaFoto.getNomeArquivo());

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(novaFoto.getTipoArquivo());

            PutObjectRequest putObjectRequest = new PutObjectRequest(storageProperties.getS3().getBucket(),
                    caminhoArquivo,
                    novaFoto.getInputStream(),
                    objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new ArmazenamentoException("Não foi possível salvar o arquivo.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
    }

    private String getCaminho(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }

}
