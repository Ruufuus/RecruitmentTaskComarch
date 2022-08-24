package com.comarchtask.moneyexchangecalculator.controller;

import com.comarchtask.moneyexchangecalculator.model.ConvertCurrencyBody;
import com.comarchtask.moneyexchangecalculator.model.Currency;
import com.comarchtask.moneyexchangecalculator.utils.CurrencyConverterUtils;
import com.comarchtask.moneyexchangecalculator.utils.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("api")
@RestController
@Slf4j
public class CurrencyConverterController {

    @GetMapping("/convert")
    public ResponseEntity<?> convertCurrency(
            @RequestBody @Valid Optional<ConvertCurrencyBody> optionalConvertCurrencyBody) {
        if (optionalConvertCurrencyBody.isPresent()) {
            ConvertCurrencyBody convertCurrencyBody = optionalConvertCurrencyBody.get();
            List<Currency> currencies
                    = ExchangeRateService.getCurrencies();
            Optional<Currency> optBaseCurrency = CurrencyConverterUtils.
                    getCurrency(currencies, convertCurrencyBody.getBaseCurrencyCode());
            Optional<Currency> optTargetCurrency = CurrencyConverterUtils.
                    getCurrency(currencies, convertCurrencyBody.getTargetCurrencyCode());
            if (optBaseCurrency.isPresent() && optTargetCurrency.isPresent()) {
                Float convertedValue = CurrencyConverterUtils
                        .calculateTargetCurrencyAmount(
                                optBaseCurrency.get().getCurrencyExchangeRate(),
                                optTargetCurrency.get().getCurrencyExchangeRate(),
                                convertCurrencyBody.getAmount()
                        );
                return ResponseEntity.ok()
                        .body(convertedValue);
            } else {
                StringBuilder responseErrorMessage = new StringBuilder();
                CurrencyConverterUtils.ErrorMessageCheck(optBaseCurrency, optTargetCurrency, responseErrorMessage);
                log.debug(responseErrorMessage.toString());
                //Not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(responseErrorMessage.toString());
            }
        }
        //Bad Request
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Please input proper request body!");
    }


    @GetMapping("currency")
    public ResponseEntity<?> getAllCurrency() {
        List<Currency> currencies
                = ExchangeRateService.getCurrencies();
        return ResponseEntity.ok()
                .body(currencies);
    }

    @GetMapping("currency/{code}")
    public ResponseEntity<?> getCurrencyByCode(@PathVariable String code) {
        Optional<Currency> optCurrency = CurrencyConverterUtils.
                getCurrency(ExchangeRateService.getCurrencies(), code);
        if (optCurrency.isPresent()) {
            return ResponseEntity.ok()
                    .body(optCurrency.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Currency with given code not found!");
    }

}
