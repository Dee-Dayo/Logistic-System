package africa.semicolon.LogisticSystem.utils;


import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.Rider;
import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.dto.requests.RiderRegisterRequest;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserRegisterRequest;
import africa.semicolon.LogisticSystem.dto.response.*;

import java.time.format.DateTimeFormatter;

public class Mapper {

    public static Rider requestMap(RiderRegisterRequest riderRegisterRequest) {
        Rider rider = new Rider();
        rider.setFirstName(riderRegisterRequest.getFirstName());
        rider.setLastName(riderRegisterRequest.getLastName());
        rider.setPhoneNumber(riderRegisterRequest.getPhoneNumber());
        rider.setPassword(riderRegisterRequest.getPassword());
        rider.setAvailable(true);
        return rider;
    }

    public static User requestMap(UserRegisterRequest userRegisterRequest) {
        User user = new User();
        user.setPhoneNumber(userRegisterRequest.getPhoneNumber());
        user.setPassword(userRegisterRequest.getPassword());
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setAddress(userRegisterRequest.getAddress());
        return user;
    }

    public static Order requestMap(User user, SendOrderRequest sendOrderRequest) {
        Order order = new Order();
        order.setSender(user);
        order.setProduct(sendOrderRequest.getProduct());
        order.setReceiverName(sendOrderRequest.getReceiverName());
        order.setReceiverPhone(sendOrderRequest.getReceiverPhone());
        order.setReceiverAddress(sendOrderRequest.getReceiverAddress());
        order.setPending(true);
        return order;
    }

    public static UserRegisterResponse responseMap(User user) {
        UserRegisterResponse response = new UserRegisterResponse();
        response.setId(user.getId());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        return response;
    }

    public static RiderRegisterResponse responseMap(Rider rider) {
        RiderRegisterResponse response = new RiderRegisterResponse();
        response.setId(rider.getId());
        response.setPhoneNumber(rider.getPhoneNumber());
        response.setFirstName(rider.getFirstName());
        response.setLastName(rider.getLastName());
        return response;
    }

    public static UserLoginResponse loginResponseMap(User user) {
        UserLoginResponse response = new UserLoginResponse();
        response.setPhoneNumber(user.getPhoneNumber());
        response.setId(user.getId());
        return response;
    }

    public static RiderLoginResponse loginResponseMap(Rider rider) {
        RiderLoginResponse response = new RiderLoginResponse();
        response.setFirstName(rider.getFirstName());
        response.setId(rider.getId());
        return response;
    }

    public static UserSendOrderResponse sendOrderResponseMap(Order order) {
        UserSendOrderResponse response = new UserSendOrderResponse();
        response.setOrderId(order.getId());
        response.setDateCreated(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(order.getDateCreated()));
        response.setAmount(order.getAmount());
//         response.setRiderName(order.getIsAssignedTo().getFirstName());
        return response;
    }

    public static OrderPaymentResponse orderConfirmationResponseMap(Order order) {
        OrderPaymentResponse orderPaymentResponse = new OrderPaymentResponse();
        orderPaymentResponse.setOrderId(order.getId());
        orderPaymentResponse.setDateCreated(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(order.getDateCreated()));
//        orderPaymentResponse.setRiderName(order.getIsAssignedTo().getFirstName());
        orderPaymentResponse.setPaymentStatus(order.isPaid());
        orderPaymentResponse.setDateCollected(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(order.getDatePaymentMade()));
        return orderPaymentResponse;
    }

    public static UnpaidOrderStatusResponse checkStatusWithoutPayment(Order order) {
        UnpaidOrderStatusResponse unpaidOrderStatusResponse = new UnpaidOrderStatusResponse();
        unpaidOrderStatusResponse.setOrderId(order.getId());
        unpaidOrderStatusResponse.setOrderAmount(order.getAmount());
        unpaidOrderStatusResponse.setDateCreated(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(order.getDateCreated()));
        return unpaidOrderStatusResponse;
    }

    public static UndeliveredOrderStatusResponse checkStatusWithoutDelivered(Order order) {
        UndeliveredOrderStatusResponse undeliveredOrderStatusResponse = new UndeliveredOrderStatusResponse();
        undeliveredOrderStatusResponse.setOrderId(order.getId());
        undeliveredOrderStatusResponse.setOrderAmount(order.getAmount());
        undeliveredOrderStatusResponse.setDateCreated(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(order.getDateCreated()));
        undeliveredOrderStatusResponse.setDatePickedUp(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(order.getDatePickedUp()));
        undeliveredOrderStatusResponse.setDateDroppedAtHQTR(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(order.getDateDeliveredToHQ()));
        return undeliveredOrderStatusResponse;
    }

    public static OrderStatusResponse checkStatusResponseMap(Order order) {
        OrderStatusResponse orderStatusResponse = new OrderStatusResponse();
        orderStatusResponse.setOrderId(order.getId());
        orderStatusResponse.setDateCreated(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(order.getDateCreated()));
        orderStatusResponse.setDatePayed(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(order.getDatePaymentMade()));
        orderStatusResponse.setDateCollectedFromSender(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(order.getDatePickedUp()));
        orderStatusResponse.setDateDeliveredToHQTR(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(order.getDateDeliveredToHQ()));
        orderStatusResponse.setDatePickedFromHQTR(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(order.getDatePickedUpFromHQ()));
        orderStatusResponse.setDateDeliveredToReceiver(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(order.getDateDeliveredToReceiver()));
        return orderStatusResponse;
    }

    public static OrderResponse orderMap(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(order.getId());
        orderResponse.setProduct(order.getProduct());
        orderResponse.setSender(order.getSender().getFirstName());
        orderResponse.setReceiver(order.getReceiverName());
        orderResponse.setReceiverAddress(order.getReceiverAddress());
        orderResponse.setAmount(order.getAmount());
        orderResponse.setPaid(order.isPaid());
        orderResponse.setDelivered(order.isDelivered());
        return orderResponse;
    }
}