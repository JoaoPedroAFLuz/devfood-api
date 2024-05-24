package com.joaopedroluz57.devfood.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Getter
@Setter
@Component
@ConfigurationProperties("devfood.storage")
public class StorageProperties {

    private final Local local = new Local();
    private final S3 s3 = new S3();

    @Getter
    @Setter
    public static class Local {

        private Path diretorioFotos;

    }

    @Getter
    @Setter
    public static class S3 {

        private String bucket;
        private String region;
        private String diretorioFotos;
        private String idChaveAcesso;
        private String chaveAcessoSecreta;

    }

}
