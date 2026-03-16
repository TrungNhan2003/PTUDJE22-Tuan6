package phammenungdungj2ee.bai4_qlsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phammenungdungj2ee.bai4_qlsp.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}