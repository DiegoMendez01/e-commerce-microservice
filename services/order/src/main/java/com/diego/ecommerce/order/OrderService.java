package com.diego.ecommerce.order;

import com.diego.ecommerce.customer.CustomerClient;
import com.diego.ecommerce.exception.BusinessException;
import com.diego.ecommerce.exception.NoStockAvailableException;
import com.diego.ecommerce.kafka.OrderConfirmation;
import com.diego.ecommerce.kafka.OrderProducer;
import com.diego.ecommerce.orderline.OrderLineRequest;
import com.diego.ecommerce.orderline.OrderLineService;
import com.diego.ecommerce.payment.PaymentClient;
import com.diego.ecommerce.payment.PaymentRequest;
import com.diego.ecommerce.product.ProductClient;
import com.diego.ecommerce.product.PurchaseRequest;
import com.diego.ecommerce.product.PurchaseResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createdOrder(OrderRequest request) {
        // check the customer --> OpenFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("No se puede crear la orden:: No existe un cliente con el ID asignado"));

        // purchase the products --> product-ms (RestTemplate)
        List<PurchaseResponse> purchasedProducts;
        try {
            purchasedProducts = this.productClient.purchaseProducts(request.products());
        } catch (HttpClientErrorException.BadRequest ex) {
            String msg = ex.getResponseBodyAsString();
            throw new NoStockAvailableException("Error al comprar productos: " + msg);
        }

        // persist order
        var order = this.repository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        var paymentRequest = new PaymentRequest(
                request.totalAmount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        // persis order lines
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.totalAmount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        // send order confirmation --> notification-ms (kafka)
        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return this.repository.findById(id)
                .map(this.mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No se encontró la orden con ID: %d", id)));
    }
}