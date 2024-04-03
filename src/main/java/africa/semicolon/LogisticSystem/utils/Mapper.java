package africa.semicolon.LogisticSystem.utils;


import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.dto.requests.UserRegisterRequest;

public class Mapper {

     public static User requestMap(UserRegisterRequest userRegisterRequest){
        User user = new User();
        user.setPhoneNumber(userRegisterRequest.getPhoneNumber());
        user.setPassword(userRegisterRequest.getPassword());
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setProduct(userRegisterRequest.getProduct());
        return user;
    }
}
