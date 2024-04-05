package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.Product;
import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.data.repositories.UserRepository;
import africa.semicolon.LogisticSystem.dto.requests.OrderPaymentRequest;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserLoginRequest;
import africa.semicolon.LogisticSystem.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static africa.semicolon.LogisticSystem.utils.Mapper.requestMap;

@Service
public class UserServicesImpl implements UserServices{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminServices adminServices;
    @Autowired
    private OrderService orderService;


    @Override
    public boolean isUserLoggedIn(String username) {
        User user = findUserByNumber(username.toLowerCase());
        return user.isLoggedIn();
    }

    @Override
    public void login(UserLoginRequest userLoginRequest) {
        User user = findUserByNumber(userLoginRequest.getPhoneNumber());
        validatePassword(userLoginRequest);
        user.setLoggedIn(true);
        userRepository.save(user);
    }

    private void validatePassword(UserLoginRequest userLoginRequest) {
        User user = findUserByNumber(userLoginRequest.getPhoneNumber());
        if(!user.getPassword().equals(userLoginRequest.getPassword())) throw new InvalidPasswordException("Wrong password");
    }

    public User findUserByNumber(String username) {
        User user = userRepository.findByPhoneNumber(username);
        if (user == null) throw new UserNotFoundException("Phone number does not exist");
        return user;
    }

    @Override
    public void sendOrder(SendOrderRequest sendOrderRequest) {
        User sender = userRepository.findByPhoneNumber(sendOrderRequest.getSender().getPhoneNumber());
        validateSenderIsLoggedIn(sender);
        Product product = sendOrderRequest.getProduct();
        validateProduct(product);
//        sender.setProduct(null);
//        sender.setSent(true);

        validateReceiver(sendOrderRequest.getReceiver().getPhoneNumber());
//        User receiver = userRepository.findByPhoneNumber(sendOrderRequest.getReceiver().getPhoneNumber());
//        receiver.setProduct(product);
//        receiver.setReceived(true);

        Order order = requestMap(sendOrderRequest);
        adminServices.takeOrder(order);

//        userRepository.save(sender);
//        userRepository.save(receiver);
    }

    @Override
    public Order trackOrderById(String id) {
        return orderService.getOrderById(id);
    }

    @Override
    public void makePayment(OrderPaymentRequest orderPaymentRequest) {
//        Order order = trackOrderById(orderPaymentRequest.getOrderId());
        adminServices.sendOrder(orderPaymentRequest);
    }

    private void validateReceiver(String phoneNumber) {
        String number = phoneNumber.strip();
        if (number.length() != 11) throw new InvalidPhoneNumber("Invalid phone number");
    }

    private void validateProduct(Product product) {
        if (product == null) throw new NoProductFoundException("No product found");
    }

    private void validateSenderIsLoggedIn(User user) {
        if (!user.isLoggedIn()) throw new NotLoggedInException("You must login to send order");
    }

}
