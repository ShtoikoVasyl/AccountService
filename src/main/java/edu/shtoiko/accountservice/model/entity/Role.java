package edu.shtoiko.accountservice.model.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Role implements GrantedAuthority {

    private long id;

    private String name;

    public String getAuthority() {
        return name;
    }

    public Role(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Role role = (Role) o;
        return Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}