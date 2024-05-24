package com.joaopedroluz57.devfood.infrastructure.service.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.joaopedroluz57.devfood.core.storage.StorageProperties;
import com.joaopedroluz57.devfood.domain.model.FotoRecuperada;
import com.joaopedroluz57.devfood.domain.model.NovaFoto;
import com.joaopedroluz57.devfood.domain.service.ArmazenamentoFotoService;

import java.net.URL;

public class ArmazenamentoAmazonS3Service implements ArmazenamentoFotoService {

    private final AmazonS3 amazonS3;
    private final StorageProperties storageProperties;

    public ArmazenamentoAmazonS3Service(AmazonS3 amazonS3,
                                        StorageProperties storageProperties) {
        this.amazonS3 = amazonS3;
        this.storageProperties = storageProperties;
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            String caminhoArquivo = getCaminho(nomeArquivo);

            URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);

            return FotoRecuperada.builder()
                    .url(url)
                    .build();
        } catch (Exception e) {
            throw new ArmazenamentoException("Não foi possível recuperar o arquivo.", e);
        }
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
        try {
            String caminhoArquivo = getCaminho(nomeArquivo);

            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(),
                    caminhoArquivo);

            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new ArmazenamentoException("Não foi possível remover o arquivo.", e);
        }
    }

    private String getCaminho(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }

}
