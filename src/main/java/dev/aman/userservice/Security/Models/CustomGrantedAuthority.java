package dev.aman.userservice.Security.Models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.aman.userservice.Models.Role;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority {

    // Convert role into customGrantedAuthority
    private String authority;

    // if jackson deserialize the we need to have a default constructor.
    public CustomGrantedAuthority() {}

    public CustomGrantedAuthority(Role role) {
        this.authority = role.getName();
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
