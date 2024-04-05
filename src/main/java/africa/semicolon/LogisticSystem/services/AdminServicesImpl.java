package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.*;
import africa.semicolon.LogisticSystem.data.repositories.OrderRepository;
import africa.semicolon.LogisticSystem.data.repositories.RiderRepository;
import africa.semicolon.LogisticSystem.data.repositories.UserRepository;
import africa.semicolon.LogisticSystem.dto.requests.requests.OrderPaymentRequest;
import africa.semicolon.LogisticSystem.dto.requests.requests.UserRegisterRequest;
import africa.semicolon.LogisticSystem.dto.requests.response.UserRegisterResponse;
import africa.semicolon.LogisticSystem.exceptions.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static africa.semicolon.LogisticSystem.utils.Mapper.requestMap;
import static africa.semicolon.LogisticSystem.utils.Mapper.responseMap;

@Service
public class AdminServicesImpl implements AdminServices{
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    RiderRepository riderRepository;

    @Autowired
    RiderService riderService;
    @Autowired
    OrderService orderService;

    private final Admin admin = new Admin();

    @PostConstruct
    public void setUpAdmin(){
        setAdminDefaultRiders(admin);
    }

    private void setAdminDefaultRiders(Admin admin) {
        Rider rider1 = new Rider();
        rider1.setFirstName("Moh");
        rider1.setLastName("Baba");
        rider1.setAvailable(true);

        Rider rider2 = new Rider();
        rider2.setFirstName("Beejay");
        rider2.setLastName("Queue");
        rider2.setAvailable(true);

        admin.getRiders().add(rider1);
        admin.getRiders().add(rider2);

//        riderService.save(rider1);
//        riderService.save(rider2);

        riderRepository.save(rider1);
        riderRepository.save(rider2);
    }

    @Override
    public Long findNoOfUsers() {
        return userRepository.count();
    }

    @Override
    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        validateLength(userRegisterRequest.getPhoneNumber());
        validatePhoneNumber(userRegisterRequest.getPhoneNumber());

        User newUser = requestMap(userRegisterRequest);
        userRepository.save(newUser);

        return responseMap(newUser);
    }

    @Override
    public int findNoOfRiders() {
        return admin.getRiders().size();
    }

    @Override
    public void takeOrder(Order order) {
        orderRepository.save(order);

        Rider rider = riderService.findRider();
        rider.setAvailable(false);
        riderRepository.save(rider);

        riderService.assignOrder(rider, order);
        orderRepository.save(order);


    }

    @Override
    public Long noOfOrders() {
        return orderRepository.count();
    }

    @Override
    public int findAvailableRiders() {
        List<Rider> riders = riderRepository.findAll();
        List<Rider> availale = new ArrayList<>();
        for (Rider rider : riders){
            if (rider.isAvailable()) availale.add(rider);
        }
        return availale.size();
    }

    @Override
    public void sendOrder(OrderPaymentRequest orderPaymentRequest) {
        Order order = orderService.getOrderById(orderPaymentRequest.getOrderId());

        if (orderPaymentRequest.isPaid()){
            order.setPaid(true);
            order.setDateCollected(LocalDateTime.now());
            orderRepository.save(order);
        } else throw new OrderPaymentNotMade("Payment not made");

        riderService.sendOrder(order);
        orderRepository.save(order);
        userRepository.save(order.getSender());
        userRepository.save(order.getReceiver());
        riderRepository.save(order.getIsAssignedTo());
    }


    private void assignOrder(Rider rider, Order order) {
        rider.setAvailable(false);
        order.setIsAssignedTo(rider);

//        User sender = order.getSender();
//        Product product = order.getProduct();
//        User receiver = order.getReceiver();
//
//        sender.setProduct(null);
//        sender.setSent(true);
//        receiver.setProduct(product);
//        receiver.setReceived(true);
//
//        order.setDelivered(true);
//        orderRepository.delete(order);
//        rider.setAvailable(true);
//
//        userRepository.save(sender);
//        userRepository.save(receiver);
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
