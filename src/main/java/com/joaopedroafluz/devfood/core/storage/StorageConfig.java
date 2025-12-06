package com.joaopedroafluz.devfood.core.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.joaopedroafluz.devfood.domain.service.ArmazenamentoFotoService;
import com.joaopedroafluz.devfood.infrastructure.service.storage.ArmazenamentoAmazonS3Service;
import com.joaopedroafluz.devfood.infrastructure.service.storage.ArmazenamentoLocalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    private final StorageProperties storageProperties;

    public StorageConfig(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }


    @Bean
    public AmazonS3 amazonS3() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(storageProperties.getS3().getIdChaveAcesso(),
                storageProperties.getS3().getChaveAcessoSecreta());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(storageProperties.getS3().getRegiao())
                .build();
    }

    @Bean
    public ArmazenamentoFotoService armazenamentoFotoService() {
        if (StorageProperties.TipoArmazenamento.LOCAL.equals(storageProperties.getTipoArmazenamento())) {
            return new ArmazenamentoLocalService(storageProperties);
        }

        return new ArmazenamentoAmazonS3Service(amazonS3(), storageProperties);
    }

}
