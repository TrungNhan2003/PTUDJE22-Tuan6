package phammenungdungj2ee.bai4_qlsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phammenungdungj2ee.bai4_qlsp.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}