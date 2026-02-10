package com.lms.shoppingcart.user;

import com.lms.shoppingcart.request.CreatUserRequest;
import com.lms.shoppingcart.request.UserUpdateRequest;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Service
public class UserService implements IUserService{

    @Override
    public User getUserById(Long userId) {
        return null;
    }

    @Override
    public User createUser(CreatUserRequest user) {
        return null;
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }
}
