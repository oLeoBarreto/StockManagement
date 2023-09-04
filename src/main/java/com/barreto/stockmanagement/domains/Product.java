package com.barreto.stockmanagement.domains;

import jakarta.persistence.Entity;
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
    public String name;

    @NotEmpty(message = "Description could not be empty")
    @NotNull(message = "Description could not be null")
    @Size(min = 3, max = 200, message = "Description must be between 3 and 200 characters")
    public String description;

    @NotNull(message = "Unit price could not be null")
    @PositiveOrZero(message = "Unit price must be a positive value")
    public BigDecimal unitPrice;

    @NotEmpty(message = "Supplier could not be empty")
    @NotNull(message = "Supplier could not be null")
    @Size(min = 3, max = 200, message = "Supplier must be between 3 and 200 characters")
    public String supplier;

    @NotEmpty(message = "Category could not be empty")
    @NotNull(message = "Category could not be null")
    @Size(min = 3, max = 200, message = "Category must be between 3 and 200 characters")
    public String category;
}
