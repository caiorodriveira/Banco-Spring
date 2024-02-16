package br.senai.sc.models;

import br.senai.sc.enums.Roles;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;

@Data

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Campo login obrigatório!")
    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    @NotEmpty(message = "Campo senha obrigatório!")
    private String senha;

    @NotEmpty(message = "Campo nome obrigatório!")
    @Column(nullable = false)
    private String nome;

    @NotEmpty(message = "Campo email  obrigatório!")
    @Email(message = "E-mail inválido!")
    @Column(unique = true)
    private String email;

    @Column(name = "ds_role")
    private Roles role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role == Roles.ADMIN) return List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_FUNC"));
        else if(role == Roles.USER) return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return List.of(new SimpleGrantedAuthority("ROLE_FUNC"));
    }

    @Override
    public String getPassword() {
        return getSenha();
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
