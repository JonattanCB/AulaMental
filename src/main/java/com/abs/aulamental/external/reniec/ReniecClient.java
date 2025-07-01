package com.abs.aulamental.external.reniec;

import com.abs.aulamental.exception.ValidarExcepciones;
import com.abs.aulamental.external.reniec.dto.RenicDto;
import com.abs.aulamental.utils.ApiGsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ReniecClient {

    @Value("${api.reniec.base-url}")
    private String baseUrl;

    @Value("${api.reniec.token}")
    private String token;

    public RenicDto buscarPorDni(String dni) {
        String url = baseUrl + "/v2/reniec/dni?numero=" + dni;
        try {
            return ApiGsonUtils.get(url,token,RenicDto.class);
        } catch (IOException | InterruptedException e) {
            throw new ValidarExcepciones("Error consultando RENIEC: "+e);
        }
    }

}
