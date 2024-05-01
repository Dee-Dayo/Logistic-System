package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.Product;
import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.data.repositories.UserRepository;
import africa.semicolon.LogisticSystem.dto.requests.CheckStatusRequest;
import africa.semicolon.LogisticSystem.dto.requests.OrderPaymentRequest;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserLoginRequest;
import africa.semicolon.LogisticSystem.dto.response.*;
import africa.semicolon.LogisticSystem.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static africa.semicolon.LogisticSystem.utils.Mapper.*;

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
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        User user = findUserByNumber(userLoginRequest.getPhoneNumber());
        validatePassword(userLoginRequest);
        if (!user.isLoggedIn()) {
            user.setLoggedIn(true);
        } else throw new AlreadyLoggedInException("UserAlready logged in");
        userRepository.save(user);
        return loginResponseMap(user);
    }

    private void validatePassword(UserLoginRequest userLoginRequest) {
        User user = findUserByNumber(userLoginRequest.getPhoneNumber());
        if(!user.getPassword().equals(userLoginRequest.getPassword())) throw new InvalidPasswordException("Wrong username or password");
    }

    public User findUserByNumber(String username) {
        User user = userRepository.findByPhoneNumber(username);
        if (user == null) throw new UserNotFoundException("Phone number does not exist");
        return user;
    }

    @Override
    public UserSendOrderResponse sendOrder(SendOrderRequest sendOrderRequest) {
        User sender = userRepository.findByPhoneNumber(sendOrderRequest.getSenderPhone());
        validateSenderIsLoggedIn(sender);
        Product product = sendOrderRequest.getProduct();
        validateProduct(product);;

        validateReceiver(sendOrderRequest.getReceiverPhone());
        validateReceiverAddress(sendOrderRequest.getReceiverAddress());
        validateReceiverName(sendOrderRequest.getReceiverName());

        Order order = adminServices.takeOrder(sender, sendOrderRequest);

        return sendOrderResponseMap(order);
    }

    private void validateReceiverName(String firstName) {
        if (firstName == null) throw new InvalidName("Supply the receiver name");
    }

    private void validateReceiverAddress(String address) {
        if (address == null) throw new InvalidAddress("Invalid address");
    }

    @Override
    public Object trackOrderById(CheckStatusRequest orderId) {
        Order order = trackOrderBy(orderId.getOrderId());
        if (!order.isPaid()) return checkStatusWithoutPayment(order);
        else if (!order.isDelivered()) return checkStatusWithoutDelivered(order);
        return checkStatusResponseMap(order);
    }

    private Order trackOrderBy(String id){
        return orderService.getOrderById(id);
    }

    @Override
    public OrderPaymentResponse makePayment(OrderPaymentRequest orderPaymentRequest) {
        adminServices.collectOrderFromSender(orderPaymentRequest);

        Order order = trackOrderBy(orderPaymentRequest.getOrderId());

        return orderConfirmationResponseMap(order);
    }

    private void validateReceiver(String phoneNumber) {
        String number = phoneNumber.strip();
        if (number.length() != 11) throw new InvalidPhoneNumber("Invalid phone number");
    }

    private void validateProduct(Product product) {
        if (product == null) throw new NoProductFoundException("No product found");
    }

    private void validateSenderIsLoggedIn(User user) {
        if (user == null) throw new UserNotFoundException("user not found");
        if (!user.isLoggedIn()) throw new NotLoggedInException("You must login to send order");
    }

}
