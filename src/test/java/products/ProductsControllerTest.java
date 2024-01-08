package products;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.task.expandapis_testtask.DTO.AddUserDTO;
import com.test.task.expandapis_testtask.DTO.ProductsDTO;
import com.test.task.expandapis_testtask.DTO.RecordsDTO;
import com.test.task.expandapis_testtask.DTO.UserAuthenticationDTO;
import com.test.task.expandapis_testtask.ExpandapisTesttaskApplication;
import com.test.task.expandapis_testtask.Response.LoginResponse;
import com.test.task.expandapis_testtask.services.ProductsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ExpandapisTesttaskApplication.class)
@AutoConfigureMockMvc
class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductsService productsService;

    private String createJwtToken(String username, String password) throws Exception {

        AddUserDTO addUserDTO = new AddUserDTO();
        addUserDTO.setUsername(username);
        addUserDTO.setPassword(password);

        mockMvc.perform(post("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addUserDTO))).andReturn();

        UserAuthenticationDTO userAuthenticationDTO = new UserAuthenticationDTO();
        userAuthenticationDTO.setUsername(username);
        userAuthenticationDTO.setPassword(password);

        MvcResult result = mockMvc.perform(post("/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userAuthenticationDTO)))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        LoginResponse loginResponse = objectMapper.readValue(responseBody, LoginResponse.class);
        String jwtToken = loginResponse.getToken();


        return jwtToken;
    }

    @Test
    public void testSaveRecordsWithValidToken() throws Exception {
        String jwtToken = createJwtToken("test", "password");

        MvcResult result = mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .content(objectMapper.writeValueAsString(createTestPayload())))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertEquals("Records saved successfully", responseBody);

        verify(productsService).saveRecords(any(ProductsDTO.class));
    }

    @Test
    public void testSaveRecordsWithWithoutToken() throws Exception {
        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTestPayload())))
                .andExpect(status().isForbidden());
    }

    private ProductsDTO createTestPayload() {
        RecordsDTO recordsDTO = new RecordsDTO();
        recordsDTO.setEntryDate("2022-01-01");
        recordsDTO.setItemCode(123);
        recordsDTO.setItemName("Test Item");
        recordsDTO.setItemQuantity(5);
        recordsDTO.setStatus("Active");

        ProductsDTO payload = new ProductsDTO();
        payload.setTable("TestTable");
        payload.setRecords(Collections.singletonList(recordsDTO));

        return payload;
    }

}