package ru.myastrebov.rest.controllers.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.myastrebov.core.security.UserRole;
import ru.myastrebov.rest.WebConfiguration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Maxim
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})
@ContextConfiguration(classes = {WebConfiguration.class})
public class ProductsControllerSecurityTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testSuccessAnonymousAccess() throws Exception {
        mockMvc.perform(get("/api/version/products"))
                .andDo(print())
                .andExpect(status().isOk());

//        when(productServiceMock.findById(any(Long.class))).thenReturn(new Product(1L, "product", 78L));
//        mockMvc.perform(get("/api/version/products/1"))
//                .andDo(print())
//                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSuccessAdminAccess() throws Exception {
        mockMvc
                .perform(delete("/api/version/products/1")
                                .with(user("user")
                                                .password("password")
                                                .roles(UserRole.ADMIN.getName())
                                )
                )
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(delete("/api/version/products/1"))
                .andDo(print())
                .andExpect(status().isFound());
    }
}
