package greenit.parser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PyaterochkaParser {

    public static void main(String[] args) throws IOException, InterruptedException {

        String url = "https://5d.5ka.ru/api/catalog/v3/stores/35XY/search?mode=delivery&include_restrict=true&q=col&limit=12";

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/124.0.0.0 Safari/537.36")
                .header("Referer", "https://5ka.ru/")
                .header("Origin", "https://5ka.ru")
                .header("X-Requested-With", "XMLHttpRequest")
                .header("Cookie", "spid=1778660374295_2ab0da4752e4f1f91764e6acb41d4cdb_5a8uic8htotxbg9k")
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        System.out.println("STATUS: " + response.statusCode());
        System.out.println("URL: " + url);

        System.out.println(response.body());
    }
}