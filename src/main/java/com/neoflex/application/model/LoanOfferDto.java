package com.neoflex.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanOfferDto {

    @Min(0)
    private Long applicationId;

    @Min(10000)
    @Max(10000000)
    private BigDecimal requestedAmount;

    @Min(10000)
    @Max(10250000)
    private BigDecimal totalAmount;

    @Min(6)
    @Max(60)
    private Integer term;

    @Min(1700)
    @Max(632895)
    private BigDecimal monthlyPayment;

    @Min(3)
    @Max(24)
    private BigDecimal rate;

    private boolean isInsuranceEnabled;

    private boolean isSalaryClient;
}
