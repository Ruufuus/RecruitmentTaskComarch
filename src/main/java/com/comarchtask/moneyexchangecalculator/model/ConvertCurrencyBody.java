package com.comarchtask.moneyexchangecalculator.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ConvertCurrencyBody {
    @NotNull
    private String baseCurrencyCode;
    @NotNull
    private String targetCurrencyCode;
    @NotNull
    private Float amount;
}
