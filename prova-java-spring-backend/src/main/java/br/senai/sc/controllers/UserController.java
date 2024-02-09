package br.senai.sc.controllers;

import br.senai.sc.DTO.LoginDTO;
import br.senai.sc.DTO.UserDTO;
import br.senai.sc.exceptions.NotFoundException;
import br.senai.sc.models.User;
import br.senai.sc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
@CrossOrigin
public class UserController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserService userService;

    @GetMapping
    ResponseEntity listar(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @PostMapping("/login")
    ResponseEntity login(@RequestBody @Valid LoginDTO login) {
         var userAuthentication = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
         var auth = authManager.authenticate(userAuthentication);

         return ResponseEntity.status(HttpStatus.ACCEPTED).body("logado com sucesso");
    }

    @PostMapping
     ResponseEntity salvarUsuario(@RequestBody @Valid UserDTO user) throws Exception{

        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getSenha());

        User newUser = new User(
                null,
                user.getLogin(),
                encryptedPassword,
                user.getNome(),
                user.getEmail(),
                user.getRole()
        );

        User userCreated = userService.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deletarUsuario(@PathVariable Long id) throws Exception{

        Optional<User> userFounded =userService.findById(id);
        if(userFounded.isEmpty()){
            throw new NotFoundException();
        }
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userFounded);
    }

    @PutMapping("/{id}")
    ResponseEntity editarUsuario(@RequestBody UserDTO user, @PathVariable Long id) throws Exception{
        Optional<User> userFounded =userService.findById(id);
        if(userFounded.isEmpty()){
            throw new NotFoundException();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getSenha());

        User newUser = new User(
                null,
                user.getLogin(),
                encryptedPassword,
                user.getNome(),
                user.getEmail(),
                user.getRole()
        );

        User userChanged = userService.save(newUser);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userChanged);
    }

}
