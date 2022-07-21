package com.neoflex.application.client;

import com.neoflex.application.model.LoanApplicationRequestDto;
import com.neoflex.application.model.LoanOfferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "dealRqService", url = "${feign-client.deal.url}")
public interface DealRqService {
    @PostMapping("deal/application")
    List<LoanOfferDto> getOffers(@RequestBody LoanApplicationRequestDto loanApplicationRequestDto);

    @PutMapping("deal/offer")
    void offerSelection(@RequestBody LoanOfferDto loanOfferDto);
}
