package africa.semicolon.LogisticSystem.utils;


import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.dto.requests.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.requests.UserRegisterRequest;
import africa.semicolon.LogisticSystem.dto.requests.response.UserLoginResponse;
import africa.semicolon.LogisticSystem.dto.requests.response.UserRegisterResponse;
import africa.semicolon.LogisticSystem.dto.requests.response.UserSendOrderResponse;

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
         response.setOrderStatus(order.isPending());
         response.setRiderName(order.getIsAssignedTo().getFirstName());
         return response;
    }


    public static Order requestMap(SendOrderRequest sendOrderRequest){
         Order order = new Order();
         order.setSender(sendOrderRequest.getSender());
         order.setReceiver(sendOrderRequest.getReceiver());
         order.setProduct(sendOrderRequest.getProduct());
         return order;
    }
}
