package com.es.brujula.brugroup;

import com.es.brujula.brugroup.api.UserController;
import com.es.brujula.brugroup.api.UserDto;
import com.es.brujula.brugroup.domain.User;
import com.es.brujula.brugroup.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerIntegrationTest {
    public static final String ALL_USERS = "/api/users";
    public static final String juan = "juan";
    public static final String password = "password";
    public static final long ID = 1;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    private UserDto user1;

    @Before
    public void setUp() throws Exception {
        user1 = new UserDto();
        user1.setId(ID);
        user1.setUsername(juan);
        user1.setFullName(juan);
        user1.setPassword(password);
    }

    @Test
    public void findAll() throws Exception {
        List<UserDto> allUsers = Arrays.asList(user1);
        given(service.getUsers()).willReturn(allUsers);
        this.mvc.perform(get(ALL_USERS))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{'id': 1,'password': 'password';'fullName': 'juan', 'username': 'juan'}]"));
    }

    @Test
    public void findById() throws Exception {
        given(service.getUser(ID)).willReturn(java.util.Optional.of(user1));
        this.mvc.perform(get(ALL_USERS + "/" + ID))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{'id': 1,'password': 'password';'fullName': 'juan', 'username': 'juan'}"));
    }
}


