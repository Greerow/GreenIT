import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class AuchanParser {

    public static void main(String[] args) {

        String url =
                "https://www.auchan.ru/v3/search/?query=%D1%81%D1%8B%D1%80&merchant_id=3&channel=W";


        RestTemplate rest =
                new RestTemplate();


        HttpHeaders headers =
                new HttpHeaders();

        headers.set(
                "User-Agent",
                "Mozilla/5.0"
        );

        headers.set(
                "Origin",
                "https://www.auchan.ru"
        );

        headers.set(
                "Referer",
                "https://www.auchan.ru/"
        );

        headers.setContentType(
                MediaType.APPLICATION_JSON
        );


        String body =
                """
                {
                  "sort":"default",
                  "filters":{},
                  "pagination":{
                     "limit":40,
                     "offset":0
                  }
                }
                """;


        HttpEntity<String> request =
                new HttpEntity<>(
                        body,
                        headers
                );


        ResponseEntity<String> response =
                rest.exchange(
                        url,
                        HttpMethod.POST,
                        request,
                        String.class
                );


        System.out.println(
                response.getBody()
        );

    }

}