package com.fciencias.freshbowl.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;

@Component
public class Hasher{

    private MessageDigest codificador;

    @Value("${authentication.secret}")
    private String secreto;

    public Hasher()
    {
        try{
            codificador = MessageDigest.getInstance("SHA-256");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public String encode256(String texto) {
        return bytesToHex(codificador.digest(texto.getBytes()));
    }

    public String generaToken(String usuario, String idRol) {
        
        StringBuilder cadena = new StringBuilder(usuario);
        cadena.append(idRol)
        .append(secreto);

        return encode256(cadena.toString());
    }

    public boolean validaToken(String usuario, String idRol, String token) {
        
        return (generaToken(usuario, idRol).equals(token));
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
}
