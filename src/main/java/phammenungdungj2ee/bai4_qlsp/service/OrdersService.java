package phammenungdungj2ee.bai4_qlsp.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phammenungdungj2ee.bai4_qlsp.model.CartItem;
import phammenungdungj2ee.bai4_qlsp.model.OrderDetail;
import phammenungdungj2ee.bai4_qlsp.model.Orders;
import phammenungdungj2ee.bai4_qlsp.repository.OrderDetailRepository;
import phammenungdungj2ee.bai4_qlsp.repository.OrdersRepository;

import java.util.Date;
import java.util.List;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public void checkout(List<CartItem> cart, HttpSession session) {
        if (cart == null || cart.isEmpty()) {
            return;
        }

        long total = 0;
        for (CartItem item : cart) {
            total += (long) item.getProduct().getPrice() * item.getQuantity();
        }

        Orders order = new Orders(new Date(), total);
        ordersRepository.save(order);

        for (CartItem item : cart) {
            long subtotal = (long) item.getProduct().getPrice() * item.getQuantity();

            OrderDetail detail = new OrderDetail(
                    item.getQuantity(),
                    Long.valueOf(item.getProduct().getPrice()),
                    subtotal,
                    order,
                    item.getProduct()
            );

            orderDetailRepository.save(detail);
        }

        session.removeAttribute("cart");
    }
}