package com.example.demo.controller;

import com.example.demo.entity.Coin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CoinControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    //1.查詢幣別對應表資料API，並顯示其內容
    @Test
    void findCoinMapping() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/coin/findCoinMapping");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    //2.新增幣別對應表資料API，並顯示其內容
    @Test
    void addCoinMapping() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        Coin coin = new Coin();
        coin.setCurrencyEnName("VND");
        coin.setCurrencyCnName("越盾");


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                        .post("/coin/addCoinMapping")
                        .headers(httpHeaders)
                        .content(objectMapper.writeValueAsString(coin));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rtnCode", equalTo("100")))
                .andExpect(jsonPath("$.data.currencyEnName", equalTo("VND")));
    }

    //3.更新幣別對應表資料API，並顯示其內容
    @Test
    void modifyCoinMapping() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        Coin coin = new Coin();
        coin.setcId(1);
        coin.setCurrencyEnName("USD");
        coin.setCurrencyCnName("美元");


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                        .put("/coin/modifyCoinMapping")
                        .headers(httpHeaders)
                        .content(objectMapper.writeValueAsString(coin));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rtnCode", equalTo("100")))
                .andExpect(jsonPath("$.data.currencyCnName", equalTo("美元")));
    }

    //4.刪除幣別對應表資料API
    @Test
    void deleteCoinMapping() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        Coin coin = new Coin();
        coin.setcId(10);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/coin/deleteCoinMapping")
                .headers(httpHeaders)
                .content(objectMapper.writeValueAsString(coin));

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rtnCode", equalTo("100")));
    }

    //5.呼叫 coindesk API
    @Test
    void coinDeskAPI() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/coin/findCoinMapping");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    //6.資料轉換的API
    @Test
    void findConvertInfo() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/coin/findConvertInfo");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }
}