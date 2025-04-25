package com.jamjam.bookjeok.domains.payment.command.infrastructure.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamjam.bookjeok.domains.payment.command.dto.PaymentDTO;
import com.jamjam.bookjeok.domains.payment.command.dto.TossPaymentApproveRequest;
import com.jamjam.bookjeok.exception.payment.ExternalPaymentException;
import com.jamjam.bookjeok.exception.payment.PaymentApprovalFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Toss Payments API와 통신하는 클라이언트 클래스입니다.
 * 이 클래스는 HTTP 통신을 처리합니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TossPaymentClientImpl implements TossPaymentClient {

    private static final String TOSS_CONFIRM_URL = "https://api.tosspayments.com/v1/payments/confirm";
    private static final String AUTH_PREFIX = "Basic ";
    private static final String AUTH_DELIMITER = ":";

    @Value("${tosspayments.api-secret-key}")
    private String tossPaymentsApiSecretKey;

    private final ObjectMapper objectMapper;

    /**
     * Toss Payments API로 결제 승인 요청을 전송합니다.
     *
     * @param request 결제 승인 요청 객체
     * @return Toss로부터 받은 결제 응답 객체
     * @throws ExternalPaymentException Toss API와 통신 중 오류가 발생한 경우
     */
    @Override
    public PaymentDTO requestPaymentApproval(TossPaymentApproveRequest request) {
        try {
            HttpURLConnection connection = getConnection();
            sendRequest(connection, request);
            return parseAndValidateResponse(connection);
        } catch (IOException e) {
            throw new ExternalPaymentException("Toss Payments 결제 승인 API 호출 중 오류가 발생했습니다.");
        }
    }

    private HttpURLConnection getConnection() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(TOSS_CONFIRM_URL).openConnection();
        connection.setRequestMethod(HttpMethod.POST.name());
        connection.setRequestProperty("Authorization", getEncodedAuthorizationKey());
        connection.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        connection.setDoOutput(true);
        return connection;
    }

    private String getEncodedAuthorizationKey() {
        String credential = tossPaymentsApiSecretKey + AUTH_DELIMITER;
        return AUTH_PREFIX + Base64.getEncoder().encodeToString(credential.getBytes(StandardCharsets.UTF_8));
    }

    private void sendRequest(HttpURLConnection connection, TossPaymentApproveRequest request) throws IOException {
        String requestBody = objectMapper.writeValueAsString(request);
        log.info("Toss 결제 승인 요청 JSON = {}", requestBody);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(requestBody.getBytes(StandardCharsets.UTF_8));
        }
    }

    private PaymentDTO parseAndValidateResponse(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();

        try (InputStream responseStream = getResponseStreamByCode(connection, responseCode);
                Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8)
        ) {
            PaymentDTO paymentDTO = objectMapper.readValue(reader, PaymentDTO.class);
            validateResponseCode(responseCode);
            return paymentDTO;
        }
    }

    private InputStream getResponseStreamByCode(HttpURLConnection connection, int httpStatusCode) throws IOException {
        return httpStatusCode == HttpURLConnection.HTTP_OK ? connection.getInputStream() : connection.getErrorStream();
    }

    private void validateResponseCode(int httpStatusCode) {
        if (httpStatusCode != HttpURLConnection.HTTP_OK) {
            throw new PaymentApprovalFailedException("결제 승인에 실패하였습니다.");
        }
    }

}