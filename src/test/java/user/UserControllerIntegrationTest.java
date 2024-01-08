package user;

import com.test.task.expandapis_testtask.DTO.AddUserDTO;
import com.test.task.expandapis_testtask.DTO.UserAuthenticationDTO;
import com.test.task.expandapis_testtask.ExpandapisTesttaskApplication;
import com.test.task.expandapis_testtask.Response.LoginResponse;
import com.test.task.expandapis_testtask.Response.ResponseUser;
import com.test.task.expandapis_testtask.services.AuthenticationService;
import com.test.task.expandapis_testtask.services.JwtService;
import com.test.task.expandapis_testtask.web.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.test.task.expandapis_testtask.Entitys.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ExpandapisTesttaskApplication.class)
public class UserControllerIntegrationTest {

    @Test
    public void testRegisterUserSuccessfully() throws Exception {
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        User registeredUser = new User("testuser", "password");
        when(authenticationService.signup(any(AddUserDTO.class))).thenReturn(registeredUser);

        JwtService jwtService = mock(JwtService.class);

        UserController controller = new UserController(jwtService, authenticationService);

        AddUserDTO addUserDTO = new AddUserDTO();
        addUserDTO.setUsername("testuser");
        addUserDTO.setPassword("password");
        ResponseEntity<ResponseUser> response = controller.register(addUserDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ResponseUser responseUser = response.getBody();
        assertEquals("testuser", responseUser.getUsername());
        assertEquals("password", responseUser.getPassword());
    }

    @Test
    public void testAuthenticateUserSuccessfully() throws Exception {
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        User authenticatedUser = new User("testuser", "password");
        when(authenticationService.authenticate(any(UserAuthenticationDTO.class))).thenReturn(authenticatedUser);

        JwtService jwtService = mock(JwtService.class);
        when(jwtService.generateToken(any(User.class))).thenReturn("jwt_token");

        UserController controller = new UserController(jwtService, authenticationService);

        UserAuthenticationDTO userAuthenticationDTO = new UserAuthenticationDTO();
        userAuthenticationDTO.setUsername("testuser");
        userAuthenticationDTO.setPassword("password");

        ResponseEntity<LoginResponse> response = controller.authenticate(userAuthenticationDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        LoginResponse loginResponse = response.getBody();
        assertEquals("jwt_token", loginResponse.getToken());
    }
}
