package com.endava.tech.courses.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BalanceHandlerTest {

    private BalanceHandler handler;

    @BeforeEach
    void setUp() {
        handler = new BalanceHandler();
    }

    @ParameterizedTest
    @MethodSource("options")
    void testProcessBalance(final BigDecimal balance, final BigDecimal historicBalance, final String expectedResult) {

        Map<String, BigDecimal> map = new HashMap<>();
        map.put("BALANCE", balance);
        map.put("HISTORIC_BALANCE", historicBalance);

        final String actualResult = handler.processBalance(map);

        assertEquals(expectedResult, actualResult);
    }

    private static Stream<Arguments> options() {
        return Stream.of(
            Arguments.of("10", "0", "10"),
            Arguments.of("-10", "-50", "-60"),
            Arguments.of("-10", "50", "-10"),
            Arguments.of("10", "-50", "-40"),
            Arguments.of("10", "20", "10"),
            Arguments.of("0", "20", "0"),
            Arguments.of("0", "-50", "-50"),
            Arguments.of(null, "-20", "-20"),
            Arguments.of(null, "20", "0"),
            Arguments.of("10", null, "10"),
            Arguments.of(null, null, "0")
        );
    }
}
