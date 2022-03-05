package ee.taltech.insidertransactionsbackend.controller;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import ee.taltech.insidertransactionsbackend.model.Account;
import ee.taltech.insidertransactionsbackend.model.Issuer;
import ee.taltech.insidertransactionsbackend.repository.AccountRepository;
import ee.taltech.insidertransactionsbackend.repository.IssuerRepository;
import ee.taltech.insidertransactionsbackend.security.jwt.AuthEntryPointJwt;
import ee.taltech.insidertransactionsbackend.security.jwt.JwtUtils;
import ee.taltech.insidertransactionsbackend.service.AccountDetailsServiceImpl;

@WebMvcTest(WatchlistController.class)
class WatchlistControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    IssuerRepository issuerRepository;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    AccountDetailsServiceImpl accountDetailsService;

    @MockBean
    AuthEntryPointJwt authEntryPointJwt;

    @MockBean
    JwtUtils jwtUtils;

    Issuer issuer1;

    Issuer issuer2;

    Account account;

    @BeforeEach
    void setUp() {
        this.issuer1 = new Issuer("Test 1", true);
        this.issuer2 = new Issuer("Test 2", false);

        this.account = new Account("Username", "Password");
        this.account.setAccountId(1L);
        this.account.setIssuers(new HashSet<>(Arrays.asList(this.issuer1, this.issuer2)));
    }

    @Test
    @WithMockUser
    void getActiveIssuers() throws Exception {
        List<Issuer> issuers = new ArrayList<>(Collections.singletonList(this.issuer1));
        Mockito.when(this.issuerRepository.findByIsActiveTrue()).thenReturn(issuers);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/watchlist/issuers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser
    void getAccountIssuers() throws Exception {
        Mockito.when(this.accountRepository.findById(this.account.getAccountId())).thenReturn(Optional.of(this.account));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/watchlist/account/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[0].name", anyOf(is("Test 1"), is("Test 2"))))
                .andExpect(jsonPath("$[1].name", anyOf(is("Test 1"), is("Test 2"))));
    }

    @Test
    @WithMockUser
    void updateAccountIssuers() throws Exception {
        Account updatedAccount = this.account;
        updatedAccount.setIssuers(new HashSet<>(Collections.singletonList(new Issuer("Test 3", true))));

        Mockito.when(this.accountRepository.findById(this.account.getAccountId())).thenReturn(Optional.of(this.account));
        Mockito.when(this.accountRepository.save(updatedAccount)).thenReturn(updatedAccount);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/watchlist/account/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(updatedAccount.getIssuers()));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }
}