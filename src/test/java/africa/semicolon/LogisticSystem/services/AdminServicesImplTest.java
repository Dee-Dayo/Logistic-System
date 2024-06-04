package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.data.repositories.OrderRepository;
import africa.semicolon.LogisticSystem.data.repositories.RiderRepository;
import africa.semicolon.LogisticSystem.data.repositories.UserRepository;
import africa.semicolon.LogisticSystem.dto.requests.*;
import africa.semicolon.LogisticSystem.exceptions.OrderPaymentNotMade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static africa.semicolon.LogisticSystem.data.models.Product.TV;
import static org.junit.jupiter.api.Assertions.*;
import africa.semicolon.LogisticSystem.data.models.Order;

@SpringBootTest
class AdminServicesImplTest {

    @Autowired
    public AdminServices adminServices;
    @Autowired
    public UserServices userServices;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    RiderRepository riderRepository;
    @Autowired
    public OrderService orderService;
    @Autowired
    public RiderService riderService;

    private UserRegisterRequest userRegisterRequest;
    private UserLoginRequest userLoginRequest;
    private User receiver;
    private RiderRegisterRequest riderRegisterRequest;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        orderRepository.deleteAll();
        riderRepository.deleteAll();


        userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstName("Dayo");
        userRegisterRequest.setLastName("Akinyemi");
        userRegisterRequest.setPhoneNumber("44444444444");
        userRegisterRequest.setPassword("password");
        userRegisterRequest.setAddress("yaba");

        riderRegisterRequest = new RiderRegisterRequest();
        riderRegisterRequest.setFirstName("Akinyemi");
        riderRegisterRequest.setLastName("Akinyemi");
        riderRegisterRequest.setPhoneNumber("00000000000");
        riderRegisterRequest.setPassword("password");


        userLoginRequest = new UserLoginRequest();
        userLoginRequest.setPhoneNumber("44444444444");
        userLoginRequest.setPassword("password");

