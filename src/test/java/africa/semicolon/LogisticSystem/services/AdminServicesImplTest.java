package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.repositories.UserRepository;
import africa.semicolon.LogisticSystem.dto.requests.UserLoginRequest;
import africa.semicolon.LogisticSystem.dto.requests.UserRegisterRequest;
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

    private UserRegisterRequest userRegisterRequest;
    private UserLoginRequest userLoginRequest;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();

        userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstName("Dayo");
        userRegisterRequest.setLastName("Akinyemi");
        userRegisterRequest.setPhoneNumber("44444444444");
        userRegisterRequest.setPassword("password");
        userRegisterRequest.setProduct(TV);
        userRegisterRequest.setAddress("yaba");

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
}