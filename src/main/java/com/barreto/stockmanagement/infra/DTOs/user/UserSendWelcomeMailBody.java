package com.barreto.stockmanagement.infra.DTOs.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
public class UserSendWelcomeMailBody implements Serializable {
    public String email;
    public String name;
}
