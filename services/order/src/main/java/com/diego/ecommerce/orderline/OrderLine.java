package com.diego.ecommerce.orderline;

import com.diego.ecommerce.order.Order;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_line_seq_gen")
    @SequenceGenerator(name = "order_line_seq_gen", sequenceName = "order_line_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer productId;

    private double quantity;
}