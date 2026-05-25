package greenit.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import greenit.GreenitApplication;
import greenit.model.Price;
import greenit.model.Product;
import greenit.model.Store;
import greenit.repository.PriceRepository;
import greenit.repository.ProductRepository;
import greenit.repository.StoreRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.client.RestTemplate;

public class MagnitTestParser {

    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext context =
                SpringApplication.run(GreenitApplication.class, args);

        ProductRepository productRepository =
                context.getBean(ProductRepository.class);

        StoreRepository storeRepository =
                context.getBean(StoreRepository.class);

        PriceRepository priceRepository =
                context.getBean(PriceRepository.class);

        // ВСТАВИ СЮДА URL ТОВАРА
        String url = "https://cosmetic.magnit.ru/webgate/v2/goods/1000529247/stores/942311?storetype=cosmetic&catalogtype=3";

        RestTemplate restTemplate = new RestTemplate();

        String response = restTemplate.getForObject(
                url,
                String.class
        );

        ObjectMapper mapper = new ObjectMapper();

        JsonNode item = mapper.readTree(response);

        // магазин
        Store store = new Store();
        store.setName("Магнит Косметик");
        store.setCity("Севастополь");
        store.setStoreType("cosmetic");

        storeRepository.save(store);

        // товар
        String name = item.path("name").asText();

        int price = item.path("price").asInt();

        int oldPrice = item.path("promotion")
                .path("oldPrice")
                .asInt();

        System.out.println("Товар: " + name);
        System.out.println("Цена: " + price / 100.0);
        System.out.println("Старая цена: " + oldPrice / 100.0);

        // product
        Product product = new Product();

        product.setName(name);
        product.setCategory("cosmetics");
        product.setNormalizedName(name.toLowerCase());

        productRepository.save(product);

        // price
        Price productPrice = new Price();

        productPrice.setPrice(price / 100.0);
        productPrice.setOldPrice(oldPrice / 100.0);

        productPrice.setStore(store);
        productPrice.setProduct(product);

        priceRepository.save(productPrice);

        System.out.println("ТОВАР СОХРАНЕН В POSTGRESQL");
    }
}