        receiver = new User();
        receiver.setAddress("mushin");
        receiver.setPhoneNumber("55555555555");
        receiver.setFirstName("jumoke");
    }

    @Test
    public void userRegisters_noOfUsersIsOne(){
        adminServices.register(userRegisterRequest);
        assertEquals(1, adminServices.findNoOfUsers());
    }

    @Test
    public void twoUsersRegister_noOfUsersIsTwo(){
        adminServices.register(userRegisterRequest);
        UserRegisterRequest userRegisterRequest2 = new UserRegisterRequest();
        userRegisterRequest2.setFirstName("Dayo");
        userRegisterRequest2.setLastName("Akinyemi");
        userRegisterRequest2.setPhoneNumber("77777777777");
        userRegisterRequest2.setPassword("password");
        userRegisterRequest2.setAddress("yaba");

        adminServices.register(userRegisterRequest2);
        assertEquals(2, adminServices.findNoOfUsers());
    }


    @Test
    public void userCanRegister_userCanLogin(){
        adminServices.register(userRegisterRequest);
        assertFalse(userServices.isUserLoggedIn("44444444444"));

        userServices.login(userLoginRequest);
        assertTrue(userServices.isUserLoggedIn("44444444444"));
    }

    @Test
    public void userCollectOrders_orderFromSenderRepositoryIsOne(){
        adminServices.register(userRegisterRequest);
        adminServices.register(riderRegisterRequest);
         assertEquals(0, adminServices.noOfOrders());

        userServices.login(userLoginRequest);
        User sender = userServices.findUserByNumber("44444444444");

        SendOrderRequest sendOrderRequest = new SendOrderRequest();
        sendOrderRequest.setSenderPhone(sender.getPhoneNumber());
        sendOrderRequest.setReceiverPhone(receiver.getPhoneNumber());
        sendOrderRequest.setReceiverAddress(receiver.getAddress());
        sendOrderRequest.setReceiverName(receiver.getFirstName());
        sendOrderRequest.setProduct(TV);
        userServices.sendOrder(sendOrderRequest);
        assertEquals(1, adminServices.noOfOrders());
    }

    @Test
    public void userCollectOrder_adminAssignRiderToRetrieveOrderFromSender(){
        adminServices.register(userRegisterRequest);
        adminServices.register(riderRegisterRequest);
        assertEquals(0, adminServices.noOfOrders());

        userServices.login(userLoginRequest);
        User sender = userServices.findUserByNumber("44444444444");


        SendOrderRequest sendOrderRequest = new SendOrderRequest();
        sendOrderRequest.setSenderPhone(sender.getPhoneNumber());
        sendOrderRequest.setReceiverPhone(receiver.getPhoneNumber());
        sendOrderRequest.setReceiverAddress(receiver.getAddress());
        sendOrderRequest.setReceiverName(receiver.getFirstName());
        sendOrderRequest.setProduct(TV);

        userServices.sendOrder(sendOrderRequest);
        assertEquals(1, adminServices.noOfOrders());

        Order order = orderService.getOrderByUser(sender);
        System.out.println(order);

        Object answer = userServices.trackOrderById(order.getId());
        System.out.println(answer);

        assertEquals(1, riderService.findNoOfAvailableRiders());
        assertEquals(1, adminServices.findAvailableRiders());
    }

    @Test
    public void userCollectOrder_adminAssignRiderToRetrieveOrder_FromSender_SenderMakesPayment(){
        adminServices.register(userRegisterRequest);
        adminServices.register(riderRegisterRequest);
        assertEquals(0, adminServices.noOfOrders());

        userServices.login(userLoginRequest);
        User sender = userServices.findUserByNumber("44444444444");


        SendOrderRequest sendOrderRequest = new SendOrderRequest();
        sendOrderRequest.setSenderPhone(sender.getPhoneNumber());
        sendOrderRequest.setReceiverPhone(receiver.getPhoneNumber());
        sendOrderRequest.setReceiverAddress(receiver.getAddress());
        sendOrderRequest.setReceiverName(receiver.getFirstName());
        sendOrderRequest.setProduct(TV);

        userServices.sendOrder(sendOrderRequest);

        Order order = orderService.getOrderByUser(sender);

        CheckStatusRequest checkStatusRequest = new CheckStatusRequest();
        checkStatusRequest.setOrderId(order.getId());


        assertEquals("Moh", order.getIsAssignedTo().getFirstName());
        assertTrue(order.isPending());
        assertFalse(order.isPaid());

        OrderPaymentRequest orderPaymentRequest = new OrderPaymentRequest();
        orderPaymentRequest.setOrderId(order.getId());
        orderPaymentRequest.setOrderAmount(9000);

        userServices.makePayment(orderPaymentRequest);

        order = orderService.getOrderByUser(sender);
        assertTrue(order.isPaid());
    }

     @Test
    public void userCollectOrder_adminAssignRiderToRetrieveOrder_FromSender_SenderDoesntMakeFullPayment_ThrowException(){
        adminServices.register(userRegisterRequest);
        adminServices.register(riderRegisterRequest);
        assertEquals(0, adminServices.noOfOrders());

        userServices.login(userLoginRequest);
        User sender = userServices.findUserByNumber("44444444444");

        SendOrderRequest sendOrderRequest = new SendOrderRequest();
        sendOrderRequest.setSenderPhone(sender.getPhoneNumber());
        sendOrderRequest.setReceiverPhone(receiver.getPhoneNumber());
        sendOrderRequest.setReceiverAddress(receiver.getAddress());
        sendOrderRequest.setReceiverName(receiver.getFirstName());
        sendOrderRequest.setProduct(TV);
        userServices.sendOrder(sendOrderRequest);

        Order order = orderService.getOrderByUser(sender);

        assertTrue(order.isPending());
        assertFalse(order.isPaid());

        OrderPaymentRequest orderPaymentRequest = new OrderPaymentRequest();
        orderPaymentRequest.setOrderId(order.getId());
        orderPaymentRequest.setOrderAmount(1000);

        assertThrows(OrderPaymentNotMade.class, ()->userServices.makePayment(orderPaymentRequest));

        order = orderService.getOrderByUser(sender);
        assertEquals("Moh", order.getIsAssignedTo().getFirstName());
        assertFalse(order.isPaid());
    }

    @Test
    public void userCollectOrder_adminAssignRiderToRetrieveOrder_senderMakesPayment_orderFromSenderDelivered(){
        adminServices.register(userRegisterRequest);
        adminServices.register(riderRegisterRequest);
        assertEquals(0, adminServices.noOfOrders());

        userServices.login(userLoginRequest);
        User sender = userServices.findUserByNumber("44444444444");

        SendOrderRequest sendOrderRequest = new SendOrderRequest();
        sendOrderRequest.setSenderPhone(sender.getPhoneNumber());
        sendOrderRequest.setReceiverPhone(receiver.getPhoneNumber());
        sendOrderRequest.setReceiverAddress(receiver.getAddress());
        sendOrderRequest.setReceiverName(receiver.getFirstName());
        sendOrderRequest.setProduct(TV);
        userServices.sendOrder(sendOrderRequest);

        Order order = orderService.getOrderByUser(sender);

        assertTrue(order.isPending());
        assertFalse(order.isPaid());

        OrderPaymentRequest orderPaymentRequest = new OrderPaymentRequest();
        orderPaymentRequest.setOrderId(order.getId());
        orderPaymentRequest.setOrderAmount(9000);

        userServices.makePayment(orderPaymentRequest);

        order = orderService.getOrderByUser(sender);
        assertEquals("Moh", order.getIsAssignedTo().getFirstName());
        assertTrue(order.isPaid());
        assertTrue(order.isDelivered());
    }


}