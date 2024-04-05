package africa.semicolon.LogisticSystem.controller;

import africa.semicolon.LogisticSystem.dto.requests.requests.UserRegisterRequest;
import africa.semicolon.LogisticSystem.dto.requests.response.LogisticsApiResponse;
import africa.semicolon.LogisticSystem.dto.requests.response.UserRegisterResponse;
import africa.semicolon.LogisticSystem.exceptions.LogisticSystemsExceptions;
import africa.semicolon.LogisticSystem.services.AdminServices;
import africa.semicolon.LogisticSystem.services.OrderService;
import africa.semicolon.LogisticSystem.services.RiderService;
import africa.semicolon.LogisticSystem.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/DeeLogistics")
public class LogisticSystemController {

    @Autowired
    AdminServices adminServices;
    @Autowired
    UserServices userServices;
    @Autowired
    RiderService riderService;
    @Autowired
    OrderService orderService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest userRegisterRequest){
        try {
            UserRegisterResponse response =adminServices.register(userRegisterRequest);
            return new ResponseEntity<>(new LogisticsApiResponse(true, response), CREATED);
        } catch (LogisticSystemsExceptions message){
            return new ResponseEntity<>(new LogisticsApiResponse(false, message), BAD_REQUEST);
        }
    }

}
