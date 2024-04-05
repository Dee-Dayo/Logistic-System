//package africa.semicolon.LogisticSystem.services;
//
//import africa.semicolon.LogisticSystem.data.models.User;
//import africa.semicolon.LogisticSystem.data.repositories.UserRepository;
//import africa.semicolon.LogisticSystem.dto.requests.SendOrderRequest;
//import africa.semicolon.LogisticSystem.dto.requests.UserLoginRequest;
//import africa.semicolon.LogisticSystem.dto.requests.UserRegisterRequest;
//import africa.semicolon.LogisticSystem.exceptions.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static africa.semicolon.LogisticSystem.data.models.Product.TV;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class UserServicesImplTest {
//
//    @Autowired
//    UserServices userServices;
//
//    @Autowired
//    AdminServices adminServices;
//
//    @Autowired
//    UserRepository userRepository;
//
//    private UserRegisterRequest userRegisterRequest;
//    private UserRegisterRequest userRegisterRequest2;
//    private UserLoginRequest userLoginRequest;
//
//
//    @BeforeEach
//    public void setUp() {
//        userRepository.deleteAll();
//
//        userRegisterRequest = new UserRegisterRequest();
//        userRegisterRequest.setFirstName("Dayo");
//        userRegisterRequest.setLastName("Akinyemi");
//        userRegisterRequest.setPhoneNumber("44444444444");
//        userRegisterRequest.setPassword("password");
//        userRegisterRequest.setAddress("yaba");
//
//        userRegisterRequest2 = new UserRegisterRequest();
//        userRegisterRequest2.setFirstName("Moh");
//        userRegisterRequest2.setLastName("Baba");
//        userRegisterRequest2.setPhoneNumber("55555555555");
//        userRegisterRequest2.setPassword("password");
//        userRegisterRequest2.setAddress("mushin");
//
//        userLoginRequest = new UserLoginRequest();
//        userLoginRequest.setPhoneNumber("44444444444");
//        userLoginRequest.setPassword("password");
//    }
//
//    @Test
//    public void userCanRegister_userCanLogin(){
//        adminServices.register(userRegisterRequest);
//        assertFalse(userServices.isUserLoggedIn("44444444444"));
//
//        userServices.login(userLoginRequest);
//        assertTrue(userServices.isUserLoggedIn("44444444444"));
//    }
//
//    @Test
//    public void userRegister_userCantLoginWithWrongPassword(){
//        adminServices.register(userRegisterRequest);
//        assertFalse(userServices.isUserLoggedIn("44444444444"));
//
//        UserLoginRequest userLoginRequest2 = new UserLoginRequest();
//        userLoginRequest2.setPhoneNumber("44444444444");
//        userLoginRequest2.setPassword("wrongPassword");
//        assertThrows(InvalidPasswordException.class, ()->userServices.login(userLoginRequest2));
//    }
//
//    @Test
//    public void userRegister_userCantLoginWithWrongPhoneNumber(){
//        adminServices.register(userRegisterRequest);
//        assertFalse(userServices.isUserLoggedIn("44444444444"));
//
//        UserLoginRequest userLoginRequest2 = new UserLoginRequest();
//        userLoginRequest2.setPhoneNumber("44444444441");
//        userLoginRequest2.setPassword("password");
//        assertThrows(UserNotFoundException.class, ()->userServices.login(userLoginRequest2));
//    }
//
//    @Test
//    public void userRegister_userLogin_userCanSendOrder_anotherRegisteredUserReceivesOrder(){
//        adminServices.register(userRegisterRequest);
//        userServices.login(userLoginRequest);
//
//        User sender = userServices.findUserByNumber("44444444444");
//        assertEquals(TV, sender.getProduct());
//        assertNotNull(sender.getProduct());
//
//        adminServices.register(userRegisterRequest2);
//        User receiver = userServices.findUserByNumber("55555555555");
//        assertNull(receiver.getProduct());
//
//        SendOrderRequest sendOrderRequest = new SendOrderRequest();
//        sendOrderRequest.setSender(sender);
//        sendOrderRequest.setProduct(sender.getProduct());
//        sendOrderRequest.setReceiver(receiver);
//        userServices.sendOrder(sendOrderRequest);
//
//        sender = userServices.findUserByNumber("44444444444");
//        assertNull(sender.getProduct());
//
//        receiver = userServices.findUserByNumber("55555555555");
//        assertEquals(TV, receiver.getProduct());
//        assertNotNull(receiver.getProduct());
//        assertTrue(receiver.isReceived());
//    }
//
//    @Test
//    public void userCantSendOrderWithoutLogin(){
//        adminServices.register(userRegisterRequest);
//
//        User sender = userServices.findUserByNumber("44444444444");
//        SendOrderRequest sendOrderRequest = new SendOrderRequest();
//        sendOrderRequest.setSender(sender);
//        assertThrows(NotLoggedInException.class, ()->userServices.sendOrder(sendOrderRequest));
//    }
//
//    @Test
//    public void userCantSendOrderIfUserProductIsNull(){
//        adminServices.register(userRegisterRequest2);
//        userLoginRequest.setPhoneNumber("55555555555");
//        userServices.login(userLoginRequest);
//
//        User sender = userServices.findUserByNumber("55555555555");
//        SendOrderRequest sendOrderRequest = new SendOrderRequest();
//        sendOrderRequest.setSender(sender);
//        sendOrderRequest.setProduct(sender.getProduct());
//        assertThrows(NoProductFoundException.class, ()->userServices.sendOrder(sendOrderRequest));
//    }
//
//    @Test
//    public void userCantSendOrderIfReceiverIsNotRegistered(){
//        adminServices.register(userRegisterRequest);
//        userServices.login(userLoginRequest);
//
//        User receiver = new User();
//        receiver.setPhoneNumber("66666666666");
//        receiver.setPassword("password");
//        receiver.setAddress("mushin");
//        receiver.setFirstName("name");
//        receiver.setLastName("lastName");
//
//        User sender = userServices.findUserByNumber("44444444444");
//        SendOrderRequest sendOrderRequest = new SendOrderRequest();
//        sendOrderRequest.setSender(sender);
//        sendOrderRequest.setProduct(sender.getProduct());
//        sendOrderRequest.setReceiver(receiver);
//        assertThrows(UserNotFoundException.class, ()->userServices.sendOrder(sendOrderRequest));
//    }
//}