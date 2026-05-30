package greenit.parser;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

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
        FileWriter writer = new FileWriter("magnit_cosmetic.csv");

        writer.write("productId,brand,name,price,oldPrice\n");
        Scanner scanner = new Scanner(new File("magnit_brands.csv"));
        scanner.nextLine();


//        ConfigurableApplicationContext context =
//                SpringApplication.run(GreenitApplication.class, args);
//
//        ProductRepository productRepository =
//                context.getBean(ProductRepository.class);
//
//        StoreRepository storeRepository =
//                context.getBean(StoreRepository.class);
//
//        PriceRepository priceRepository =
//                context.getBean(PriceRepository.class);

        String url =
                "https://cosmetic.magnit.ru/webgate/v2/goods/search";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("x-client-name", "cosmetic");
        headers.set("x-device-platform", "Web");
        headers.set("x-new-magnit", "true");
        headers.set("x-device-id", "a9f1406c-e6a9-4f31-81d2-88ed905d3d32");

        headers.set("Origin", "https://cosmetic.magnit.ru");
        headers.set("Referer", "https://cosmetic.magnit.ru/");
        headers.set("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/148.0.0.0 Safari/537.36");
//
//        // создаем магазин ОДИН раз
//        Store store = new Store();
//        store.setName("Магнит Косметик");
//        store.setCity("Севастополь");
//        store.setStoreType("cosmetic");

//        storeRepository.save(store);

        // pagination
        while (scanner.hasNextLine()) {
            String brand = scanner.nextLine().trim().replace("\"", "");
            System.out.println("Парсим бренд: " + brand);

            for (int offset = 0; offset < 1000; offset += 33) {

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
                      "storeCode": "930249",
                      "storeType": "cosmetic",
                      "term":"%s"
                    }
                    """.formatted(offset, brand);

            HttpEntity<String> request =
                    new HttpEntity<>(body, headers);
            System.out.println("Запрос offset = " + offset);


            String response = restTemplate.postForObject(
                    url,
                    request,
                    String.class
            );

            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(response);
//           System.out.println("TOTAL = " + root.path("pagination").path("totalCount").asInt());

            JsonNode items = root.path("items");

//            System.out.println("OFFSET: " + offset + ", ITEMS: " + items.size());
                    if (items.isEmpty()) {
                        System.out.println(("Каталог закончился"));
                        break;
                    }

            for (JsonNode item : items) {

                String productId = item.path("productId").asText();

                String name = item.path("name").asText();

                int price = item.path("price").asInt();

                int oldPrice = item.path("promotion")
                        .path("oldPrice")
                        .asInt();
                writer.write(
                        "\"" + productId + "\"," +
                                "\"" + brand + "\"," +
                                "\"" + name.replace("\"", "'") + "\"," +
                                (price / 100) + "," +
                                (oldPrice / 100) + "\n"
                );

//                System.out.println("Товар: " + name);
//                System.out.println("Цена: " + price / 100.0);
//                System.out.println("Старая цена: " + oldPrice / 100.0);
//                System.out.println("-------------------");

//                Product product = new Product();
//                product.setName(name);
//                product.setCategory("cosmetics");
//                product.setNormalizedName(name.toLowerCase());
//
////                productRepository.save(product);
//
//                Price productPrice = new Price();
//                productPrice.setPrice(price / 100.0);
//                productPrice.setOldPrice(oldPrice / 100.0);
//                productPrice.setStore(store);
//                productPrice.setProduct(product);

//                priceRepository.save(productPrice);
            }
            }

        }
        writer.close();
        System.out.println("CSV сохранен");
    }
}