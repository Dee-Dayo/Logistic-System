package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.*;
import africa.semicolon.LogisticSystem.data.repositories.OrderRepository;
import africa.semicolon.LogisticSystem.data.repositories.UserRepository;
import africa.semicolon.LogisticSystem.dto.requests.UserRegisterRequest;
import africa.semicolon.LogisticSystem.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static africa.semicolon.LogisticSystem.utils.Mapper.requestMap;

@Service
public class AdminServicesImpl implements AdminServices{
    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    private final Admin admin = new Admin();

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
        return admin.getRiders().length;
    }

    @Override
    public void takeOrder(Order order) {
        orderRepository.save(order);
        Rider rider = findAvailableRider();
        assignOrder(rider, order);
    }

    private void assignOrder(Rider rider, Order order) {
        rider.setAvailable(false);
        order.setIsAssignedTo(rider);

        User sender = order.getSender();
        Product product = order.getProduct();
        User receiver = order.getReceiver();

        sender.setProduct(null);
        sender.setSent(true);
        receiver.setProduct(product);
        receiver.setReceived(true);

        order.setDelivered(true);
        orderRepository.delete(order);
        rider.setAvailable(true);

        userRepository.save(sender);
        userRepository.save(receiver);
    }

    private Rider findAvailableRider() {
        Rider[] riders = admin.getRiders();
        for (Rider rider : riders) {
            if(rider.isAvailable()) return rider;
        }
        throw new RiderNotAvailableException("No rider available at the moment");
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
