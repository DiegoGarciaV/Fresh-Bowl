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
        flashMap.put("requestedUrl", url);

        if (url.contains("login") || url.contains(".css") || url.contains(".js") || url.contains("/imgs")
                || url.equals("/"))
            return true;

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
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

        Map<String, String> tokenData = TokenGenerator.decodeToken(authData);
        if (tokenData == null) {
            try {
                response.sendRedirect("/login");
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        UserRole role = new UserRole();
        role.setRoleId(Integer.parseInt(tokenData.get("role")));

        ResourceAccess resourceAccess = resourceRepository.findByResourceUrlAndRoleId(url, role);

        validation = (resourceAccess != null);

        String resource = validation ? url : "/login";

        if (!validation) {

            System.out.println("Validacion fallida. Redireccion al login, se indica recurso solicitado.");
        } else
            System.out.println("Validacion exitosa. Redireccion al recurso solicitado.");

        try {
            response.sendRedirect(resource);
            flashMap.remove("requestedUrl");
            return validation;
        } catch (IOException e) {
            e.printStackTrace();
            return validation;
        }

    }
}
