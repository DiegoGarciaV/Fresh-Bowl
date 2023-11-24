package com.fciencias.freshbowl.utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class TokenGenerator {

    private TokenGenerator() {
    }

    public static String mapToString(Map<String, Object> authValues) {
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

    public static String generateToken(Map<String, Object> authValues) {
        Hasher hasher = new Hasher();
        return hasher.encode256(mapToString(authValues));
    }

    public static String encodeToken(String token) {
        return new String(Base64.getEncoder().encode(token.getBytes()));
    }

    public static String encodeToken(Map<String, Object> authValues) {
        String token = mapToString(authValues);
        return new String(Base64.getEncoder().encode(token.getBytes()));
    }

    public static Map<String, String> decodeToken(String encodedToken) {
        String[] tokenComponens = encodedToken.split(".");
        if (tokenComponens.length != 2)
            return null;

        String payload = new String(Base64.getDecoder().decode(tokenComponens[0]));
        String signature = new String(Base64.getDecoder().decode(tokenComponens[1]));

        payload = payload.replace("{", "")
                .replace("}", "")
                .replace("\"", "");
        Map<String, String> tokenData = new HashMap<>();
        tokenData.put("signature", signature);

        String[] tuples = payload.split(",");
        for (String tuple : tuples) {
            String key = tuple.split(":")[0];
            String value = tuple.split(":")[1];
            tokenData.put(key, value);
        }

        return tokenData;

    }
}
