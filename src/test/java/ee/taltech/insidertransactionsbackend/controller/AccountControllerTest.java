package ee.taltech.insidertransactionsbackend.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import ee.taltech.insidertransactionsbackend.dto.AccountLoginDto;
import ee.taltech.insidertransactionsbackend.dto.AccountSignupDto;
import ee.taltech.insidertransactionsbackend.model.Account;
import ee.taltech.insidertransactionsbackend.repository.AccountRepository;
import ee.taltech.insidertransactionsbackend.security.jwt.AuthEntryPointJwt;
import ee.taltech.insidertransactionsbackend.security.jwt.JwtUtils;
import ee.taltech.insidertransactionsbackend.service.AccountDetailsImpl;
import ee.taltech.insidertransactionsbackend.service.AccountDetailsServiceImpl;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    AccountDetailsServiceImpl accountDetailsService;

    @MockBean
    AuthEntryPointJwt authEntryPointJwt;

    @MockBean
    JwtUtils jwtUtils;

    @MockBean
    AuthenticationManager authenticationManager;

    Account account;

    @BeforeEach
    void setUp() {
        account = new Account("User@User.com", "Password");
        account.setAccountId(1L);
    }

    @Test
    void registerUser() throws Exception {
        AccountSignupDto accountSignupDto = new AccountSignupDto();
        accountSignupDto.setUsername(this.account.getUsername());
        accountSignupDto.setPassword(this.account.getPassword());

        Mockito.when(this.accountRepository.save(this.account)).thenReturn(this.account);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/account/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(accountSignupDto));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void registerUser_userExists() throws Exception {
        AccountSignupDto accountSignupDto = new AccountSignupDto();
        accountSignupDto.setUsername(this.account.getUsername());
        accountSignupDto.setPassword(this.account.getPassword());

        Mockito.when(this.accountRepository.existsByUsername(accountSignupDto.getUsername())).thenReturn(Boolean.TRUE);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/account/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(accountSignupDto));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void authenticateUser() throws Exception {
        AccountLoginDto accountLoginDto = new AccountLoginDto();
        accountLoginDto.setUsername(this.account.getUsername());
        accountLoginDto.setPassword(this.account.getPassword());

        Mockito.when(this.accountRepository.save(this.account)).thenReturn(this.account);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/account/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(accountLoginDto));

        Authentication authentication = mock(Authentication.class);
        authentication.setAuthenticated(true);

        Mockito.when(this.authenticationManager.authenticate(any())).thenReturn(authentication);

        Mockito.when(this.jwtUtils.generateJwtToken(authentication)).thenReturn("mock-token");

        AccountDetailsImpl accountDetails = AccountDetailsImpl.build(this.account);

        Mockito.when(authentication.getPrincipal()).thenReturn(accountDetails);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is("mock-token")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("User@User.com")));
    }
}