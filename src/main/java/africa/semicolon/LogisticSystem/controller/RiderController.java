package africa.semicolon.LogisticSystem.controller;

import africa.semicolon.LogisticSystem.dto.requests.RiderRegisterRequest;
import africa.semicolon.LogisticSystem.dto.response.LogisticsApiResponse;
import africa.semicolon.LogisticSystem.dto.response.RiderRegisterResponse;
import africa.semicolon.LogisticSystem.exceptions.LogisticSystemsExceptions;
import africa.semicolon.LogisticSystem.services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/DeeLogistics")
public class RiderController {

    @Autowired
    AdminServices adminServices;

    @PostMapping("/register-rider")
    public ResponseEntity<?> register(@RequestBody RiderRegisterRequest riderRegisterRequest){
        try {
            RiderRegisterResponse response = adminServices.register(riderRegisterRequest);
            return new ResponseEntity<>(new LogisticsApiResponse(true, response), CREATED);
        } catch (LogisticSystemsExceptions error){
            return new ResponseEntity<>(new LogisticsApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }
}
