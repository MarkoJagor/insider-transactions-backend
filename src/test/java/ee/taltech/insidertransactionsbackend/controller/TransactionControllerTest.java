package ee.taltech.insidertransactionsbackend.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import ee.taltech.insidertransactionsbackend.model.Transaction;
import ee.taltech.insidertransactionsbackend.repository.TransactionRepository;
import ee.taltech.insidertransactionsbackend.security.jwt.AuthEntryPointJwt;
import ee.taltech.insidertransactionsbackend.security.jwt.JwtUtils;
import ee.taltech.insidertransactionsbackend.service.AccountDetailsServiceImpl;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionRepository transactionRepository;

    @MockBean
    AccountDetailsServiceImpl accountDetailsService;

    @MockBean
    AuthEntryPointJwt authEntryPointJwt;

    @MockBean
    JwtUtils jwtUtils;

    Transaction transaction1;
    Transaction transaction2;

    @BeforeEach
    void setUp() {
        this.transaction1 = new Transaction(new Date(2022 - 12 - 12), new Date(2022 - 12 - 15), "Investor 1", "Investor Position 1",
                "Issuer 1", "Instrument 1", "Type 1", 1.0, 1.0, "Market 1", false, "");

        this.transaction2 = new Transaction(new Date(2021 - 12 - 12), new Date(2021 - 12 - 15), "Investor 2", "Investor Position 2",
                "Issuer 2", "Instrument 2", "Type 2", 2.0, 2.0, "Market 2", true, "reason");
    }

    @Test
    void getAllTransactions() throws Exception {
        List<Transaction> transactions = new ArrayList<>(Arrays.asList(this.transaction1, this.transaction2));
        Mockito.when(this.transactionRepository.findAll(Sort.by(Sort.Direction.DESC, "publishedDate"))).thenReturn(transactions);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getTransactionById() throws Exception {
        transaction1.setTransactionId(1L);
        Mockito.when(this.transactionRepository.findById(this.transaction1.getTransactionId())).thenReturn(Optional.of(this.transaction1));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transactions/1")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.transactionId", is(1)));
    }
}