package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Admin;
import africa.semicolon.LogisticSystem.data.models.Product;
import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.data.repositories.OrderRepository;
import africa.semicolon.LogisticSystem.data.repositories.UserRepository;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserLoginRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserRegisterRequest;
import africa.semicolon.LogisticSystem.exceptions.InvalidPasswordException;
import africa.semicolon.LogisticSystem.exceptions.InvalidPhoneNumber;
import africa.semicolon.LogisticSystem.exceptions.UserAlreadyExistException;
import africa.semicolon.LogisticSystem.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static africa.semicolon.LogisticSystem.utils.Mapper.requestMap;

@Service
public class AdminServicesImpl implements AdminServices{
    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public void collectOrder(SendOrderRequest sendOrderRequest) {
//        User user = userServices.findUserByNumber(sendOrderRequest.getSender().getPhoneNumber());
//        Product product = user.getProduct();
//        user.setProduct(null);
//        userRepository.save(user);
    }

    @Override
    public Long findNoOfUsers() {
        return userRepository.count();
    }

    @Override
    public void register(UserRegisterRequest userRegisterRequest) {
        validateLength(userRegisterRequest.getPhoneNumber());
        validatePhoneNumber(userRegisterRequest.getPhoneNumber());

        User newUser = requestMap(userRegisterRequest);
        userRepository.save(newUser);
    }

    @Override
    public int findNoOfRiders() {
        Admin admin = new Admin();
        return admin.getRiders().length;
    }

    private void validateLength(String phoneNumber) {
        String number = phoneNumber.strip();
        if (number.length() != 11) throw new InvalidPhoneNumber("Invalid phone number");
    }

    private void validatePhoneNumber(String phoneNumber) {
        boolean userExists = userRepository.existsByPhoneNumber(phoneNumber);
        if (userExists) throw new UserAlreadyExistException("Phone number already exist");
    }

}
