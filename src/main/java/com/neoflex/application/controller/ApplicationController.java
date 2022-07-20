package com.neoflex.application.controller;

import com.neoflex.application.model.LoanApplicationRequestDto;
import com.neoflex.application.model.LoanOfferDto;
import com.neoflex.application.service.DealService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class ApplicationController {

    private final DealService dealService;

    @Operation(summary = "calculation Of PossibleConditions")
    @PostMapping("/deal/application")
    public ResponseEntity<List<LoanOfferDto>> dealApplication(@RequestBody @Valid LoanApplicationRequestDto loanApplicationRequestDto) {
        log.info("dealApplication method start: request body={}", loanApplicationRequestDto);
        return ResponseEntity.ok(dealService.getOffers(loanApplicationRequestDto));
    }

    @Operation(summary = "selecting one of the offers")
    @PutMapping("/deal/offer")
    public void dealOffer(@RequestBody @Valid LoanOfferDto loanOfferDto) {
        log.info("dealOffer method start: request body={}", loanOfferDto);
        dealService.offerSelection(loanOfferDto);
    }
}
