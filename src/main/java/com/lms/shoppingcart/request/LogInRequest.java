package com.lms.shoppingcart.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class LogInRequest {

    private String email;
    private String password;
}
