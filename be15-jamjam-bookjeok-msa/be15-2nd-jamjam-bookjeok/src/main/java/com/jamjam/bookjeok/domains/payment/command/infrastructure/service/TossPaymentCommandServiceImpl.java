package com.jamjam.bookjeok.domains.payment.command.infrastructure.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamjam.bookjeok.domains.payment.command.dto.PaymentDTO;
import com.jamjam.bookjeok.domains.payment.command.dto.request.PaymentConfirmRequest;
import com.jamjam.bookjeok.exception.payment.ExternalPaymentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TossPaymentCommandServiceImpl implements TossPaymentCommandService {

    private static final String TOSS_CONFIRM_URL = "https://api.tosspayments.com/v1/payments/confirm";
    private static final String CONTENT_TYPE = "application/json";
    private static final String AUTH_PREFIX = "Basic ";
    private static final String AUTH_DELIMITER = ":";
    private static final int HTTP_STATUS_CODE_OK = 200;

    @Value("${tosspayments.api-secret-key}")
    private String tossPaymentsApiSecretKey;

    private final ObjectMapper objectMapper;

    @Override
    public PaymentDTO approvePayment(PaymentConfirmRequest paymentConfirmRequest) {
        try {
            HttpURLConnection connection = createConnection();
            sendRequest(connection, paymentConfirmRequest);

            int code = connection.getResponseCode();
            boolean isSuccess = code == HTTP_STATUS_CODE_OK;

            PaymentDTO paymentDTO = parsePaymentResponse(isSuccess, connection);
            log.info("paymentDTO = {}", paymentDTO);

            return paymentDTO;
        } catch (IOException e) {
            throw new ExternalPaymentException("Toss Payments 결제 승인 API 호출 중 오류가 발생했습니다.");
        }
    }

    private String getEncodedAuthorizationKey() {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((tossPaymentsApiSecretKey + AUTH_DELIMITER).getBytes(StandardCharsets.UTF_8));
        return AUTH_PREFIX + new String(encodedBytes);
    }

    private HttpURLConnection createConnection() throws IOException {
        URL url = new URL(TOSS_CONFIRM_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", getEncodedAuthorizationKey());
        connection.setRequestProperty("Content-Type", CONTENT_TYPE);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        return connection;
    }

    private void sendRequest(HttpURLConnection connection, PaymentConfirmRequest paymentConfirmRequest) throws IOException {
        String requestJson = objectMapper.writeValueAsString(paymentConfirmRequest);
        log.info("requestJson = {}", requestJson);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(requestJson.getBytes(StandardCharsets.UTF_8));
        }
    }

    private PaymentDTO parsePaymentResponse(boolean isSuccess, HttpURLConnection connection) throws IOException {
        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();
        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        PaymentDTO paymentDTO = objectMapper.readValue(reader, PaymentDTO.class);
        responseStream.close();
        return paymentDTO;
    }

}