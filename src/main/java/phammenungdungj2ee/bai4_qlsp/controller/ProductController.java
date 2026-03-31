package phammenungdungj2ee.bai4_qlsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    // ===================== LIST + SEARCH + PAGINATION + SORT + FILTER CATEGORY =====================
    @GetMapping("")
    public String index(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            Model model) {

        int pageSize = 5;

        Sort sortObj = Sort.unsorted();
        if ("asc".equals(sort)) {
            sortObj = Sort.by("price").ascending();
        } else if ("desc".equals(sort)) {
            sortObj = Sort.by("price").descending();
        }

        Page<Product> productPage;
        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        boolean hasCategory = categoryId != null;

        if (hasKeyword && hasCategory) {
            productPage = productService.searchByNameAndCategory(
                    keyword, categoryId, PageRequest.of(page, pageSize, sortObj));
        } else if (hasKeyword) {
            productPage = productService.searchByName(
                    keyword, PageRequest.of(page, pageSize, sortObj));
        } else if (hasCategory) {
            productPage = productService.filterByCategory(
                    categoryId, PageRequest.of(page, pageSize, sortObj));
        } else {
            productPage = productService.getAll(
                    PageRequest.of(page, pageSize, sortObj));
        }

        model.addAttribute("listproduct", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categories", categoryService.getAll());

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

        if (imageProduct != null && !imageProduct.isEmpty()) {
            productService.updateImage(newProduct, imageProduct);
        }

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

        if (imageProduct != null && !imageProduct.isEmpty()) {
            productService.updateImage(editProduct, imageProduct);
        }

        Category selectedCategory = categoryService.get(categoryId);
        editProduct.setCategory(selectedCategory);

        productService.update(editProduct);

        return "redirect:/products";
    }

    // ===================== DELETE =====================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products";
    }
}