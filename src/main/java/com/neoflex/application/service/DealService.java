package com.neoflex.application.service;

import com.neoflex.application.client.DealRqService;
import com.neoflex.application.model.LoanApplicationRequestDto;
import com.neoflex.application.model.LoanOfferDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DealService {

    private final DealRqService dealRqService;

    public List<LoanOfferDto> getOffers(LoanApplicationRequestDto loanApplicationRequestDto) {
        return dealRqService.getOffers(loanApplicationRequestDto);
    }

    public void offerSelection(LoanOfferDto loanOfferDto) {
        dealRqService.offerSelection(loanOfferDto);
    }
}

