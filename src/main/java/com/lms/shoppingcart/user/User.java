package com.lms.shoppingcart.user;


import com.lms.shoppingcart.cart.Cart;
import com.lms.shoppingcart.order.Order;
import jakarta.persistence.*;
import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;

    @NaturalId
    private String email;
    private String password;

    @OneToOne(mappedBy = "user", cascade =  CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> order;
}
