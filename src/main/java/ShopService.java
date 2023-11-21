import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Builder
@Data
public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            productToOrder.ifPresentOrElse(
                    product -> products.add(product),
                    ()->System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!"));
            /*
            if (productToOrder == null) {
                System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
                return null;
            }
            products.add(productToOrder);*/
        }


        Order newOrder = new Order(UUID.randomUUID().toString(), products,OrderStatus.PROCESSING);

        return orderRepo.addOrder(newOrder);
    }
    //returns a list of all orders with a specific order status (parameter) using streams
    public List<Order> getAllOrdersWithOrderStatus(OrderStatus orderStatus){
        List<Order> orders=this.orderRepo.getOrders();
        return orders.stream().
                filter(order -> order.orderStatus().equals(orderStatus))
                .collect(Collectors.toList());
    }
}
