package phammenungdungj2ee.bai4_qlsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phammenungdungj2ee.bai4_qlsp.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}