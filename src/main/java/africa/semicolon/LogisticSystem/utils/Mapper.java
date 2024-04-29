package africa.semicolon.LogisticSystem.utils;


import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.Rider;
import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.dto.requests.RiderRegisterRequest;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserRegisterRequest;
import africa.semicolon.LogisticSystem.dto.response.*;

import java.time.format.DateTimeFormatter;

public class Mapper {

    public static Rider requestMap(RiderRegisterRequest riderRegisterRequest){
        Rider rider = new Rider();
        rider.setFirstName(riderRegisterRequest.getFirstName());
        rider.setLastName(riderRegisterRequest.getLastName());
        rider.setPhoneNumber(riderRegisterRequest.getPhoneNumber());
        rider.setPassword(riderRegisterRequest.getPassword());
        rider.setAvailable(true);
        return rider;
    }

     public static User requestMap(UserRegisterRequest userRegisterRequest){
        User user = new User();
        user.setPhoneNumber(userRegisterRequest.getPhoneNumber());
        user.setPassword(userRegisterRequest.getPassword());
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setAddress(userRegisterRequest.getAddress());
        return user;
    }

    public static UserRegisterResponse responseMap(User user){
        UserRegisterResponse response = new UserRegisterResponse();
        response.setId(user.getId());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        return response;
    }

    public static RiderRegisterResponse responseMap(Rider rider){
        RiderRegisterResponse response = new RiderRegisterResponse();
        response.setId(rider.getId());
        response.setPhoneNumber(rider.getPhoneNumber());
        response.setFirstName(rider.getFirstName());
        response.setLastName(rider.getLastName());
        return response;
    }

    public static UserLoginResponse loginResponseMap(User user){
        UserLoginResponse response = new UserLoginResponse();
        response.setPhoneNumber(user.getPhoneNumber());
        response.setId(user.getId());
        return response;
    }

    public static UserSendOrderResponse sendOrderResponseMap(Order order){
         UserSendOrderResponse response = new UserSendOrderResponse();
         response.setOrderId(order.getId());
         response.setDateCreated(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(order.getDateCreated()));
         response.setAmount(order.getAmount());
         response.setRiderName(order.getIsAssignedTo().getFirstName());
         return response;
    }

    public static OrderPaymentResponse orderConfirmationResponseMap(Order order){
        OrderPaymentResponse orderPaymentResponse = new OrderPaymentResponse();
        orderPaymentResponse.setOrderId(order.getId());
        orderPaymentResponse.setDateCreated(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(order.getDateCreated()));
        orderPaymentResponse.setRiderName(order.getIsAssignedTo().getFirstName());
        orderPaymentResponse.setPaymentStatus(order.isPaid());
        orderPaymentResponse.setDateCollected(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(order.getDateCollected()));
        return orderPaymentResponse;
    }

    public static OrderStatusResponse checkStatusResponseMap (Order order){
         OrderStatusResponse orderStatusResponse = new OrderStatusResponse();
         orderStatusResponse.setOrderId(order.getId());
         orderStatusResponse.setDateCreated(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(order.getDateCreated()));
         orderStatusResponse.setPaymentMade(order.isPaid());
         orderStatusResponse.setDateCollected(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(order.getDateCollected()));
         orderStatusResponse.setCompleted(order.isDelivered());
         orderStatusResponse.setDateDelivered(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(order.getDateDelivered()));
        return orderStatusResponse;
     }
}
