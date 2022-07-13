package com.example.mall.controller;

import com.example.mall.dto.BuyItem;
import com.example.mall.dto.CreateOrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getOrdersSuccess() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/orders",1);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.result", hasSize(2)))
                .andExpect(jsonPath("$.result[0].orderId", notNullValue()))
                .andExpect(jsonPath("$.result[0].userId", equalTo(1)))
                .andExpect(jsonPath("$.result[0].totalAmount", equalTo(100000)))
                .andExpect(jsonPath("$.result[0].orderItemList", hasSize(1)))
                .andExpect(jsonPath("$.result[0].createdDate", notNullValue()))
                .andExpect(jsonPath("$.result[0].lastModifiedDate", notNullValue()))
                .andExpect(jsonPath("$.result[1].orderId", notNullValue()))
                .andExpect(jsonPath("$.result[1].userId", equalTo(1)))
                .andExpect(jsonPath("$.result[1].totalAmount", equalTo(500690)))
                .andExpect(jsonPath("$.result[1].orderItemList", hasSize(3)))
                .andExpect(jsonPath("$.result[1].createdDate", notNullValue()))
                .andExpect(jsonPath("$.result[1].lastModifiedDate", notNullValue()));
    }

    @Test
    void getOrdersPage() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/orders",1)
                .param("limit","2")
                .param("offset","2");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", equalTo(2)))
                .andExpect(jsonPath("$.offset", equalTo(2)))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.result", hasSize(0)));
    }

    @Test
    void getOrdersUserNoOrder() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/orders",2);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.result", hasSize(0)));
    }

    @Test
    void getOrdersUserNotExist() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/users/{userId}/orders",100);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.result", hasSize(0)));
    }

    @Transactional
    @Test
    void createOrderSuccess() throws Exception{
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        List<BuyItem> buyItemList = new ArrayList<>();

        BuyItem buyItem1 = new BuyItem();
        buyItem1.setProductId(1);
        buyItem1.setQuantity(5);
        buyItemList.add(buyItem1);

        BuyItem buyItem2 = new BuyItem();
        buyItem2.setProductId(2);
        buyItem2.setQuantity(2);
        buyItemList.add(buyItem2);

        createOrderRequest.setBuyItemList(buyItemList);
        String json = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.orderId", notNullValue()))
                .andExpect(jsonPath("$.userId", equalTo(1)))
                .andExpect(jsonPath("$.totalAmount", equalTo(750)))
                .andExpect(jsonPath("$.orderItemList", hasSize(2)))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
    }

    @Transactional
    @Test
    void createOrderEmptyBuyItemList() throws Exception{
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        List<BuyItem> buyItemList = new ArrayList<>();
        createOrderRequest.setBuyItemList(buyItemList);
        String json = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Transactional
    @Test
    void createOrderUserNotExist() throws Exception{
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        List<BuyItem> buyItemList = new ArrayList<>();

        BuyItem buyItem = new BuyItem();
        buyItem.setProductId(1);
        buyItem.setQuantity(5);
        buyItemList.add(buyItem);

        createOrderRequest.setBuyItemList(buyItemList);
        String json = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders",100)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Transactional
    @Test
    void createOrderProductNotExist() throws Exception{
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        List<BuyItem> buyItemList = new ArrayList<>();

        BuyItem buyItem = new BuyItem();
        buyItem.setProductId(100);
        buyItem.setQuantity(5);
        buyItemList.add(buyItem);

        createOrderRequest.setBuyItemList(buyItemList);
        String json = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Transactional
    @Test
    void createOrderStockNotEnough() throws Exception{
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        List<BuyItem> buyItemList = new ArrayList<>();

        BuyItem buyItem = new BuyItem();
        buyItem.setProductId(1);
        buyItem.setQuantity(5000);
        buyItemList.add(buyItem);

        createOrderRequest.setBuyItemList(buyItemList);
        String json = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{userId}/orders",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }
}