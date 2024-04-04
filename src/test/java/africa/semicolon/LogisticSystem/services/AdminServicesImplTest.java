package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.data.repositories.OrderRepository;
import africa.semicolon.LogisticSystem.data.repositories.UserRepository;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserLoginRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserRegisterRequest;
import africa.semicolon.LogisticSystem.exceptions.OrderPaymentNotMade;
import org.junit.jupiter.api.AfterEach;
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
    public OrderService orderService;

    private UserRegisterRequest userRegisterRequest;
    private UserRegisterRequest userRegisterRequest2;
    private UserLoginRequest userLoginRequest;

    @AfterEach
    public void tearDown(){

    }

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        orderRepository.deleteAll();

        userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstName("Dayo");
        userRegisterRequest.setLastName("Akinyemi");
        userRegisterRequest.setPhoneNumber("44444444444");
        userRegisterRequest.setPassword("password");
        userRegisterRequest.setAddress("yaba");

//        userRegisterRequest2 = new UserRegisterRequest();
//        userRegisterRequest2.setFirstName("Moh");
//        userRegisterRequest2.setLastName("Baba");
//        userRegisterRequest2.setPhoneNumber("55555555555");
//        userRegisterRequest2.setPassword("password");
//        userRegisterRequest2.setAddress("mushin");

        userLoginRequest = new UserLoginRequest();
        userLoginRequest.setPhoneNumber("44444444444");
        userLoginRequest.setPassword("password");
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
    public void adminHasTwoRidersByDefaultAvailable(){
        assertEquals(2, adminServices.findNoOfRiders());
        assertEquals(2, adminServices.findAvailableRiders());
    }

    @Test
    public void userCanRegister_userCanLogin(){
        adminServices.register(userRegisterRequest);
        assertFalse(userServices.isUserLoggedIn("44444444444"));

        userServices.login(userLoginRequest);
        assertTrue(userServices.isUserLoggedIn("44444444444"));
    }

    @Test
    public void userSendOrders_orderRepositoryIsOne(){
        adminServices.register(userRegisterRequest);
         assertEquals(0, adminServices.noOfOrders());

        userServices.login(userLoginRequest);
        User sender = userServices.findUserByNumber("44444444444");

        User receiver = new User();
        receiver.setAddress("mushin");
        receiver.setPhoneNumber("55555555555");

        SendOrderRequest sendOrderRequest = new SendOrderRequest();
        sendOrderRequest.setSender(sender);
        sendOrderRequest.setProduct(TV);
        sendOrderRequest.setReceiver(receiver);
//        sendOrderRequest.setPaid(true);
        userServices.sendOrder(sendOrderRequest);
        assertEquals(1, adminServices.noOfOrders());
    }

//    @Test
//    public void userSendOrdersButDoesntPay_orderRepositoryIsZero(){
//        adminServices.register(userRegisterRequest);
////        adminServices.register(userRegisterRequest2);
//        assertEquals(0, adminServices.noOfOrders());
//
//        userServices.login(userLoginRequest);
//        User sender = userServices.findUserByNumber("44444444444");
//
//        User receiver = new User();
//        receiver.setAddress("mushin");
//        receiver.setPhoneNumber("55555555555");
//
//        SendOrderRequest sendOrderRequest = new SendOrderRequest();
//        sendOrderRequest.setSender(sender);
//        sendOrderRequest.setProduct(TV);
//        sendOrderRequest.setReceiver(receiver);
//        assertThrows(OrderPaymentNotMade.class, ()->userServices.sendOrder(sendOrderRequest));
//        assertEquals(0, adminServices.noOfOrders());
//    }

    @Test
    public void userSendOrder_adminAssignRiderToOrderToDeliver(){
        adminServices.register(userRegisterRequest);
//        adminServices.register(userRegisterRequest2);
        assertEquals(0, adminServices.noOfOrders());
        assertEquals(2, adminServices.findAvailableRiders());

        userServices.login(userLoginRequest);
        User sender = userServices.findUserByNumber("44444444444");
//        User receiver = userServices.findUserByNumber("55555555555");

        User receiver = new User();
        receiver.setAddress("mushin");
        receiver.setPhoneNumber("55555555555");

        SendOrderRequest sendOrderRequest = new SendOrderRequest();
        sendOrderRequest.setSender(sender);
        sendOrderRequest.setProduct(TV);
        sendOrderRequest.setReceiver(receiver);
//        sendOrderRequest.setPaid(true);

        userServices.sendOrder(sendOrderRequest);
        assertEquals(1, adminServices.noOfOrders());

        Order order = orderService.getOrderByUser(sender);

        assertEquals("Moh", order.getIsAssignedTo().getFirstName());
    }
}