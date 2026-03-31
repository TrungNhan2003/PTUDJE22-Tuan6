package phammenungdungj2ee.bai4_qlsp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import phammenungdungj2ee.bai4_qlsp.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Product> findByCategory_Id(Long categoryId, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndCategory_Id(String keyword, Long categoryId, Pageable pageable);
}