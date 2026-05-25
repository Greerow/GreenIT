package greenit.parser;

import com.microsoft.playwright.*;

public class CooperPlaywrightParser {

    public static void main(String[] args) {

        try (Playwright playwright =
                     Playwright.create()) {

            Browser browser =
                    playwright.chromium().launch(
                            new BrowserType.LaunchOptions()
                                    .setHeadless(false)
                    );

            BrowserContext context =
                    browser.newContext();

            Page page =
                    context.newPage();

            // открываем Купер
            page.navigate(
                    "https://kuper.ru"
            );

            page.waitForTimeout(
                    50000 
            );


            String response =
                    page.evaluate("""
                        async () => {

                          const res =
                          await fetch(
                          'https://kuper.ru/api/web/v1/products',
                          {

                            method:'POST',

                            headers:{
                               'Content-Type':
                               'application/json'
                            },

                            body:
                            JSON.stringify({

                                store_id:"239",

                                page:"1",

                                per_page:"100",

                                tenant_id:
                                "sbermarket",

                                q:"сыр"

                            })

                          });

                          return await res.text();

                        }
                    """).toString();


            System.out.println(
                    response
            );


            browser.close();

        }

    }

}