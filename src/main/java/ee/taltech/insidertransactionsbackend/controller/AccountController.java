package ee.taltech.insidertransactionsbackend.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.taltech.insidertransactionsbackend.dto.AccountLoginDto;
import ee.taltech.insidertransactionsbackend.dto.AccountSignupDto;
import ee.taltech.insidertransactionsbackend.model.Account;
import ee.taltech.insidertransactionsbackend.repository.AccountRepository;
import ee.taltech.insidertransactionsbackend.response.JwtResponse;
import ee.taltech.insidertransactionsbackend.response.MessageResponse;
import ee.taltech.insidertransactionsbackend.security.jwt.JwtUtils;
import ee.taltech.insidertransactionsbackend.service.AccountDetailsImpl;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountRepository accountRepository;

    private final PasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @Autowired
    public AccountController(final AccountRepository accountRepository, final PasswordEncoder encoder,
            final AuthenticationManager authenticationManager, final JwtUtils jwtUtils) {
        this.accountRepository = accountRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody AccountSignupDto accountSignup) {
        if (Boolean.TRUE.equals(this.accountRepository.existsByUsername(accountSignup.getUsername()))) {
            return ResponseEntity.
                    badRequest().
                    body(new MessageResponse(String.format("Error: Account with email %s already exists", accountSignup.getUsername())));
        }

        Account account = new Account(accountSignup.getUsername(), this.encoder.encode(accountSignup.getPassword()));

        this.accountRepository.save(account);

        return ResponseEntity.ok(new MessageResponse(String.format("Account with email %s registered successfully", account.getUsername())));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AccountLoginDto accountLogin) {

        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(accountLogin.getUsername(), accountLogin.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = this.jwtUtils.generateJwtToken(authentication);
        AccountDetailsImpl accountDetails = (AccountDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, accountDetails.getId(), accountDetails.getUsername()));
    }
}
