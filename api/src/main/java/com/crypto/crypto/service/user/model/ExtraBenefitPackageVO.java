package com.crypto.crypto.service.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtraBenefitPackageVO {
    @NotNull(message = "must not be null or emtpy")
    private BigDecimal receiveBaseYearlyPackage;
    @NotBlank(message = "must not be null or emtpy")
    private String onBoardDay;

}
