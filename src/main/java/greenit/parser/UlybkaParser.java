package greenit.parser;

import java.io.FileWriter;
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

public class UlybkaParser {

    public static void main(String[] args) throws Exception {
        FileWriter writer = new FileWriter("ulybka.csv");

        writer.write("brand,name,price,oldPrice\n");

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

        // создаем магазин ОДИН раз
//        Store store = new Store();
//        store.setName("Улыбка Радуги");
//        store.setCity("Севастополь");
//        store.setStoreType("cosmetic");

//        storeRepository.save(store);

        RestTemplate restTemplate = new RestTemplate();

        ObjectMapper mapper = new ObjectMapper();

        // pages
        for (int page = 1; page <= 64; page++) {

            String url =
                    "https://bff.r-ulybka.ru/api/products?page="
                            + page +
                            "&limit=100&fields[]=id&fields[]=name&fields[]=slug&fields[]=images&fields[]=active&fields[]=article&fields[]=stockStatus&fields[]=label&fields[]=brand&fields[]=ratio&fields[]=description&fields[]=promo&fields[]=new&fields[]=stockReceivedAt&fields[]=pricelistCode&fields[]=storages&fields[]=weight&fields[]=volume&fields[]=prices&fields[]=category&fields[]=rating&sortBy=onlyStore+asc,+stockStatus+desc,+marginality+desc&stockStatus=1&withStockQuantity=false&pricelist[]=8000&storages[]=SPb";

            String json = restTemplate.getForObject(url, String.class);

            JsonNode root = mapper.readTree(json);


            JsonNode items = root.path("_embedded").path("items");

            System.out.println("PAGE: " + page);

            for (JsonNode item : items) {

                String name = item.path("name").asText();

                JsonNode prices = item.path("prices");

//                System.out.println("Товар: " + name);

                if (prices.isArray() && prices.size() > 0) {

                    JsonNode firstPrice = prices.get(0);

                    String brand = item.path("brand")
                                    .path("name")
                                    .asText();
                    int price = firstPrice.path("price").asInt();

                    int salePrice = firstPrice.path("salePrice").asInt();

                    int regularPrice = firstPrice.path("regularPrice").asInt();
                    double finalPrice;

                    if (salePrice > 0) {
                        finalPrice = salePrice;
                    } else {
                        finalPrice = price;
                    }

                    writer.write(
                            "\"" + brand + "\"," +
                                    "\"" + name.replace("\"", "'") + "\"," +
                                    finalPrice + "," +
                                    regularPrice +
                                    "\n"
                    );

//                    System.out.println("Цена: " + price);
//                    System.out.println("Цена со скидкой: " + salePrice);
//                    System.out.println("Старая цена: " + regularPrice);
//
//                    Product product = new Product();
//                    product.setName(name);
//                    product.setCategory("cosmetics");
//                    product.setNormalizedName(name.toLowerCase());
//
////                    productRepository.save(product);
//
//                    Price productPrice = new Price();

                    // если salePrice есть — берем его
//                    if (salePrice > 0) {
//                        productPrice.setPrice(salePrice / 100.0);
//                    } else {
//                        productPrice.setPrice(price / 100.0);
//                    }
//
//                    productPrice.setOldPrice(regularPrice / 100.0);
//
//                    productPrice.setStore(store);
//                    productPrice.setProduct(product);

//                    priceRepository.save(productPrice);

                } else {
//                    System.out.println("Цены нет");
                }

//                System.out.println("------------------");
            }
        }
        writer.close();
        System.out.println("CSV сохранен");
    }
}
