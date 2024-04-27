package com.barreto.stockmanagement.useCases.user;

import com.barreto.stockmanagement.domains.Company;
import com.barreto.stockmanagement.domains.user.User;
import com.barreto.stockmanagement.domains.user.UserRole;
import com.barreto.stockmanagement.infra.DTOs.user.UserRegisterRequestBody;
import com.barreto.stockmanagement.infra.DTOs.user.UserUpdateLoginResponseBody;
import com.barreto.stockmanagement.infra.config.token.TokenManageService;
import com.barreto.stockmanagement.infra.database.repository.CompanyRepository;
import com.barreto.stockmanagement.infra.database.repository.UserRepository;
import com.barreto.stockmanagement.infra.exceptions.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository repository;

    @Mock
    private TokenManageService tokenManageService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private SendMailUseCase sendMailUseCase;

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        var company = new Company(
                "12.123.123/0001-12",
                "Company test",
                "company@test.com",
                "12345"
        );
        company.setId("companyId");

        var user = new User(
                "test@gmail.com",
                "12345",
                "user test",
                UserRole.ADMIN,
                company
        );
        user.setId("userId");

        PageImpl<User> productPage = new PageImpl<>(List.of(user));

        when(repository.findByCompanyId(anyString(), any(Pageable.class))).thenReturn(productPage);
        when(repository.findById(anyString())).thenReturn(Optional.of(user));
        when(repository.save(any(User.class))).thenReturn(user);

        when(repository.findByLogin("test@gmail.com")).thenReturn(user);
        when(repository.findByLogin("test1@gmail.com")).thenReturn(null);

        when(companyRepository.findByCnpj(anyString())).thenReturn(Optional.of(company));

        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should be able to create a new user")
    void testCreateNewUser() {
        var user = userService.registerUser(new UserRegisterRequestBody(
                "tes1t@gmail.com",
                "12345",
                "user test",
                UserRole.ADMIN,
                "12.123.123/0001-12"
        ));

        assertNotNull(user);
        assertEquals("userId", user.getId());
    }

    @Test
    @DisplayName("Should not be able to create a user with registered email")
    void testDoNotCreateNewUserWithUsedEmail() {
        assertThrows(
                BadRequestException.class,
                () -> userService.registerUser(new UserRegisterRequestBody(
                        "test@gmail.com",
                        "12345",
                        "user test",
                        UserRole.ADMIN,
                        "12.123.123/0001-12"
                ))
        );
    }

    @Test
    @DisplayName("Should be able to update a user data")
    void testUpdateUser() {
        var user = userService.updateUserLogin(new UserUpdateLoginResponseBody(
                "userId",
                "test@gmail.com",
                "123456",
                "user test",
                UserRole.USER)
        );

        assertNotNull(user);
        assertEquals(UserRole.USER, user.getRole());
    }

    @Test
    @DisplayName("Should be able to list all users by company")
    void testListUsersByCompany() {
        var userPage = userService.listAllUserByCompany(
                "companyId", PageRequest.of(0, 10));

        assertNotNull(userPage.getContent());
        assertEquals("userId", userPage.getContent().get(0).getId());
    }
}
