package com.lms.shoppingcart.user;

import com.lms.shoppingcart.dto.UserDto;
import com.lms.shoppingcart.exception.AlreadyExistsException;
import com.lms.shoppingcart.exception.ResourceNotFoundException;
import com.lms.shoppingcart.request.CreatUserRequest;
import com.lms.shoppingcart.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
    }

    @Override
    public User createUser(CreatUserRequest request) {
        return Optional.of(request)
                .filter(user-> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());

                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException(request.getEmail() +" User Already exists"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {

        return userRepository.findById(userId).map(existinhUser->{
            existinhUser.setFirstName(request.getFirstName());
            existinhUser.setLastName(request.getLastName());
            return  userRepository.save(existinhUser);
        }).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository ::delete, ()-> {
            throw new ResourceNotFoundException("user not Found");
        });
    }

    @Override
    public UserDto convertUserToDto(User user)
    {
        return modelMapper.map(user, UserDto.class);
    }
}
