package africa.semicolon.LogisticSystem.controller;

import africa.semicolon.LogisticSystem.dto.requests.OrderPaymentRequest;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserLoginRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserRegisterRequest;
import africa.semicolon.LogisticSystem.dto.response.*;
import africa.semicolon.LogisticSystem.exceptions.LogisticSystemsExceptions;
import africa.semicolon.LogisticSystem.services.AdminServices;
import africa.semicolon.LogisticSystem.services.OrderService;
import africa.semicolon.LogisticSystem.services.RiderService;
import africa.semicolon.LogisticSystem.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

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
            UserRegisterResponse response = adminServices.register(userRegisterRequest);
            return new ResponseEntity<>(new LogisticsApiResponse(true, response), CREATED);
        } catch (LogisticSystemsExceptions error){
            return new ResponseEntity<>(new LogisticsApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest){
        try {
            UserLoginResponse response = userServices.login(userLoginRequest);
            return new ResponseEntity<>(new LogisticsApiResponse(true, response), ACCEPTED);
        } catch (LogisticSystemsExceptions error){
            return new ResponseEntity<>(new LogisticsApiResponse(false, error.getMessage()), FORBIDDEN);
        }
    }

    @PostMapping("/send_order")
    public ResponseEntity<?> sendOrder(@RequestBody SendOrderRequest sendOrderRequest){
        try {
            UserSendOrderResponse response = userServices.sendOrder(sendOrderRequest);
            return new ResponseEntity<>(new LogisticsApiResponse(true, response), CREATED);
        } catch (LogisticSystemsExceptions error){
            return new ResponseEntity<>(new LogisticsApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/make_payment")
    public ResponseEntity<?> orderPayment(@RequestBody OrderPaymentRequest orderPaymentRequest){
        try {
            OrderPaymentResponse response = userServices.makePayment(orderPaymentRequest);
            return new ResponseEntity<>(new LogisticsApiResponse(true, response), ACCEPTED);
        } catch (LogisticSystemsExceptions error){
            return new ResponseEntity<>(new LogisticsApiResponse(false, error.getMessage()), FORBIDDEN);
        }
    }

}
