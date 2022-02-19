package com.example.demo;


import com.apple.eawt.Application;
import com.example.demo.repository.CoinJpaRepository;
import com.example.demo.service.CoinService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.yml")
public class FirstTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CoinService coinService;

    @Autowired
    private CoinJpaRepository coinJpaRepository;

    @Before
    public void init() {
    }

    @Test
    public void givenEmployees_whenGetEmployees_thenStatus200()
            throws Exception {

    }
}
