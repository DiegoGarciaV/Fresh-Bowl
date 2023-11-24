package com.fciencias.freshbowl.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fciencias.freshbowl.models.LoginModel;
import com.fciencias.freshbowl.models.User;
import com.fciencias.freshbowl.services.users.UserRepository;
import com.fciencias.freshbowl.utils.TokenGenerator;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @GetMapping(value={"/","","index","home"})
    public ModelAndView home()
    {
        return new ModelAndView("index");
    }


    @GetMapping(value={"login/","login"})
    public ModelAndView login(@ModelAttribute("requestedUrl") String redirectUrl)
    {
        ModelAndView response = new ModelAndView("login");
        if(redirectUrl != null && !redirectUrl.isEmpty())
            response.addObject("requestedResource",redirectUrl);
        else
            response.addObject("requestedResource","");
        
        return response;
    }

    @PostMapping(value ={"login/form","login/form/"}, consumes = "application/x-www-form-urlencoded")
    public ModelAndView loginUser(LoginModel login, Model model, HttpServletResponse response)
    {
        Map<String,Object> authToken = new HashMap<>();
        final int TOKEN_DURATION = 60*30;

        if(login.getUsername() == null)
        {
            model.addAttribute("message","No se ha informado nombre de usuario.");
            return new ModelAndView("inventory/index");
        }

        if(login.getPassword() == null)
        {
            model.addAttribute("message","No se ha informado contrasenia.");
            return new ModelAndView("inventory/index");
        }

        if(login.getUsername().isBlank() || login.getUsername().isEmpty())
        {
            model.addAttribute("message","El nombre de usuario no puede ser vacio");
            return new ModelAndView("inventory/index");
        }

        if(login.getPassword().isBlank() || login.getPassword().isEmpty())
        {
            model.addAttribute("message","La contrasenia debe tener al menos 8 caracteres");
            return new ModelAndView("inventory/index");
        }

        User requestedUser = userRepository.findByUsername(login.getUsername());
        if(requestedUser != null)
        {
            boolean authUser = passwordEncoder.matches(login.getPassword(), requestedUser.getPassword());
            if(authUser)
            {
                authToken.put("username", login.getUsername());
                authToken.put("role", requestedUser.getRole().getRoleId());
                authToken.put("expires", (System.currentTimeMillis() + TOKEN_DURATION));
                String valuesComponent = TokenGenerator.mapToString(authToken);
                String authSignature = TokenGenerator.generateToken(authToken);
                
                String token = TokenGenerator.encodeToken(valuesComponent) + "." + TokenGenerator.encodeToken(authSignature);
                Cookie cookie = new Cookie("auth-token",token);
                cookie.setMaxAge(TOKEN_DURATION);
                cookie.setPath("/"); 
                response.addCookie(cookie);
                String redirectUrl = "/" + login.getRequestedResource();
                return new ModelAndView("redirect:" + redirectUrl);
            }
        }
        model.addAttribute("message","Usuario o contrasenia incorrectos");
        return new ModelAndView("inventory/index");

        
    }

}
