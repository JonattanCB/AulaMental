package com.abs.aulamental.external.whatsapp;

import com.abs.aulamental.exception.ValidarExcepciones;
import com.abs.aulamental.external.whatsapp.dto.CodeResultMensajeriaDto;
import com.abs.aulamental.external.whatsapp.dto.LoginDto;
import com.abs.aulamental.external.whatsapp.dto.MensajeriaDto;
import com.abs.aulamental.external.whatsapp.dto.TokenDto;
import com.abs.aulamental.utils.ApiGsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WhasapClient {

    @Value("${whatsapp.api.username}")
    private  String username;

    @Value("${whatsapp.api.password}")
    private String password;

    @Value("${whatsapp.api.url}")
    private String urlbase;

    @Value("${whatsapp.api.phone}")
    private String fullPhone;

    private TokenDto tokenCache = null;

    private long tokenObtenidoEn = 0L;


    private static final long DURACION_TOKEN = 24 * 60 * 60 * 1000;

    public boolean mensajes(MensajeriaDto dto){
        String url = urlbase+"/"+fullPhone+"/send/message";
        try{
            TokenDto token = obtenerToken();

            CodeResultMensajeriaDto result = ApiGsonUtils.post(url,token.token(),dto, CodeResultMensajeriaDto.class);

            return "SUCCESS".equalsIgnoreCase(result.code());
        }catch (Exception e){
            throw new ValidarExcepciones("error al enviar mensaje: "+e);
        }
    }

    private TokenDto obtenerToken(){
        long ahora = System.currentTimeMillis();

        if (tokenCache != null && (ahora - tokenObtenidoEn) < DURACION_TOKEN) {
            return tokenCache;
        }

        try {
            tokenCache = conexion();
            tokenObtenidoEn = ahora;
            return tokenCache;
        } catch (Exception e) {
            throw new ValidarExcepciones("Error obteniendo token: "+ e);
        }
    }

    private TokenDto conexion(){
        String url = urlbase+"/auth/login";
        try{
            LoginDto login  = new LoginDto(username, password);
            TokenDto token =  ApiGsonUtils.post(url, login, TokenDto.class);
            return token;
        }catch (Exception e){
            throw new ValidarExcepciones("Error en la conexion: "+e);
        }
    }

}
