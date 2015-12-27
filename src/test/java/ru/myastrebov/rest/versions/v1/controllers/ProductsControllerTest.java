package ru.myastrebov.rest.versions.v1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.myastrebov.core.Product;
import ru.myastrebov.core.services.ProductService;
import ru.myastrebov.rest.controllers.ProductsController;
import ru.myastrebov.rest.resources.ProductResource;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductsControllerTest {
    
    private MockMvc mockMvc;
    
    @InjectMocks
    private ProductsController uut;
    @Mock
    private ProductService productServiceMock;
    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(uut).build();
    }

    @Test
    public void testGetAllProducts() throws Exception {
        when(productServiceMock.getAllProducts()).thenReturn(Lists.newArrayList(new Product(1L, "table", 100L)));
        mockMvc.perform(get("/api/version/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName", equalTo("table")))
                .andExpect(jsonPath("$[0].cost", equalTo(100)))
                .andExpect(jsonPath("$[0].links[*].href", hasItem(endsWith("/api/version/products/1"))));

        verify(productServiceMock).getAllProducts();
    }

    @Test
    public void testCreateProduct() throws Exception {
        when(productServiceMock.createProduct(any(Product.class))).thenReturn(new Product(12L, "chair", 46L));

        String content = new ObjectMapper().writeValueAsString(new Product(null, "chair", 46L));

        mockMvc.perform(post("/api/version/products").content(content).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productName", equalTo("chair")))
                .andExpect(jsonPath("$.cost", equalTo(46)))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/api/version/products/12"))));

        verify(productServiceMock).createProduct(productArgumentCaptor.capture());
        Product product = productArgumentCaptor.getValue();
        assertThat(product.getId(), is(nullValue()));
        assertThat(product.getCost(), equalTo(46L));
        assertThat(product.getProductName(), equalTo("chair"));
    }

    @Test
    public void testFindProductById() throws Exception {
        when(productServiceMock.findById(any(Long.class))).thenReturn(new Product(1L, "cupboard", 48L));

        mockMvc.perform(get("/api/version/products/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName", equalTo("cupboard")))
                .andExpect(jsonPath("$.cost", equalTo(48)))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/api/version/products/1"))));
        verify(productServiceMock).findById(eq(1L));
    }

    @Test
    public void testFindNonExistingDish() throws Exception {
        when(productServiceMock.findById(any(Long.class))).thenReturn(null);

        mockMvc.perform(get("/api/version/products/1"))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(productServiceMock).findById(eq(1L));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        when(productServiceMock.updateProduct(any(Long.class), any(Product.class))).thenReturn(new Product(45L, "table", 14L));

        ProductResource productResource = new ProductResource();
        productResource.setCost(14L);
        productResource.setProductName("table");
        String content = new ObjectMapper().writeValueAsString(productResource);

        mockMvc.perform(put("/api/version/products/45").content(content).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName", equalTo("table")))
                .andExpect(jsonPath("$.cost", equalTo(14)))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/api/version/products/45"))));

        verify(productServiceMock).updateProduct(eq(45L), productArgumentCaptor.capture());
        Product productForUpdate = productArgumentCaptor.getValue();
        assertThat(productForUpdate.getProductName(), equalTo("table"));
        assertThat(productForUpdate.getCost(), equalTo(14L));
    }
}