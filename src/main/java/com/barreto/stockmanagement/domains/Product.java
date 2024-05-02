package com.barreto.stockmanagement.domains;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product extends AbstractDomain{

    @NotEmpty(message = "Name could not be empty")
    @NotNull(message = "Name could not be null")
    private String name;

    @NotEmpty(message = "Description could not be empty")
    @NotNull(message = "Description could not be null")
    @Size(min = 3, max = 200, message = "Description must be between 3 and 200 characters")
    private String description;

    @NotNull(message = "Unit price could not be null")
    @PositiveOrZero(message = "Unit price must be a positive value")
    private BigDecimal unitPrice;

    @NotEmpty(message = "Supplier could not be empty")
    @NotNull(message = "Supplier could not be null")
    @Size(min = 3, max = 200, message = "Supplier must be between 3 and 200 characters")
    private String supplier;

    @NotEmpty(message = "Category could not be empty")
    @NotNull(message = "Category could not be null")
    @Size(min = 3, max = 200, message = "Category must be between 3 and 200 characters")
    private String category;

    private String image;

    private Float stockQuantity = 0F;

    @NotNull(message = "Company id could not be null")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;
}
