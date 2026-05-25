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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class MagnitCosmeticParser {

    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext context =
                SpringApplication.run(GreenitApplication.class, args);

        ProductRepository productRepository =
                context.getBean(ProductRepository.class);

        StoreRepository storeRepository =
                context.getBean(StoreRepository.class);

        PriceRepository priceRepository =
                context.getBean(PriceRepository.class);

        String url =
                "https://cosmetic.magnit.ru/webgate/v2/goods/search";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // создаем магазин ОДИН раз
        Store store = new Store();
        store.setName("Магнит Косметик");
        store.setCity("Севастополь");
        store.setStoreType("cosmetic");

        storeRepository.save(store);

        // pagination
        for (int offset = 0; offset <= 100; offset += 36) {

            String body = """
                    {
                      "catalogType": "3",
                      "includeAdultGoods": true,
                      "pagination": {
                        "offset": %d,
                        "limit": 36
                      },
                      "sort": {
                        "order": "desc",
                        "type": "popularity"
                      },
                      "storeCode": "942311",
                      "storeType": "cosmetic",
                      "term": ""
                    }
                    """.formatted(offset);

            HttpEntity<String> request =
                    new HttpEntity<>(body, headers);

            String response = restTemplate.postForObject(
                    url,
                    request,
                    String.class
            );

            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(response);

            JsonNode items = root.path("items");

            System.out.println("OFFSET: " + offset);

            for (JsonNode item : items) {

                String name = item.path("name").asText();

                int price = item.path("price").asInt();

                int oldPrice = item.path("promotion")
                        .path("oldPrice")
                        .asInt();

                System.out.println("Товар: " + name);
                System.out.println("Цена: " + price / 100.0);
                System.out.println("Старая цена: " + oldPrice / 100.0);
                System.out.println("-------------------");

                Product product = new Product();
                product.setName(name);
                product.setCategory("cosmetics");
                product.setNormalizedName(name.toLowerCase());

                productRepository.save(product);

                Price productPrice = new Price();
                productPrice.setPrice(price / 100.0);
                productPrice.setOldPrice(oldPrice / 100.0);
                productPrice.setStore(store);
                productPrice.setProduct(product);

                priceRepository.save(productPrice);
            }
        }
    }
}