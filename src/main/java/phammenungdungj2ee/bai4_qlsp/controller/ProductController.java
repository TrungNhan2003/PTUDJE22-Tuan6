package phammenungdungj2ee.bai4_qlsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

import phammenungdungj2ee.bai4_qlsp.model.Product;
import phammenungdungj2ee.bai4_qlsp.model.Category;
import phammenungdungj2ee.bai4_qlsp.service.ProductService;
import phammenungdungj2ee.bai4_qlsp.service.CategoryService;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // ===================== LIST =====================
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        return "product/products";
    }

    // ===================== CREATE =====================
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute("product") Product newProduct,
            BindingResult result,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("imageProduct") MultipartFile imageProduct,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }

        // xử lý ảnh
        if (imageProduct != null && !imageProduct.isEmpty()) {
            productService.updateImage(newProduct, imageProduct);
        }

        // set category
        Category selectedCategory = categoryService.get(categoryId);
        newProduct.setCategory(selectedCategory);

        productService.add(newProduct);

        return "redirect:/products";
    }

    // ===================== EDIT =====================
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {

        Product product = productService.get(id);

        if (product == null) {
            return "error/404";
        }

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAll());

        return "product/edit";
    }

    @PostMapping("/edit")
    public String edit(
            @Valid @ModelAttribute("product") Product editProduct,
            BindingResult result,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("imageProduct") MultipartFile imageProduct,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }

        // update ảnh nếu có
        if (imageProduct != null && !imageProduct.isEmpty()) {
            productService.updateImage(editProduct, imageProduct);
        }

        Category selectedCategory = categoryService.get(categoryId);
        editProduct.setCategory(selectedCategory);

        productService.update(editProduct);

        return "redirect:/products";
    }
}