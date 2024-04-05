package africa.semicolon.LogisticSystem.utils;


import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserRegisterRequest;
import africa.semicolon.LogisticSystem.dto.response.OrderPaymentResponse;
import africa.semicolon.LogisticSystem.dto.response.UserLoginResponse;
import africa.semicolon.LogisticSystem.dto.response.UserRegisterResponse;
import africa.semicolon.LogisticSystem.dto.response.UserSendOrderResponse;

import java.time.format.DateTimeFormatter;

public class Mapper {

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


//    public static Order requestMap(SendOrderRequest sendOrderRequest){
//         Order order = new Order();
//         order.setSenderPhone(sendOrderRequest.getSenderPhone());
//         order.setReceiverPhone(sendOrderRequest.getReceiverPhone());
//         order.setProduct(sendOrderRequest.getProduct());
//         return order;
//    }
}
