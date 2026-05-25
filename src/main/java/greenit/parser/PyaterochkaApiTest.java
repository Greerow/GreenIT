package greenit.parser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PyaterochkaApiTest {

    public static void main(String[] args) throws Exception {

        HttpClient client =
                HttpClient.newHttpClient();

        HttpRequest request =
                HttpRequest.newBuilder()

                        .uri(
                                URI.create(
                                        "https://5d.5ka.ru/api/catalog/v3/stores/34R5/search?mode=delivery&include_restrict=true&q=%D1%81%D1%8B%D1%80&limit=12"
                                )
                        )

                        .header(
                                "user-agent",
                                "Mozilla/5.0"
                        )

                        .header(
                                "origin",
                                "https://5ka.ru"
                        )

                        .header(
                                "referer",
                                "https://5ka.ru/"
                        )

                        .header(
                                "x-platform",
                                "webapp"
                        )

                        .header(
                                "x-tenant-id",
                                "TC5"
                        )

                        .header(
                                "x-device-id",
                                "91059d7a-4b58-467c-9452-ad93cb6055ca"
                        )

                        .header(
                                "x-app-version",
                                "0.1.1.dev"
                        )

                        .header(
                                "cookie",

                                "spid=1778660374295_2ab0da4752e4f1f91764e6acb41d4cdb_5a8uic8htotxbg9k; spjs=1778660374338_253e4e95_0135264c_d0259eca8bbc8e4ec93317b94c0c8789_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; spsc=1779041581419_63bce2713313c12bbf527b658b3cc803_bP14uuFXklGvWYnLTtWi7cWpr3aC2ca755-IWNzlrGzLv40dN4cSUuH4dtvIQKxTZ"

                        )

                        .header(
                                "x-authorization",

                                "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJFRlBVcWRLamZTVG9vR09POWRpLURKQk4zWkgyaUxlcE5yWTF3dXgyNHFvIn0.eyJleHAiOjE3NzkyODIzODEsImlhdCI6MTc3ODY3NzU4MSwiYXV0aF90aW1lIjoxNzc4Njc3NTgxLCJqdGkiOiIyMDk5NzE4MC0zOTE2LTQ3MGUtODVkNS1kNjlmNzViNGMxMzUiLCJpc3MiOiJodHRwczovL2lkLng1LnJ1L2F1dGgvcmVhbG1zL3Nzb3g1aWQiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiZjo2NmI5YWQ2MC00Y2I0LTRlZTEtYjlhMC0wNTI4ZmRlYWMyYjE6MTExMTAxNzg2IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoidGM1X3NpdGUiLCJzZXNzaW9uX3N0YXRlIjoiMjMyNzFmNTQtZTYwMC00OGIyLTkxNTAtZWM0YzlhYjRkNTM4IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIG9mZmxpbmVfYWNjZXNzIHByb2ZpbGUiLCJzaWQiOiIyMzI3MWY1NC1lNjAwLTQ4YjItOTE1MC1lYzRjOWFiNGQ1MzgiLCJzb3VyY2VfZGV0YWlsIjoibW9iaWxlIiwicGVybWlzc2lvbk1hcmtldGluZyI6ImZhbHNlIiwibmFtZSI6ItGB0LXRgNCz0LXQuSIsInByZWZlcnJlZF91c2VybmFtZSI6Ijc5OTExMTI4MDIyIiwiY2lwX2lkIjoiSURYLjExMTEwMTc4NiIsInN1YnNjcmlwdGlvbiI6ImZhbHNlIiwiZ2l2ZW5fbmFtZSI6ItGB0LXRgNCz0LXQuSIsIng1aWQiOiIxMTExMDE3ODYifQ.gNhPfNv8xZhSSFz9uLpIowIAykfHgtQo-QaEkc13sTUqfTnmX1TNGa-5GmcE50SNXToaJ-Z-W62_YKuteEcKB1cZ9f2UGpWYNCesZ3ZqVDRwb4-0UxR7hwYQgb__D1fTEb8g0z9VHVEz1MOCe1FVsjAzKK2YMra-ay4piXPLF75lDSfLMwiljtXxNdGHp1M971ZsC_CZqZmt0MWWTTTJZTSn_UsWZFp0DKuBclYHdLy3SALVA5aUyF638DlV-sLwSB8lWGFTpuCaEFCUU8rsxmCIX8HCcgvP7kj_Ja_V_dL_kArslLzchCC8djIAKn8R4cr_NnreZ-TRfhNyQH2lpg'"

                        )

                        .GET()

                        .build();


        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );


        System.out.println(
                "STATUS = "
                        + response.statusCode()
        );


        System.out.println(
                "\n===== HEADERS ====="
        );

        System.out.println(
                response.headers()
        );


        System.out.println(
                "\n===== BODY ====="
        );


        String body =
                response.body();


        System.out.println(
                body.substring(
                        0,
                        Math.min(
                                body.length(),
                                500
                        )
                )
        );

    }
}