package greenit.parser;

import java.io.FileWriter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class MagnitFiltersParser {

    public static void main(String[] args) throws Exception {

        FileWriter writer = new FileWriter("magnit_brands.csv");
        writer.write("brand\n");

        String url =
                "https://cosmetic.magnit.ru/webgate/v1/goods/filters";

        RestTemplate restTemplate =
                new RestTemplate();

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("x-client-name", "cosmetic");
        headers.set("x-device-platform", "Web");
        headers.set("x-new-magnit", "true");
        headers.set("x-device-id", "a9f1406c-e6a9-4f31-81d2-88ed905d3d32");

        headers.set("Origin", "https://cosmetic.magnit.ru");
        headers.set("Referer", "https://cosmetic.magnit.ru/");
        headers.set("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/148.0.0.0 Safari/537.36");

        String body = """
                {
                  "catalogType": "3",
                  "storeType": "cosmetic",
                  "includeAdultGoods": true,
                  "correctQuery": false,
                  "client": "",
                  "service": "",
                  "onlyAvailable": false,
                  "onlyDiscount": false,
                  "filters": [],
                  "storeCodes": ["930249"],
                  "categoryIDs": []
                }
                """;

        HttpEntity<String> request =
                new HttpEntity<>(body, headers);

        String response =
                restTemplate.postForObject(
                        url,
                        request,
                        String.class
                );

        ObjectMapper mapper =
                new ObjectMapper();

        JsonNode root =
                mapper.readTree(response);

        JsonNode filters =
                root.path("filters");

        for (JsonNode filter : filters) {

            String id =
                    filter.path("id").asText();

            if (id.equals("brands")) {

                JsonNode elements =
                        filter.path("elements");

                System.out.println(
                        "Брендов найдено: " + elements.size()
                );

                for (JsonNode element : elements) {

                    String name =
                            element.path("name").asText();

                    int count =
                            element.path("count").asInt();

                    System.out.println(name + " -> " + count);
                    writer.write(
                            "\"" + name.replace("\"", "'") + "\"\n"
                    );
                }
            }
        }
        writer.close();
        System.out.println("brands.csv сохранен");
    }
}