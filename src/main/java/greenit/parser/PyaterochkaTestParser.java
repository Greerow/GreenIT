package greenit.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class PyaterochkaTestParser {

    public static void main(String[] args) throws Exception {

        // сюда вставишь Request URL из DevTools
        String url = "https://5d.5ka.ru/api/catalog/v2/stores/35XY/categories/251C17048/products?mode=delivery&include_restrict=true&limit=12&offset=0";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers =
                new HttpHeaders();

        headers.set(
                "User-Agent",
                "Mozilla/5.0"
        );
        headers.set(
                "Origin",
                "https://5ka.ru"
        );

        headers.set(
                "Referer",
                "https://5ka.ru/"
        );

        headers.set(
                "Accept",
                "application/json"
        );

        headers.set(
                "X-App-Version",
                "0.1.1.dev"
        );

        headers.set(
                "X-Platform",
                "webapp"
        );

        headers.set(
                "X-Tenant-Id",
                "TC5"
        );

        headers.set(
                "X-Device-Id",
                "91059d7a-4b58-467c-9452-ad93cb6055ca"
        );

        HttpEntity<String> entity =
                new HttpEntity<>(headers);


        ResponseEntity<String> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        entity,
                        String.class
                );


        String json =
                response.getBody();

        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(json);

        JsonNode products = root.path("products");

        for (JsonNode item : products) {

            String name = item.path("name").asText();

            double regularPrice = item.path("prices")
                    .path("regular")
                    .asDouble();

            double discountPrice = item.path("prices")
                    .path("discount")
                    .asDouble();

            System.out.println("Товар: " + name);
            System.out.println("Обычная цена: " + regularPrice);
            System.out.println("Цена со скидкой: " + discountPrice);
            System.out.println("-------------------");
        }
    }
}