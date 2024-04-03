package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Product;
import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.data.repositories.UserRepository;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserLoginRequest;
import africa.semicolon.LogisticSystem.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImpl implements UserServices{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminServices adminServices;


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
        Product product = sender.getProduct();
        validateProduct(product);
        sender.setProduct(null);
        sender.setSent(true);
        userRepository.save(sender);

        User receiver = userRepository.findByPhoneNumber(sendOrderRequest.getReceiver().getPhoneNumber());
        validateReceiver(receiver.getPhoneNumber());
        receiver.setProduct(product);
        receiver.setReceived(true);
        userRepository.save(receiver);
    }

    private void validateReceiver(String username) {
        User user = findUserByNumber(username);
        if (user == null) throw new ReceiverNotFoundException("Receiver not found");
    }

    private void validateProduct(Product product) {
        if (product == null) throw new NoProductFoundException("No product found");
    }

    private void validateSenderIsLoggedIn(User user) {
        if (!user.isLoggedIn()) throw new NotLoggedInException("You must login to send order");
    }

}
