package com.joaopedroafluz.devfood.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.net.URL;

@Getter
@Builder
public class FotoRecuperada {

    private URL url;
    private InputStream inputStream;

    public boolean temUrl() {
        return url != null;
    }

}
