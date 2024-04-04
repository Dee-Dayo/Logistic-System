package africa.semicolon.LogisticSystem.utils;


import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserRegisterRequest;

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

    public static Order requestMap(SendOrderRequest sendOrderRequest){
         Order order = new Order();
         order.setSender(sendOrderRequest.getSender());
         order.setReceiver(sendOrderRequest.getReceiver());
         order.setProduct(sendOrderRequest.getProduct());
//         order.setPaid(sendOrderRequest.isPaid());
         return order;
    }
}
