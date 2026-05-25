package greenit.parser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class MagnitParser {

    public static void main(String[] args) throws IOException, InterruptedException {

        String json = """
                {
                  "catalogType": "3",
                  "includeAdultGoods": true,
                  "pagination": {
                    "limit": 32,
                    "offset": 32
                  },
                  "sort": {
                    "order": "desc",
                    "type": "popularity"
                  },
                  "storeCode": "992301",
                  "storeType": "dostavka"
                }
                """;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://magnit.ru/webgate/v2/goods/search"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        System.out.println(response.body());

        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(response.body());

        JsonNode items = root.path("items");

        for (JsonNode item : items) {

            String name = item.get("name").asText();

            int price = item.get("price").asInt();

            String productId = item.get("productId").asText();

            System.out.println("Товар: " + name);

            System.out.println("Цена: " + (price / 100.0));

            System.out.println("ID: " + productId);

            System.out.println("------------------");
            System.out.println(items.size());
        }
    }
}
