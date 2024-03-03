package com.barreto.stockmanagement.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company extends AbstractDomain{
    @Column(unique = true)
    @NotEmpty(message = "The field CNPJ could not be empty")
    private String cnpj;
    private String name;
    @Email
    @Column(unique = true)
    private String email;
    private String password;
}
