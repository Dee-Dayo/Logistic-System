package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.data.repositories.OrderRepository;
import africa.semicolon.LogisticSystem.data.repositories.UserRepository;
import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserLoginRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserRegisterRequest;
import africa.semicolon.LogisticSystem.exceptions.OrderPaymentNotMade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static africa.semicolon.LogisticSystem.data.models.Product.TV;
import static org.junit.jupiter.api.Assertions.*;

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

    private UserRegisterRequest userRegisterRequest;
    private UserRegisterRequest userRegisterRequest2;
    private UserLoginRequest userLoginRequest;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        orderRepository.deleteAll();

        userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstName("Dayo");
        userRegisterRequest.setLastName("Akinyemi");
        userRegisterRequest.setPhoneNumber("44444444444");
        userRegisterRequest.setPassword("password");
        userRegisterRequest.setProduct(TV);
        userRegisterRequest.setAddress("yaba");

        userRegisterRequest2 = new UserRegisterRequest();
        userRegisterRequest2.setFirstName("Moh");
        userRegisterRequest2.setLastName("Baba");
        userRegisterRequest2.setPhoneNumber("55555555555");
        userRegisterRequest2.setPassword("password");
        userRegisterRequest2.setAddress("mushin");

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
        userRegisterRequest2.setProduct(TV);
        userRegisterRequest2.setAddress("yaba");

        adminServices.register(userRegisterRequest2);
        assertEquals(2, adminServices.findNoOfUsers());
    }

    @Test
    public void adminHasTwoRidersByDefaultAvailable(){
        assertEquals(2, adminServices.findNoOfRiders());
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
        adminServices.register(userRegisterRequest2);
         assertEquals(0, adminServices.noOfOrders());

        userServices.login(userLoginRequest);
        User sender = userServices.findUserByNumber("44444444444");
        User receiver = userServices.findUserByNumber("55555555555");

        SendOrderRequest sendOrderRequest = new SendOrderRequest();
        sendOrderRequest.setSender(sender);
        sendOrderRequest.setProduct(sender.getProduct());
        sendOrderRequest.setReceiver(receiver);
        sendOrderRequest.setPaid(true);
        userServices.sendOrder(sendOrderRequest);
        assertEquals(1, adminServices.noOfOrders());
    }

    @Test
    public void userSendOrdersButDoesntPay_orderRepositoryIsZero(){
        adminServices.register(userRegisterRequest);
        adminServices.register(userRegisterRequest2);
        assertEquals(0, adminServices.noOfOrders());

        userServices.login(userLoginRequest);
        User sender = userServices.findUserByNumber("44444444444");
        User receiver = userServices.findUserByNumber("55555555555");

        SendOrderRequest sendOrderRequest = new SendOrderRequest();
        sendOrderRequest.setSender(sender);
        sendOrderRequest.setProduct(sender.getProduct());
        sendOrderRequest.setReceiver(receiver);
        assertThrows(OrderPaymentNotMade.class, ()->userServices.sendOrder(sendOrderRequest));
        assertEquals(0, adminServices.noOfOrders());
    }

    @Test
    public void userSendOrder_adminAssignRiderToOrderToDeliver(){
        adminServices.register(userRegisterRequest);
        adminServices.register(userRegisterRequest2);
        assertEquals(0, adminServices.noOfOrders());
        assertEquals(2, adminServices.findAvailableRiders());

        userServices.login(userLoginRequest);
        User sender = userServices.findUserByNumber("44444444444");
        User receiver = userServices.findUserByNumber("55555555555");

        SendOrderRequest sendOrderRequest = new SendOrderRequest();
        sendOrderRequest.setSender(sender);
        sendOrderRequest.setProduct(sender.getProduct());
        sendOrderRequest.setReceiver(receiver);
        sendOrderRequest.setPaid(true);

        userServices.sendOrder(sendOrderRequest);
        assertEquals(1, adminServices.noOfOrders());
        assertEquals(1, adminServices.findAvailableRiders());
    }
}