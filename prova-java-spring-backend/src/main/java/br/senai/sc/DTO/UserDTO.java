package br.senai.sc.DTO;

import br.senai.sc.enums.Roles;
import lombok.Data;

@Data
public class UserDTO {
    private String login, nome, senha, email;
    private Roles role;

}
