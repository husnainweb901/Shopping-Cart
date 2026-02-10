package com.lms.shoppingcart.role;

import com.lms.shoppingcart.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
