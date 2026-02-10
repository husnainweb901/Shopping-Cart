package com.lms.shoppingcart.user;

import com.lms.shoppingcart.request.CreatUserRequest;
import com.lms.shoppingcart.request.UserUpdateRequest;

public interface IUserService{

    User getUserById(Long userId);
    User createUser(CreatUserRequest user);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);
}
