package com.abc.farms.abcfarmsbackendjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.farms.abcfarmsbackendjava.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}