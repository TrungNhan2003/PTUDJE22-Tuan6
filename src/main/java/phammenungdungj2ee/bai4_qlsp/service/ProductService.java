package phammenungdungj2ee.bai4_qlsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import phammenungdungj2ee.bai4_qlsp.model.Product;
import phammenungdungj2ee.bai4_qlsp.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product get(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void add(Product product) {
        productRepository.save(product);
    }

    public void update(Product product) {
        productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> searchByName(String keyword, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }

    public Page<Product> filterByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategory_Id(categoryId, pageable);
    }

    public Page<Product> searchByNameAndCategory(String keyword, Long categoryId, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCaseAndCategory_Id(keyword, categoryId, pageable);
    }

    public void updateImage(Product product, MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = imageFile.getOriginalFilename();
            product.setImage(fileName);
        }
    }
}