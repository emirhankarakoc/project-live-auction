package com.karakoc.mezat.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,String> {

    Optional<Product> findById(String id);
}
