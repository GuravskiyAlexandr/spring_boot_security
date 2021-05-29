package com.example.demo.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;


@Data
@EqualsAndHashCode(of = {"id", "role"})
@AllArgsConstructor
@RequiredArgsConstructor
@ToString(of = {"id", "role"})
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;
    @ManyToMany(mappedBy = "roles")
    public Set<User> user;

    public Role(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Role(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
