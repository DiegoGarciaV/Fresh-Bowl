package com.fciencias.freshbowl.services.validator;

import jakarta.servlet.http.Cookie;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.fciencias.freshbowl.models.ResourceAccess;
import com.fciencias.freshbowl.models.UserRole;
import com.fciencias.freshbowl.utils.TokenGenerator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthValidator implements HandlerInterceptor {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String url = request.getRequestURI();

        FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
        flashMap.put("testData", "Datos de prueba con flashMap");
        flashMap.put("requestedUrl", url);

        if (url.contains("login") || url.contains(".css") || url.contains(".js") || url.contains("/imgs")
                || url.equals("/") || url.contains("/favicon.ico") || url.contains("/error")
                || url.contains("/restricted"))
            return true;

        System.out.println("Validación de acceso en proceso");
        System.out.println("Recurso solicitado: " + url);
        Cookie[] cookies = request.getCookies();

        System.out.println("Obtencion de cookies");

        if (cookies == null) {
            System.out.println("No hay datos de sesión. Se redirecciona a login");
            flashMap.put("message", "Tu sesión ha caducado, debes iniciar sesión.");
            try {

                response.sendRedirect("/login?requestedUrl=" + url);
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        boolean validation = false;

        String authData = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("auth-token"))
                authData = cookie.getValue();
        }

        System.out.println("Credenciales obtenidas");

        Map<String, String> tokenData = TokenGenerator.decodeToken(authData);
        if (tokenData == null) {
            System.out.println("Credenciales inexistentes");
            flashMap.put("message", "Tu sesión ha caducado, debes iniciar sesión.");
            try {
                response.sendRedirect("/login?requestedUrl=" + url);
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        UserRole role = new UserRole();
        role.setRoleId(Integer.parseInt(tokenData.get("role")));

        System.out.println("Validando premisos al recurso solicitado");
        ResourceAccess resourceAccess = resourceRepository.findByResourceUrlAndRoleId(url, role);
        System.out.println(validation ? "SUCCESSFULL" : "FORBIDEN");
        validation = (resourceAccess != null);

        String resource = url;

        if (!validation) {

            System.out.println("Validacion fallida. Redireccion al login, se indica recurso solicitado.");
            resource = "/restricted";
        } else
            System.out.println("Validacion exitosa. Redireccion al recurso solicitado.");

        try {
            response.sendRedirect(resource);
            if (validation)
                flashMap.remove("requestedUrl");
            return validation;
        } catch (IOException e) {
            e.printStackTrace();
            return validation;
        }

    }
}
