package com.neoflex.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.neoflex.application.client.DealRqService;
import com.neoflex.application.model.LoanApplicationRequestDto;
import com.neoflex.application.model.LoanOfferDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerTest {

    public static final int MIN_AMOUNT = 100000;
    public static final int TERM = 6;
    public static final int MIN_TERM = 6;
    public static final String SMALL_PASSPORT_SERIES = "444";
    public static final int SMALL_AMOUNT = 1000;
    public static final String PASSPORT_SERIES = "4444";
    public static final int SMALL_TERM = 5;

    @Autowired
    MockMvc mockMvc;
    @MockBean
    DealRqService dealRqService;

    private final ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();


    @Test
    void controller_dealApplication_SuccessTest() throws Exception {
        when(dealRqService.getOffers(any()))
                .thenReturn(Collections.singletonList(LoanOfferDto.builder()
                        .applicationId(1L)
                        .isInsuranceEnabled(true)
                        .isSalaryClient(false)
                        .monthlyPayment(BigDecimal.valueOf(5000))
                        .rate(BigDecimal.valueOf(10))
                        .requestedAmount(BigDecimal.valueOf(100000))
                        .totalAmount(BigDecimal.valueOf(110000))
                        .build()));
        LoanApplicationRequestDto loanApplicationRequestDto = CreateLoanApplicationRequestDto(MIN_TERM, MIN_AMOUNT, PASSPORT_SERIES);
        mockMvc.perform(post("/deal/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanApplicationRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    void controller_dealOffer_SuccessTest() throws Exception {
        doNothing().when(dealRqService).offerSelection(any());
        LoanOfferDto loanOfferDto = createLoanOfferDto();
        mockMvc.perform(put("/deal/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanOfferDto)))
                .andExpect(status().isOk());
    }

    @Test
    void controller_dealApplication_SmallTermTest() throws Exception {
        LoanApplicationRequestDto loanApplicationRequestDto = CreateLoanApplicationRequestDto(SMALL_TERM, MIN_AMOUNT, PASSPORT_SERIES);
        mockMvc.perform(post("/deal/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanApplicationRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void controller_dealApplication_SmallAmountTest() throws Exception {
        LoanApplicationRequestDto loanApplicationRequestDto = CreateLoanApplicationRequestDto(TERM, SMALL_AMOUNT, PASSPORT_SERIES);
        mockMvc.perform(post("/deal/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanApplicationRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void controller_dealApplication_SmallPassportSeries() throws Exception {
        LoanApplicationRequestDto loanApplicationRequestDto = CreateLoanApplicationRequestDto(MIN_TERM, MIN_AMOUNT, SMALL_PASSPORT_SERIES);
        mockMvc.perform(post("/deal/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanApplicationRequestDto)))
                .andExpect(status().isBadRequest());
    }

    public LoanApplicationRequestDto CreateLoanApplicationRequestDto(Integer term, Integer amount, String passportSeries) {
        return LoanApplicationRequestDto.builder()
                .firstName("Darth")
                .lastName("Vader")
                .middleName("Lord")
                .amount(BigDecimal.valueOf(amount))
                .birthdate(LocalDate.now().minusYears(30))
                .passportNumber("111111")
                .passportSeries(passportSeries)
                .term(term)
                .email("LordVader@gmail.com")
                .build();
    }

    public LoanOfferDto createLoanOfferDto() {
        return LoanOfferDto.builder()
                .applicationId(5L)
                .requestedAmount(BigDecimal.valueOf(300000))
                .totalAmount(BigDecimal.valueOf(312000))
                .term(24)
                .monthlyPayment(BigDecimal.valueOf(14833.05))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .rate(BigDecimal.valueOf(13))
                .build();
    }
}
