package com.barreto.stockmanagement.infra.database.repository;

import com.barreto.stockmanagement.domains.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByLogin(String login);
    @Query(
            value = "SELECT * FROM Users u WHERE u.login = ?1 AND u.company_id = ?2",
            nativeQuery = true
    )
    User findByLoginAndCompany(String login, String companyID);
    Page<User> findByCompanyId(String companyID, Pageable pageable);
}
