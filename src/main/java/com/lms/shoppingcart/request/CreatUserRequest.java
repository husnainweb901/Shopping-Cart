package com.lms.shoppingcart.request;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

@Data
public class CreatUserRequest {


    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
