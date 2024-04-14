package africa.semicolon.LogisticSystem.controller;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.dto.requests.OrderPaymentRequest;
import africa.semicolon.LogisticSystem.dto.response.LogisticsApiResponse;
import africa.semicolon.LogisticSystem.dto.response.OrderPaymentResponse;
import africa.semicolon.LogisticSystem.exceptions.LogisticSystemsExceptions;
import africa.semicolon.LogisticSystem.services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/api/DeeLogistics")
public class AdminController {

    @Autowired
    AdminServices adminServices;

    @GetMapping("/all_orders")
     public ResponseEntity<?> allOrders(){
        try {
            List<Order> orders = adminServices.getAllOrders();
            return new ResponseEntity<>(new LogisticsApiResponse(true, orders), ACCEPTED);
        } catch (LogisticSystemsExceptions error){
            return new ResponseEntity<>(new LogisticsApiResponse(false, error.getMessage()), FORBIDDEN);
        }
    }
}
