package com.simbrella.simplepay.user_management.controller;

import com.simbrella.simplepay.user_management.model.POJO.RegistrationRequest;
import com.simbrella.simplepay.user_management.model.POJO.StandardResponse;
import com.simbrella.simplepay.user_management.service.UserService;
import com.simbrella.simplepay.user_management.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registeruser")
    public ResponseEntity<StandardResponse> register(
            @RequestBody RegistrationRequest request
    ) {
        return userService.register(request);
    }

    @GetMapping("/resendOTP")
    public ResponseEntity<StandardResponse> resendOTP(@RequestParam("email") String email) throws MessagingException, UnsupportedEncodingException {
        return userService.resendOtp(email);
    }

    @GetMapping("/resetpassword")
    public ResponseEntity<StandardResponse> resetPassWord(@RequestParam("email") String email,
                                                          @RequestParam("newPassword") String newPassword) throws MessagingException, UnsupportedEncodingException {
        return userService.resetPassword(newPassword, email);
    }

    @GetMapping("/forgotpassword")
    public ResponseEntity<StandardResponse> forgetPassword(@RequestParam("email") String email) throws MessagingException, UnsupportedEncodingException {
        return userService.forgetPassword(email);
    }

    @GetMapping("/verifycode")
    public ResponseEntity<StandardResponse> verifyCode(@RequestParam("verificationOtp") String verificationOtp, @RequestParam("email") String email){
        return userService.verifyCode(verificationOtp, email);
    }

    @GetMapping("/verifyresetcode")
    public ResponseEntity<StandardResponse> verifyResetCode(@RequestParam("resetToken") String resetToken,
                                                            @RequestParam("email") String email){
        return userService.verifyResetCode(resetToken, email);
    }

    @GetMapping("/getuserinfo")
    public ResponseEntity<StandardResponse> getUserInfo(@RequestParam("id") int id){
        return userService.getUserById(id);
    }

    @PutMapping("/updateuserinfo")
    public ResponseEntity<StandardResponse> updateUserInfo(@RequestBody User user) throws MessagingException, UnsupportedEncodingException {
        return userService.updateUser(user);
    }

    @GetMapping("/getuserbyusername")
    public User getUserByUserName(@RequestParam("username") String username){
        return userService.getUserByUserName(username);
    }

    @DeleteMapping("/deleteuser")
    public ResponseEntity<StandardResponse> deleteUser(@RequestParam("id") int id){
        return userService.deleteUser(id);
    }

    @GetMapping("/getallusers")
    public ResponseEntity<StandardResponse> getAllUser(){
        return userService.getAllUsers();
    }

    @GetMapping("/getuserbyemail")
    public User getUserByEmail(@RequestParam("email") String email){
        return userService.getUserByEmail(email);
    }
}
