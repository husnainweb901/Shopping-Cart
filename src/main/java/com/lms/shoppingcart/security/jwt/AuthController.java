package com.lms.shoppingcart.security.jwt;


import com.lms.shoppingcart.request.LogInRequest;
import com.lms.shoppingcart.response.ApiResponse;
import com.lms.shoppingcart.response.JwtResponse;
import com.lms.shoppingcart.security.user.ShopUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LogInRequest request){
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateTokenForUser(authentication);
            ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
            JwtResponse jwtResponse = new JwtResponse(userDetails.getUserId(), jwt);

            return ResponseEntity.ok().body(new ApiResponse("Longin Successful",jwtResponse));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(UNAUTHORIZED)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }
}
