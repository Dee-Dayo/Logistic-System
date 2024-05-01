package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.*;
import africa.semicolon.LogisticSystem.data.repositories.OrderRepository;
import africa.semicolon.LogisticSystem.data.repositories.RiderRepository;
import africa.semicolon.LogisticSystem.data.repositories.UserRepository;
import africa.semicolon.LogisticSystem.dto.requests.OrderPaymentRequest;
import africa.semicolon.LogisticSystem.dto.requests.RiderRegisterRequest;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserRegisterRequest;
import africa.semicolon.LogisticSystem.dto.response.RiderRegisterResponse;
import africa.semicolon.LogisticSystem.dto.response.UserRegisterResponse;
import africa.semicolon.LogisticSystem.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static africa.semicolon.LogisticSystem.utils.Mapper.requestMap;
import static africa.semicolon.LogisticSystem.utils.Mapper.responseMap;

@Service
public class AdminServicesImpl implements AdminServices {
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


    @Override
    public Long findNoOfUsers() {
        return userRepository.count();
    }

    @Override
    public Long findNoOfRiders() {
        return riderRepository.count();
    }

    @Override
    public RiderRegisterResponse register(RiderRegisterRequest riderRegisterRequest) {
        validateLength(riderRegisterRequest.getPhoneNumber());
        validateRiderPhoneNumber(riderRegisterRequest.getPhoneNumber());

        Rider newRider = requestMap(riderRegisterRequest);
        riderRepository.save(newRider);

        return responseMap(newRider);
    }

    @Override
    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        validateLength(userRegisterRequest.getPhoneNumber());
        validatePhoneNumber(userRegisterRequest.getPhoneNumber());
        validateAddress(userRegisterRequest.getAddress());

        User newUser = requestMap(userRegisterRequest);
        userRepository.save(newUser);

        return responseMap(newUser);
    }

    private void validateAddress(String address) {
        if (address == null) throw new InvalidAddress("Invalid address");
    }


    @Override
    public Order takeOrder(User user, SendOrderRequest sendOrderRequest) {
        riderService.findRider();

        Order order = orderService.setOrder(user, sendOrderRequest);
//        orderRepository.save(order);


        return order;
    }

    @Override
    public Long noOfOrders() {
        return orderRepository.count();
    }

    @Override
    public int findAvailableRiders() {
        List<Rider> riders = riderRepository.findAll();
        List<Rider> available = new ArrayList<>();
        for (Rider rider : riders) {
            if (rider.isAvailable()) available.add(rider);
        }
        return available.size();
    }

    @Override
    public void collectOrderFromSender(OrderPaymentRequest orderPaymentRequest) {
        orderService.setPaymentStatus(orderPaymentRequest);
        Order order = orderService.getOrderById(orderPaymentRequest.getOrderId());

        Order pickedUpOrder = riderService.pickupItemFromCustomer(order);
        pickedUpOrder.setDateDeliveredToHQ(LocalDateTime.now());
//        pickedUpOrder.setIsAssignedTo(null);

        orderRepository.save(pickedUpOrder);






//        Order deliveredOrder = riderService.deliverItemToReceiver(pickedUpOrder);
//        orderRepository.save(deliveredOrder);
    }

    public void sendAllOrdersInSelectedArea(String address) {
        List<Order> allOrders = getAllOrders();

        List<Order> selectedOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if (order.getReceiverAddress() == address && order.isPaid()) selectedOrders.add(order);
        }

        if (selectedOrders.isEmpty()) throw new NoOrderException("No order found");

        Rider rider = riderService.findRider();

        for (Order order : selectedOrders){
            order.setDatePickedUpFromHQ(LocalDateTime.now());
            order.setIsAssignedTo(rider);
            orderRepository.save(order);
        }
        rider.setOrders(selectedOrders);

        riderService.deliverItems(rider);
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) throw new NoOrderException("No orders found");
        return orders;
    }


    private void validateLength(String phoneNumber) {
        String number = phoneNumber.strip();
        if (number.length() != 11) throw new InvalidPhoneNumber("Invalid phone number");
    }

    private void validatePhoneNumber(String phoneNumber) {
        boolean userExists = userRepository.existsByPhoneNumber(phoneNumber);
        if (userExists) throw new UserAlreadyExistException("Phone number already exist");
    }

    private void validateRiderPhoneNumber(String phoneNumber) {
        boolean riderExists = riderRepository.existsByPhoneNumber(phoneNumber);
        if (riderExists) throw new RiderAlreadyExist("Phone number already exist");
    }

}