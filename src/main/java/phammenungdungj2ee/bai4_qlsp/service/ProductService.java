package phammenungdungj2ee.bai4_qlsp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import phammenungdungj2ee.bai4_qlsp.model.Product;
import phammenungdungj2ee.bai4_qlsp.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
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

    // THÊM METHOD NÀY
    public void updateImage(Product product, MultipartFile imageFile) {

        if (imageFile != null && !imageFile.isEmpty()) {

            String fileName = imageFile.getOriginalFilename();

            product.setImage(fileName);

        }

    }
}