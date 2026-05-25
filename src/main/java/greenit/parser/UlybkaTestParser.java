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

public class UlybkaTestParser {

    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext context =
                SpringApplication.run(GreenitApplication.class, args);

        ProductRepository productRepository =
                context.getBean(ProductRepository.class);

        StoreRepository storeRepository =
                context.getBean(StoreRepository.class);

        PriceRepository priceRepository =
                context.getBean(PriceRepository.class);

        // ВСТАВЬ СЮДА URL ТОВАРА
        String url = "https://www.r-ulybka.ru/_next/data/2xOd0c9B06rRiv1E0fVKM/catalog/goods/shampun-dlia-volos-elseve-gialuron-balans-400ml-3152509.json?slug=shampun-dlia-volos-elseve-gialuron-balans-400ml-3152509";
        RestTemplate restTemplate = new RestTemplate();

        String json = restTemplate.getForObject(
                url,
                String.class
        );

        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(json);

        JsonNode item = root
                .path("pageProps")
                .path("dehydratedState")
                .path("queries")
                .get(0)
                .path("state")
                .path("data");


        // магазин
        Store store = new Store();
        store.setName("Улыбка Радуги");
        store.setCity("Севастополь");
        store.setStoreType("cosmetic");

        storeRepository.save(store);

        // товар
        String name = item.path("name").asText();

        JsonNode prices = item.path("prices");

        if (prices.isArray() && prices.size() > 0) {

            JsonNode firstPrice = prices.get(0);

            int price = firstPrice.path("price").asInt();

            int salePrice = firstPrice.path("salePrice").asInt();

            int regularPrice = firstPrice.path("regularPrice").asInt();

            System.out.println("Товар: " + name);
            System.out.println("Цена: " + price);
            System.out.println("Цена со скидкой: " + salePrice);
            System.out.println("Старая цена: " + regularPrice);

            Product product = new Product();

            product.setName(name);
            product.setCategory("cosmetics");
            product.setNormalizedName(name.toLowerCase());

            productRepository.save(product);

            Price productPrice = new Price();

            if (salePrice > 0) {
                productPrice.setPrice((double)salePrice);
            } else {
                productPrice.setPrice((double)price);
            }

            productPrice.setOldPrice((double)regularPrice);

            productPrice.setStore(store);
            productPrice.setProduct(product);

            priceRepository.save(productPrice);

            System.out.println("ТОВАР СОХРАНЕН В POSTGRESQL");

        } else {
            System.out.println("ЦЕНЫ НЕТ");
        }
    }
}