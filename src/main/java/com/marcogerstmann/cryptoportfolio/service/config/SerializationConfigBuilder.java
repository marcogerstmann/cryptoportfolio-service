package com.marcogerstmann.cryptoportfolio.service.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.zalando.jackson.datatype.money.MoneyModule;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SerializationConfigBuilder {

    public static final String MONETARY_AMOUNT_FIELD_NAME = "amount";
    public static final String MONETARY_AMOUNT_CURRENCY_NAME = "currency";

    public static Jackson2ObjectMapperBuilder buildJackson2ObjectMapperBuilder() {
        final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.indentOutput(true);
        builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        builder.serializationInclusion(JsonInclude.Include.NON_EMPTY);
        builder.indentOutput(true);
        builder.simpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        builder.modules(new JavaTimeModule(), createMoneyModule(), new Jdk8Module());
        return builder;
    }

    private static MoneyModule createMoneyModule() {
        return new MoneyModule()
            .withAmountFieldName(MONETARY_AMOUNT_FIELD_NAME)
            .withCurrencyFieldName(MONETARY_AMOUNT_CURRENCY_NAME);
    }
}
