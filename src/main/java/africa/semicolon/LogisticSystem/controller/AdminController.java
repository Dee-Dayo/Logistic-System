package africa.semicolon.LogisticSystem.controller;

import africa.semicolon.LogisticSystem.dto.response.*;
import africa.semicolon.LogisticSystem.exceptions.LogisticSystemsExceptions;
import africa.semicolon.LogisticSystem.services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/DeeLogistics")
public class AdminController {

    @Autowired
    AdminServices adminServices;

    @GetMapping("/all_orders")
     public ResponseEntity<?> allOrders(){
        try {
            List<OrderResponse> orders = adminServices.getAllUsersOrders();
            return new ResponseEntity<>(new LogisticsApiResponse(true, orders), ACCEPTED);
        } catch (LogisticSystemsExceptions error){
            return new ResponseEntity<>(new LogisticsApiResponse(false, error.getMessage()), FORBIDDEN);
        }
    }

    @GetMapping("/all_users")
    public ResponseEntity<?> allUsers(){
        try {
            List<AdminUsersResponse> responses = adminServices.getAllUsers();
            return new ResponseEntity<>(new LogisticsApiResponse(true, responses), ACCEPTED);
        } catch (LogisticSystemsExceptions error) {
            return new ResponseEntity<>(new LogisticsApiResponse(false, error.getMessage()), FORBIDDEN);
        }
    }

    @GetMapping("/all-riders")
    public ResponseEntity<?> allRiders(){
        try {
            List<AdminRidersResponse> responses = adminServices.getAllRiders();
            return new ResponseEntity<>(new LogisticsApiResponse(true, responses), ACCEPTED);
        } catch (LogisticSystemsExceptions error) {
            return new ResponseEntity<>(new LogisticsApiResponse(false, error.getMessage()), FORBIDDEN);
        }
    }
}
