package com.fciencias.freshbowl.utils;

import java.util.Base64;
import java.util.Map;

public class TokenGenerator {
    
    private TokenGenerator(){}
    
    public static String mapToString(Map<String,Object> authValues)
    {
        StringBuilder jsonString = new StringBuilder("{");
        for (Map.Entry<String, Object> entry : authValues.entrySet()) {
            jsonString.append("\"")
            .append(entry.getKey())
            .append("\":\"")
            .append(entry.getValue())
            .append("\",");
        }
        if (jsonString.length() > 1) 
            jsonString.setLength(jsonString.length() - 1);
            
        jsonString.append("}");
        return jsonString.toString();
    }

    public static String generateToken(Map<String,Object> authValues)
    {
        Hasher hasher = new Hasher();
        return hasher.encode256(mapToString(authValues));
    }

    public static String encodeToken(String token)
    {
        return new String(Base64.getEncoder().encode(token.getBytes()));
    }

    public static String encodeToken(Map<String,Object> authValues)
    {
        String token = mapToString(authValues);
        return new String(Base64.getEncoder().encode(token.getBytes()));
    }
}
