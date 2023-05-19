package br.senai.sc.services;

import br.senai.sc.models.User;
import br.senai.sc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

@Service
public class UserService {
    private static final Logger LOG = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void save() {
        try {
            User user = new User();
            user.setName("Usuário");
            user.setEmail("usuario@sc.senai.br");
            userRepository.save(user);
        } catch (Exception e) {
            LOG.severe(e.getMessage());
        }
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
