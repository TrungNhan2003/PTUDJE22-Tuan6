package phammenungdungj2ee.bai4_qlsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phammenungdungj2ee.bai4_qlsp.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}