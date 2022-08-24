package com.comarchtask.moneyexchangecalculator.utils;

import com.comarchtask.moneyexchangecalculator.model.Currency;

import java.util.List;
import java.util.Optional;

public class CurrencyConverterUtils {
    public static float calculateTargetCurrencyAmount
            (float baseCurrencyExchangeRatio, float targetCurrencyExchangeRatio, float amountOfBaseCurrency) {
        float exchangeRatio = baseCurrencyExchangeRatio / targetCurrencyExchangeRatio;
        return exchangeRatio * amountOfBaseCurrency;
    }

    public static void ErrorMessageCheck(Optional<Currency> optBaseCurrency, Optional<Currency> optTargetCurrency, StringBuilder responseErrorMessage) {
        if (!optBaseCurrency.isPresent() && !optTargetCurrency.isPresent()) {
            responseErrorMessage.append("Base and Target currency codes not found!");
        }
        if (!optBaseCurrency.isPresent()) {
            responseErrorMessage.append("Base Currency Code not found!");
        }
        if (!optTargetCurrency.isPresent()) {
            responseErrorMessage.append("Target Currency Code not found!");
        }
    }


    public static Optional<Currency> getCurrency(List<Currency> currencies, String targetCurrencyCode) {
        return currencies.stream()
                .filter(c -> c.getCurrencyCode().equalsIgnoreCase(targetCurrencyCode))
                .findFirst();
    }

}
