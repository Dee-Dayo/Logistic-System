package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.*;
import africa.semicolon.LogisticSystem.data.repositories.OrderRepository;
import africa.semicolon.LogisticSystem.data.repositories.RiderRepository;
import africa.semicolon.LogisticSystem.data.repositories.UserRepository;
import africa.semicolon.LogisticSystem.dto.requests.OrderPaymentRequest;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserRegisterRequest;
import africa.semicolon.LogisticSystem.dto.response.UserRegisterResponse;
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
        rider1.setId("1");

        Rider rider2 = new Rider();
        rider2.setFirstName("Beejay");
        rider2.setLastName("Queue");
        rider2.setAvailable(true);
        rider2.setId("2");

        admin.getRiders().add(rider1);
        admin.getRiders().add(rider2);

        riderService.save(rider1);
        riderService.save(rider2);
    }

    @Override
    public Long findNoOfUsers() {
        return userRepository.count();
    }

    @Override
    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        validateLength(userRegisterRequest.getPhoneNumber());
        validatePhoneNumber(userRegisterRequest.getPhoneNumber());
        validateNumber(userRegisterRequest.getAddress());

        User newUser = requestMap(userRegisterRequest);
        userRepository.save(newUser);

        return responseMap(newUser);
    }

    private void validateNumber(String address) {
        if (address == null) throw new InvalidAddress("Invalid address");
    }

    @Override
    public int findNoOfRiders() {
        return admin.getRiders().size();
    }

    @Override
    public Order takeOrder(User user, SendOrderRequest sendOrderRequest) {
        Rider rider = riderService.findRider();
        rider.setAvailable(false);
        riderRepository.save(rider);

        Product product = sendOrderRequest.getProduct();

        Order order = new Order();
        setOrderPrice(order, product);
        order.setSender(user);
        order.setProduct(sendOrderRequest.getProduct());
        order.setReceiverName(sendOrderRequest.getReceiverName());
        order.setReceiverPhone(sendOrderRequest.getReceiverPhone());
        order.setReceiverName(sendOrderRequest.getReceiverAddress());
        order.setIsAssignedTo(rider);

        orderRepository.save(order);

        return order;
    }

    private void setOrderPrice(Order order, Product product) {
        switch (product){
            case TV -> order.setAmount(5000);
            case FOOD -> order.setAmount(1500);
            case CLOTH -> order.setAmount(2000);
            case LAPTOP -> order.setAmount(8000);
            default -> order.setAmount(10000);
        }
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

        if (orderPaymentRequest.getOrderAmount() >= order.getAmount()){
            order.setPaid(true);
            order.setDateCollected(LocalDateTime.now());
            orderRepository.save(order);
        } else throw new OrderPaymentNotMade("Insufficient amount for order");

        order.setDelivered(true);
        order.setDateDelivered(LocalDateTime.now().plusHours(2));
        orderRepository.save(order);
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
