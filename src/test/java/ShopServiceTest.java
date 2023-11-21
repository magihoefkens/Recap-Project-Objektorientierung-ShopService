import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        Product product=new Product("P1","Produkt1");
        ProductRepo productRepo=new ProductRepo();
        productRepo.addProduct(product);
        Order newOrder1 = new Order("1", List.of(product),OrderStatus.PROCESSING);
        OrderMapRepo repo=new OrderMapRepo();
        repo.addOrder(newOrder1);
        ShopService shopService = ShopService.builder()
                .productRepo(productRepo)
                .orderRepo(repo)
                .build();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")),OrderStatus.PROCESSING);
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }
    @Test
    void getAllOrdersWhenOrderStatusPROCESSING(){
        //GIVEN

        Product product = new Product("1", "Apfel");
        Order newOrder1 = new Order("1", List.of(product),OrderStatus.PROCESSING);
        Order newOrder2 = new Order("2", List.of(product),OrderStatus.PROCESSING);
        OrderMapRepo repo = new OrderMapRepo();
        ProductRepo productRepo=new ProductRepo();
        productRepo.addProduct(product);

        repo.addOrder(newOrder1);
        repo.addOrder(newOrder2);
        ShopService shopService=ShopService.builder()
                .productRepo(productRepo)
                .orderRepo(repo)
                .build();
        //WHEN
        List<Order>actual=shopService.getAllOrdersWithOrderStatus(OrderStatus.PROCESSING);
        //THEN
        List<Order> expected=List.of(newOrder1,newOrder2);
        assertEquals(expected,actual);
    }

   /* @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        //ShopService shopService = ShopService.builder().build();
        Product product=new Product("P1","Produkt1");
        ProductRepo productRepo=new ProductRepo();
        productRepo.addProduct(product);
        Order newOrder1 = new Order("1", List.of(product),OrderStatus.PROCESSING);
        OrderMapRepo repo=new OrderMapRepo();
        repo.addOrder(newOrder1);
        ShopService shopService = ShopService.builder()
                .productRepo(productRepo)
                .orderRepo(repo)
                .build();
        List<String> productsIds = List.of("T1", "T2");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNull(actual);
    }*/
}
