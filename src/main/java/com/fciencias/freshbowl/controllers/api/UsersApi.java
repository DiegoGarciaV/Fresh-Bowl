package com.fciencias.freshbowl.controllers.api;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fciencias.freshbowl.models.ApiResponse;
import com.fciencias.freshbowl.models.LoginModel;
import com.fciencias.freshbowl.models.User;
import com.fciencias.freshbowl.services.users.UserRepository;
import com.fciencias.freshbowl.utils.TokenGenerator;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/users")
public class UsersApi {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UsersApi(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId)
    {
        User foundUser = userRepository.findById(userId).orElse(null);
        if(foundUser == null)
            return ResponseEntity.noContent().build();
        else
        {
           foundUser.setPassword(null);
            return ResponseEntity.ok(foundUser);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers()
    {
        List<User> allUsers = userRepository.findAll();
        if(allUsers.isEmpty())
            return ResponseEntity.noContent().build();
        else
        {
            for(User user : allUsers)
                user.setPassword(null);
            return ResponseEntity.ok(allUsers);
        }
    }

    @PostMapping("/")
    @Validated
    public ResponseEntity<Object> createUser(@RequestBody @Valid User user, BindingResult bindingResult)
    {
        User createdUser = null;
        if (bindingResult.hasErrors()) 
        {
            Map<String,Object> errorMessage = new HashMap<>();
            Map<String,Object> constraintsViolations = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) 
                constraintsViolations.put(error.getField(), error.getDefaultMessage());

            errorMessage.put("errors", constraintsViolations);
            errorMessage.put("User", user);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        if(user!= null)
        {

            User checkUserById = userRepository.findById(user.getUserId()).orElse(null);
            User checkUserByUsername = userRepository.findByUsername(user.getUsername());

            if(checkUserById != null || checkUserByUsername != null)
            {
                Map<String,Object> errorMessage = new HashMap<>();
                errorMessage.put("errors", "Usuario ya existente");
                errorMessage.put("User", user);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
            }
            
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
            createdUser = userRepository.save(user);
            createdUser.setPassword(null);
            return ResponseEntity.created(URI.create("/api/users/" + createdUser.getUserId())).build();
        }
        else
            return ResponseEntity.badRequest().body("El usuario no puede ser nulo");

        
    }

    @PutMapping("/")
    public ResponseEntity<User> updateUser(@RequestBody User user)
    {
        if(userRepository.findById(user.getUserId()).orElse(null) != null)
        {
            User updatedUser = userRepository.save(user);
            updatedUser.setPassword(null);
            return ResponseEntity.ok(updatedUser);
        }
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId)
    {
        if(userRepository.findById(userId).isEmpty())
            return ResponseEntity.notFound().build();
            
        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value ="/login", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<ApiResponse> validateUser(LoginModel login)
    {
        ApiResponse apiResponse = new ApiResponse();
        if(login.getUsername() == null)
        {
            apiResponse.setResultMessage("No se ha informado nombre de usuario.");
            return ResponseEntity.badRequest().body(apiResponse);
        }

        if(login.getPassword() == null)
        {
            apiResponse.setResultMessage("No se ha informado contrasenia.");
            return ResponseEntity.badRequest().body(apiResponse);
        }

        if(login.getUsername().isBlank() || login.getUsername().isEmpty())
        {
            apiResponse.setResultMessage("El nombre de usuario no puede ser vacio");
            return ResponseEntity.badRequest().body(apiResponse);
        }

        if(login.getPassword().isBlank() || login.getPassword().isEmpty())
        {
            apiResponse.setResultMessage("La contrasenia debe tener al menos 8 caracteres");
            return ResponseEntity.badRequest().body(apiResponse);
        }

        User requestedUser = userRepository.findByUsername(login.getUsername());
        if(requestedUser != null)
        {
            boolean authUser = passwordEncoder.matches(login.getPassword(), requestedUser.getPassword());
            apiResponse.setResultState(authUser);
            if(authUser)
            {
                apiResponse.setResultMessage("Usuario verificado.");
                return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
            }
        }
        apiResponse.setResultMessage("Usuario o contrasenia incorrectos");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);

        
    }

    @PostMapping(value ="/login-form", consumes = "application/x-www-form-urlencoded")
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
                System.out.println(TokenGenerator.mapToString(authToken));
                String valuesComponent = TokenGenerator.mapToString(authToken);
                String authSignature = TokenGenerator.generateToken(authToken);
                
                String token = TokenGenerator.encodeToken(valuesComponent) + "." + TokenGenerator.encodeToken(authSignature);
                Cookie cookie = new Cookie("auth-token",token);
                cookie.setMaxAge(TOKEN_DURATION);
                cookie.setPath("/"); 
                response.addCookie(cookie);
                return new ModelAndView("index");
            }
        }
        model.addAttribute("message","Usuario o contrasenia incorrectos");
        return new ModelAndView("inventory/index");

        
    }
    
    
}
