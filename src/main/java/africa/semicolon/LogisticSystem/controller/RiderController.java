package africa.semicolon.LogisticSystem.controller;

import africa.semicolon.LogisticSystem.dto.requests.RiderLoginRequest;
import africa.semicolon.LogisticSystem.dto.requests.RiderRegisterRequest;
import africa.semicolon.LogisticSystem.dto.response.LogisticsApiResponse;
import africa.semicolon.LogisticSystem.dto.response.RiderLoginResponse;
import africa.semicolon.LogisticSystem.dto.response.RiderRegisterResponse;
import africa.semicolon.LogisticSystem.exceptions.LogisticSystemsExceptions;
import africa.semicolon.LogisticSystem.services.AdminServices;
import africa.semicolon.LogisticSystem.services.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/api/DeeLogistics")
public class RiderController {

    @Autowired
    AdminServices adminServices;
    @Autowired
    RiderService riderServices;

    @PostMapping("/register-rider")
    public ResponseEntity<?> register(@RequestBody RiderRegisterRequest riderRegisterRequest){
        try {
            RiderRegisterResponse response = adminServices.register(riderRegisterRequest);
            return new ResponseEntity<>(new LogisticsApiResponse(true, response), CREATED);
        } catch (LogisticSystemsExceptions error){
            return new ResponseEntity<>(new LogisticsApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/login-rider")
    public ResponseEntity<?> login(@RequestBody RiderLoginRequest riderLoginRequest){
        try {
            RiderLoginResponse response = riderServices.login(riderLoginRequest);
            return new ResponseEntity<>(new LogisticsApiResponse(true, response), ACCEPTED);
        } catch (LogisticSystemsExceptions error){
            return new ResponseEntity<>(new LogisticsApiResponse(false, error.getMessage()), FORBIDDEN);
        }
    }
}
