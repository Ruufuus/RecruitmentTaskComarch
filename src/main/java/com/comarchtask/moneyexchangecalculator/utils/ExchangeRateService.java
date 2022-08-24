package com.comarchtask.moneyexchangecalculator.utils;

import com.comarchtask.moneyexchangecalculator.Constants;
import com.comarchtask.moneyexchangecalculator.exceptions.CurrencyExchangeServiceNotWorkingException;
import com.comarchtask.moneyexchangecalculator.model.Currency;
import com.comarchtask.moneyexchangecalculator.model.ExchangeRateTable;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class ExchangeRateService {

    private static List<Currency> currencies;

    public static List<Currency> getCurrencies() {
        return currencies;
    }

    public static void setCurrencies(List<Currency> currencies) {
        ExchangeRateService.currencies = currencies;
    }

    public List<Currency> getExchangeRates() throws CurrencyExchangeServiceNotWorkingException {
        RestTemplate restTemplate = new RestTemplate();

        String baseUri = Constants.NBP_EXCHANGE_RATES_URI;
        List<String> tableTypes = new ArrayList<>(Arrays.asList(Constants.TABLE_TYPES));
        List<Currency> currencies = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        for (String tableType :
                tableTypes) {
            String uri = new StringBuilder(baseUri)
                    .append(tableType)
                    .toString();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<ExchangeRateTable[]> result =
                    restTemplate.exchange(uri, HttpMethod.GET, entity, ExchangeRateTable[].class);
            if (result.getBody() != null) {
                currencies.addAll(result.getBody()[0].getCurrencies());
            } else {
                throw new CurrencyExchangeServiceNotWorkingException("Communication with currency service is not working!");
            }
        }
        return currencies;
    }
}
