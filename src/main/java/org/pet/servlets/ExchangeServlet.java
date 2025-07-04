package org.pet.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.pet.dto.CurrencyExchangeRateRequestDto;
import org.pet.dto.CurrencyExchangeRateResponseDto;
import org.pet.exception.ExchangeRateException;
import org.pet.mapper.CurrencyExchangeMapper;
import org.pet.services.ExchangeRateService;
import org.pet.utils.JsonResponseBuilder;

import java.io.IOException;

@WebServlet("/exchange/*")
public class ExchangeServlet extends ExceptionHandler {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String baseCurrency = req.getParameter("from");
            String targetCurrency = req.getParameter("to");
            double amount = Double.parseDouble(req.getParameter("amount"));
            CurrencyExchangeRateRequestDto dto = CurrencyExchangeMapper.INSTANCE.toDto(baseCurrency, targetCurrency, amount);
            CurrencyExchangeRateResponseDto currencyExchangeRateResponseDto = ExchangeRateService.getINSTANCE().executeExchangeCurrency(dto);

            JsonResponseBuilder.buildJsonResponse(resp, currencyExchangeRateResponseDto);
        } catch (RuntimeException e) {
            throw new ExchangeRateException(e.getMessage());
        }
    }
}
