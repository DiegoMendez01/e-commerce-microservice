package com.diego.ecommerce.product;

import com.diego.ecommerce.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class ProductClient {

    @Value("${application.config.product-url}")
    private String productUrl;
    private final RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> requestBody) {
        if (requestBody == null || requestBody.isEmpty()) {
            throw new BusinessException("No se proporcionaron productos para comprar");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);

        HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(requestBody, headers);
        ParameterizedTypeReference<List<PurchaseResponse>> responseType = new ParameterizedTypeReference<>() {
        };

        ResponseEntity<List<PurchaseResponse>> responseEntity = restTemplate.exchange(
                productUrl + "/purchase",
                POST,
                requestEntity,
                responseType
        );

        if (responseEntity.getStatusCode().isError()) {
            throw new BusinessException("Ocurrió un error al procesar la compra de productos: " + responseEntity.getStatusCode());
        }

        return  responseEntity.getBody();
    }

    public boolean checkStock(Integer productId, double quantity) {
        String url = String.format("%s/%d/stock?quantity=%f", productUrl, productId, quantity);

        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(url, Boolean.class);

        if (responseEntity.getStatusCode().isError()) {
            throw new BusinessException("Error al consultar stock del producto con ID " + productId);
        }

        Boolean available = responseEntity.getBody();
        return available != null && available;
    }
}