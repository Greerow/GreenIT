package greenit.parser;

import com.microsoft.playwright.*;

public class PyaterochkaPlaywrightParser {

    public static void main(String[] args) {

        try (Playwright playwright =
                     Playwright.create()) {

            Browser browser =
                    playwright.chromium()
                            .launch(
                                    new BrowserType.LaunchOptions()
                                            .setHeadless(false)
                            );

            BrowserContext context =
                    browser.newContext();

            Page page =
                    context.newPage();


            // Слушаем все ответы
            page.onResponse(response -> {

                String url =
                        response.url();

                // Ищем API поиска товаров
                if (
                        url.contains("5d.5ka.ru")
                                &&
                                url.contains("/search")
                ) {

                    System.out.println(
                            "\n===== FOUND API ====="
                    );

                    System.out.println(
                            "STATUS: "
                                    +
                                    response.status()
                    );

                    System.out.println(
                            "URL: "
                                    +
                                    url
                    );


                    try {

                        String json =
                                response.text();

                        System.out.println(
                                "\n===== JSON ====="
                        );

                        System.out.println(
                                json
                        );

                    }

                    catch (Exception e) {

                        System.out.println(
                                "Ошибка чтения JSON"
                        );

                    }

                }

            });


            // Открываем поиск
            page.navigate(
                    "https://5ka.ru/search?query=сыр"
            );


            // ждём запросы
            page.waitForTimeout(
                    30000
            );


            browser.close();

        }

    }

}