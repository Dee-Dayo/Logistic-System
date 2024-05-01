package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.Rider;
import africa.semicolon.LogisticSystem.data.repositories.OrderRepository;
import africa.semicolon.LogisticSystem.data.repositories.RiderRepository;
import africa.semicolon.LogisticSystem.dto.requests.RiderLoginRequest;
import africa.semicolon.LogisticSystem.dto.response.RiderLoginResponse;
import africa.semicolon.LogisticSystem.exceptions.InvalidPasswordException;
import africa.semicolon.LogisticSystem.exceptions.RiderAlreadyExist;
import africa.semicolon.LogisticSystem.exceptions.RiderNotAvailableException;
import africa.semicolon.LogisticSystem.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static africa.semicolon.LogisticSystem.utils.Mapper.loginResponseMap;

@Service
public class RiderServiceImpl implements RiderService{

    @Autowired
    RiderRepository riderRepository;
    @Autowired
    OrderRepository orderRepository;

    @Override
    public RiderLoginResponse login(RiderLoginRequest riderLoginRequest) {
        Rider rider = findRiderByNumber(riderLoginRequest.getPhoneNumber());
        validatePassword(riderLoginRequest);
        rider.setLoggedIn(true);
        riderRepository.save(rider);
        return loginResponseMap(rider);
    }

    @Override
    public Order pickupItemFromCustomer(Order order) {
//        Rider rider = findRider();

//        order.setIsAssignedTo(rider);
        order.setDatePickedUp(LocalDateTime.now());
//        rider.getOrders().add(order);
//        riderRepository.save(rider);

        return order;
    }

//    @Override
//    public Order deliverItemToReceiver(Order pickedUpOrder) {
//        Rider rider = pickedUpOrder.getIsAssignedTo();
//        rider.setAvailable(false);
//        riderRepository.save(rider);
//
//        pickedUpOrder.setDelivered(true);
//        pickedUpOrder.setDateDeliveredToReceiver(LocalDateTime.now());
//
//        rider.setAvailable(true);
//        riderRepository.save(rider);
//        return pickedUpOrder;
//    }

    @Override
    public void deliverItems(Rider rider) {
        List<Order> orders = rider.getOrders();
        rider.setAvailable(false);
        riderRepository.save(rider);

        for (Order order : orders) {
            order.setDelivered(true);
            order.setDateDeliveredToReceiver(LocalDateTime.now());
            orderRepository.save(order);
        }

        rider.setAvailable(true);
        riderRepository.save(rider);
    }

    @Override
    public Rider findRider() {
        List<Rider> riders = riderRepository.findByIsAvailableTrue();
        if(!riders.isEmpty()){
            return riders.get(0);
        }
        throw new RiderNotAvailableException("No rider available at the moment");
    }


    @Override
    public int findNoOfAvailableRiders() {
        List<Rider> riders = riderRepository.findByIsAvailableTrue();
        return riders.size();
    }


    public Rider findRiderByNumber(String phoneNumber) {
        Rider rider = riderRepository.findByPhoneNumber(phoneNumber);
        if (rider == null) throw new UserNotFoundException("Phone number does not exist");
        return rider;
    }

    private void validatePassword(RiderLoginRequest riderLoginRequest) {
       Rider rider = findRiderByNumber(riderLoginRequest.getPhoneNumber());
        if(!rider.getPassword().equals(riderLoginRequest.getPassword())) throw new InvalidPasswordException("Wrong username or password");
    }
}
