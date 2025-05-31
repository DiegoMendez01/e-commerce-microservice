package com.diego.product.category.models;

import com.diego.product.product.models.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq_gen")
    @SequenceGenerator(name = "category_seq_gen", sequenceName = "category_seq", allocationSize = 1)
    private int id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<Product> products;
}