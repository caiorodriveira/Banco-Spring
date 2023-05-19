package br.senai.sc.controllers;

import br.senai.sc.models.User;
import br.senai.sc.services.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller
@Scope(value = "request")
public class UserController {
    private static final String PAGE_USER = "/public/user.jsf";

    @Autowired
    private UserService userService;

    @Getter
    @Setter
    @Autowired
    private User user;

    public String findByEmail(String email) {
        user = userService.findByEmail(email);
        return PAGE_USER;
    }
}
