package com.lms.shoppingcart.security.user;

import com.lms.shoppingcart.user.User;
import com.lms.shoppingcart.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findUserByEmail(email))
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found "));
        return ShopUserDetails.buildUserDetails(user);
    }
}
