package com.simbrella.simplepay.user_management.service;

import com.simbrella.simplepay.user_management.client.GatewayClient;
import com.simbrella.simplepay.user_management.exception.ResourceNotFoundException;
import com.simbrella.simplepay.user_management.model.*;
import com.simbrella.simplepay.user_management.model.POJO.RegistrationRequest;
import com.simbrella.simplepay.user_management.model.POJO.StandardResponse;
import com.simbrella.simplepay.user_management.model.Role;
import com.simbrella.simplepay.user_management.repository.TokenRepository;
import com.simbrella.simplepay.user_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepo;

//    private final EmailService emailService;

    private final TokenRepository tokenRepository;

    private final GatewayClient gatewayClient;

    private final ValidationService validationService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final int OTP_EXPIRATION_MINUTES = 1;

    public void generateOneTimePassword(String email, User user) throws UnsupportedEncodingException, MessagingException {

        Random random = new Random();

        String OTP = String.format("%04d", random.nextInt(10000));

        String content = "Your OTP is "+OTP+". \n" +
                "\nPlease be sure to use it as soon as possible as the token expires in 1 minute. Thank you";


        String encodeOTP = gatewayClient.encodePassword(OTP);
        user.setVerificationOtp(OTP);
        user.setOtpRequestTime(LocalDateTime.now());

        userRepo.save(user);
        logger.info(" Email not Null and OTP is " + user.getEmail() + " Is " + OTP + " Encoded Otp " + encodeOTP);
//        NService.sendNotificationOTP(user, OTP);
//        emailService.sendSimpleMessage(email, "Email Verification", content);
    }

    public void generateEmailOneTimePassword(User user) throws UnsupportedEncodingException, MessagingException {

        Random random = new Random();
        String OTP = String.format("%04d", random.nextInt(10000));

        String content = "Your OTP is "+OTP+". \n" +
                "\nPlease be sure to use it as soon as possible as the token expires in 1 minute. Thank you";
        user.setResetOtp(gatewayClient.encodePassword(OTP));
        user.setResetOtpRequestTime(LocalDateTime.now());
        userRepo.save(user);
//        NService.sendNotificationResetPassword(user, OTP);
//        emailService.sendSimpleMessage(user.getEmail(), "OTP User Verification", content);

    }

    public ResponseEntity<StandardResponse> register(RegistrationRequest request) {
        try {
            boolean loggedUser = userRepo.findByUsername(request.getUserName()).isPresent();
            boolean emailUser = userRepo.findByEmail(request.getEmail()).isPresent();

            if (!validationService.validateEmail(request.getEmail())){
                return StandardResponse.sendFailedHttpResponse(false, "Invalid email");
            }

            if(!validationService.validatePassword(request.getPassword())){
                return StandardResponse.sendFailedHttpResponse(false, "Please follow the guideline for the password");
            }

            if(!validationService.validatePhoneNumber(request.getPhoneNumber())){
                return StandardResponse.sendFailedHttpResponse(false, "Invalid phone number");
            }

            if (loggedUser) {
                return StandardResponse.sendFailedHttpResponse(false, "Username already exists");
            } else if(emailUser) {
                return StandardResponse.sendFailedHttpResponse(false, "This email is already registered with us.");
            }else {

                Role role = Role.valueOf(request.getRole().toUpperCase());
                var user = User.builder()
                        .firstName(request.getFirstname())
                        .lastName(request.getLastname())
                        .email(request.getEmail())
                        .phoneNumber(request.getPhoneNumber())
                        .username(request.getUserName())
                        .userTimeZone(String.valueOf(LocalDateTime.now()))
                        .password(gatewayClient.encodePassword(request.getPassword()))
                        .role(role)
                        .build();
                var savedUser = userRepo.save(user);
//                saveUserToken(savedUser, jwtToken);
//
//                emailService.sendRegistrationNotification(user);
//                emailService.sendNotificationWelcome(user);

                return StandardResponse.sendHttpResponse(true, "Successfully created user", savedUser, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return StandardResponse.sendFailedHttpResponse(false, "Something went wrong");
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }


    public ResponseEntity<StandardResponse> resendOtp(String userEmail) {
        try {
            Optional<User> userOptional = userRepo.findByEmail(userEmail);
            if (userOptional.isEmpty()) {
                return StandardResponse.sendFailedHttpResponse(false, "Invalid email address.");
            }
            User user = userOptional.get();
            generateOneTimePassword(userEmail, user);

            return StandardResponse.sendHttpResponse(true, "Email successfully sent.", HttpStatus.CREATED);
        } catch (Exception e) {
            StandardResponse sr = new StandardResponse();
            sr.setMessage("An error occurred. Please try again");
            return new ResponseEntity<>(sr, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<StandardResponse> forgetPassword(String userName)
            throws UnsupportedEncodingException, MessagingException {
        try {
            Optional<User> userOptional = userRepo.findByEmail(userName);
            if (userOptional.isEmpty()) {
                return StandardResponse.sendFailedHttpResponse(false, "User with userName \b"+userName+" does not exit.");
            }
            User user = userOptional.get();
            generateEmailOneTimePassword(user);
            return StandardResponse.sendHttpResponse(true, "Email sent successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            StandardResponse sr = new StandardResponse();
            sr.setMessage("An error occurred. Please try again");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(sr);
        }

    }

    public String generateUniqueResetToken() {
        byte[] randomBytes = new byte[5];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }


    public ResponseEntity<StandardResponse> verifyCode(String verificationOtp, String email) {
        try {
            Optional<User> userOptional = userRepo.findByVerificationOtpAndEmail(verificationOtp, email);
            if (userOptional.isEmpty()) {
                return StandardResponse.sendFailedHttpResponse(false, "Invalid OTP or email.");
            }

            User user = userOptional.get();
            if (!user.getVerificationOtp().equals(verificationOtp)) {
                return StandardResponse.sendFailedHttpResponse(false, "Incorrect OTP.");
            }

            long diff = Duration.between(user.getOtpRequestTime(), LocalDateTime.now()).toMinutes();
            if (diff >= OTP_EXPIRATION_MINUTES) {
                return StandardResponse.sendFailedHttpResponse(false, "The OTP has expired. Please request a new one.");
            }

            user.setEmailVerified(true);
            userRepo.save(user);
            return StandardResponse.sendHttpResponse(true, "Email verification successful.", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return StandardResponse.sendFailedHttpResponse(false, "User not found.");
        } catch (Exception e) {
            return StandardResponse.sendFailedHttpResponse(false, "An error occurred.");
        }
    }

    public ResponseEntity<StandardResponse> verifyResetCode(String resetToken, String email) {
        try {
            Optional<User> userOptional = userRepo.findByEmailAndResetOtp(email, resetToken);
            if (userOptional.isEmpty()) {
                return StandardResponse.sendFailedHttpResponse(false, "Invalid OTP or email.");
            }

            User user = userOptional.get();
            if (!user.getResetOtp().equals(resetToken)) {
                return StandardResponse.sendFailedHttpResponse(false, "Incorrect OTP.");
            }

            long diff = Duration.between(user.getResetOtpRequestTime(), LocalDateTime.now()).toMinutes();
            if (diff >= OTP_EXPIRATION_MINUTES) {
                return StandardResponse.sendFailedHttpResponse(false, "The OTP has expired. Please request a new one.");
            }

            user.setEmailVerified(true);
            userRepo.save(user);
            return StandardResponse.sendHttpResponse(true, "Email verification successful.", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return StandardResponse.sendFailedHttpResponse(false, "User not found.");
        } catch (Exception e) {
            return StandardResponse.sendFailedHttpResponse(false, "An error occurred.");
        }
    }

    public ResponseEntity<StandardResponse> resetPasswordOptimized(String email, String newPassword) {
        try {
            Optional<User> userOptional = userRepo.findByEmail(email);
            if (userOptional.isEmpty()) {
                return StandardResponse.sendFailedHttpResponse(false, "Invalid email.");
            }
            User user = userOptional.get();
            user.setPassword(gatewayClient.encodePassword(newPassword)); // Assuming you're using password encoding
            user.setResetOtp(null);
            userRepo.save(user);

            return StandardResponse.sendHttpResponse(true, "Password reset successful.", HttpStatus.ACCEPTED);
        } catch (NoSuchElementException e) {
            return StandardResponse.sendFailedHttpResponse(false, "User not found.");
        } catch (Exception e) {
            return StandardResponse.sendFailedHttpResponse(false, "An error occurred. Please try again.");
        }
    }


    public ResponseEntity<StandardResponse> resetPassword(String password, String email) {
        try {
            String encodedPassword = gatewayClient.encodePassword(password);
            User user;
            Optional<User> usr = userRepo.findByEmail(email);
            if (usr.isPresent()) {
                user = usr.get();
                user.setPassword(encodedPassword);
                userRepo.save(user);
            }
            return StandardResponse.sendHttpResponse(true, "Successful", HttpStatus.CREATED);
        } catch (Exception e) {
            return StandardResponse.sendFailedHttpResponse(false, "Could not update password");
        }

    }

    public ResponseEntity<StandardResponse> updateUser(User user) throws MessagingException, UnsupportedEncodingException {

        User userDB = userRepo.findById(user.getId()).orElseThrow();
        if (Objects.nonNull(user.getFirstName()) && !"".equalsIgnoreCase(user.getFirstName())) {
            userDB.setFirstName(user.getFirstName());
        }

        if (Objects.nonNull(user.getLastName()) && !"".equalsIgnoreCase(user.getLastName())) {
            userDB.setLastName(user.getLastName());
        }

        if (Objects.nonNull(user.getEmail()) && !"".equalsIgnoreCase(user.getEmail())) {
            if(!userDB.getEmail().equals(user.getEmail())){
                boolean emailUser = userRepo.findByEmail(user.getEmail()).isPresent();
                if(emailUser){
                    return StandardResponse.sendFailedHttpResponse(false, "User with email already exits");
                }
                userDB.setEmail(user.getEmail());
                userDB.setEmailVerified(false);
                generateOneTimePassword(user.getEmail(), userDB);
            }
        }

        if (Objects.nonNull(user.getUsername()) && !"".equalsIgnoreCase(user.getUsername())) {
            if(!userDB.getUsername().equals(user.getUsername())) {
                boolean loggedUser = userRepo.findByUsername(user.getUsername()).isPresent();
                if (loggedUser) {
                    return StandardResponse.sendFailedHttpResponse(false, "UserName is already taken");
                } else {
                    userDB.setUsername(user.getUsername());
                }
            }
        }

        if (Objects.nonNull(user.getPhoneNumber()) && !"".equalsIgnoreCase(user.getPhoneNumber())) {
            userDB.setPhoneNumber(user.getPhoneNumber());
        }
        return StandardResponse.sendHttpResponse(true, "Successfully updated", userRepo.save(userDB), HttpStatus.OK);
    }

    public ResponseEntity<StandardResponse> uploadPhoto(int id, MultipartFile multipartFile) {
        try {

            User user = userRepo.findById(id).orElseThrow();

            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            user.setPhotos(fileName);

            User savedUser = userRepo.save(user);

            String uploadDir = "user-photos/" + savedUser.getUserId();

//            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            return StandardResponse.sendHttpResponse(true, "Successful", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return StandardResponse.sendFailedHttpResponse(false, "Could not upload photo");
        }
    }


    public ResponseEntity<StandardResponse> deleteUser(int id) {
        userRepo.deleteById(id);
        return StandardResponse.sendHttpResponse(true, "Successfully deleted user", HttpStatus.OK);
    }


    public ResponseEntity<StandardResponse> getAllUsers(){
        try {
            List<User> userList = userRepo.findAllByRole(Role.USER);
            return StandardResponse.sendHttpResponse(true, "Successful", userList, HttpStatus.OK);
        } catch (Exception e) {
            return StandardResponse.sendFailedHttpResponse(false, "Something went wrong");
        }
    }

    public ResponseEntity<StandardResponse> getUserById(int id) {
        try {
            Optional<User> optionalUser = userRepo.findById(id);
            return optionalUser.map(user -> StandardResponse.sendHttpResponse(true, "Successful", user, HttpStatus.OK)).orElseGet(() -> StandardResponse.sendFailedHttpResponse(false, "User does not exist"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByUserName(String userName) {
        try {
            return userRepo.findByUsername(userName).orElseThrow(() -> new ResourceNotFoundException(("Could not find User by Username")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByEmail(String email) {
        try {
            return userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(("Could not find User")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
