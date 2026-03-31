package phammenungdungj2ee.bai4_qlsp.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import phammenungdungj2ee.bai4_qlsp.model.CartItem;
import phammenungdungj2ee.bai4_qlsp.model.Product;
import phammenungdungj2ee.bai4_qlsp.service.OrdersService;
import phammenungdungj2ee.bai4_qlsp.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrdersService ordersService;

    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @GetMapping("")
    public String viewCart(HttpSession session, Model model) {
        List<CartItem> cart = getCart(session);

        long total = 0;
        for (CartItem item : cart) {
            total += (long) item.getProduct().getPrice() * item.getQuantity();
        }

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);

        return "product/cart";
    }

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session) {
        Product product = productService.get(id);

        if (product == null) {
            return "redirect:/products";
        }

        List<CartItem> cart = getCart(session);

        boolean found = false;
        for (CartItem item : cart) {
            if (item.getProduct().getId().equals(id)) {
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }

        if (!found) {
            cart.add(new CartItem(product, 1));
        }

        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam("id") Long id,
                             @RequestParam("quantity") int quantity,
                             HttpSession session) {
        List<CartItem> cart = getCart(session);

        for (CartItem item : cart) {
            if (item.getProduct().getId().equals(id)) {
                item.setQuantity(quantity);
                break;
            }
        }

        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id, HttpSession session) {
        List<CartItem> cart = getCart(session);

        cart.removeIf(item -> item.getProduct().getId().equals(id));

        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session) {
        List<CartItem> cart = getCart(session);
        ordersService.checkout(cart, session);
        return "redirect:/cart";
    }
}