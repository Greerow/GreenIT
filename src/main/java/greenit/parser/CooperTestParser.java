package greenit.parser;

import okhttp3.MediaType;
import okhttp3.*;
import java.util.Scanner;

public class CooperTestParser {

    public static void main(String[] args) throws Exception {

        Scanner scanner =
                new Scanner(System.in);

        System.out.println(
                "Что ищем?"
        );

        String search =
                scanner.nextLine();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\"store_id\":\"238\",\"page\":\"1\",\"per_page\":\"24\",\"tenant_id\":\"sbermarket\",\"filter\":[{\"key\":\"permalinks\",\"values\":[]},{\"key\":\"brand\",\"values\":[]},{\"key\":\"discounted\",\"values\":[]}],\"q\":\"" + search + "\",\"ads_identity\":{\"ads_promo_identity\":{\"site_uid\":\"c9qep2jupf8ugo3scn10\",\"placement_uid\":\"cg4tmrigsvdveog2p240\"}}}", mediaType);
        Request request = new Request.Builder()
                .url("https://kuper.ru/api/web/v1/products")
                .method("POST", body)
                .addHeader("accept", "application/json, text/plain, */*")
                .addHeader("accept-language", "ru,en-US;q=0.9,en;q=0.8,uk;q=0.7")
                .addHeader("anonymous_id", "1ab1de23-51d7-4f29-bc30-f858c61a0619")
                .addHeader("baggage", "sentry-environment=client,sentry-release=r26-05-19-2220-a427955e,sentry-public_key=f9d0a0afb8d5420bb353a190580ae049,sentry-trace_id=9b30706279f74d789bd78307cfd6ff48,sentry-transaction=%2Fmultisearch,sentry-sampled=false,sentry-sample_rand=0.296003370348487,sentry-sample_rate=0.1")
                .addHeader("client-id", "SbermarketPlatformWeb")
                .addHeader("client-token", "7ba97b6f4049436dab90c789f946ee2f")
                .addHeader("content-type", "application/json")
                .addHeader("origin", "https://kuper.ru")
                .addHeader("priority", "u=1, i")
                .addHeader("referer", "https://kuper.ru/multisearch?q=%D1%81%D1%8B%D1%80%20%D0%BB%D0%B0%D0%BC%D0%B1%D0%B5%D1%80&shippingMethod=by_courier&sid=1&vertical=all")
                .addHeader("sbm-forward-tenant", "sbermarket")
                .addHeader("sec-ch-ua", "\"Chromium\";v=\"148\", \"Google Chrome\";v=\"148\", \"Not/A)Brand\";v=\"99\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "empty")
                .addHeader("sec-fetch-mode", "cors")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sentry-trace", "9b30706279f74d789bd78307cfd6ff48-8a7de8000790746b-0")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/148.0.0.0 Safari/537.36")
                .addHeader("x-csrf-token", "G3+zthBSmpxNurEjrZQ4KYt2NlulvpF9wXA0E1gM0XDOC6X0j+i1OZ8U91UduD72jFR0riH9GGVXqzkBt8a4pA==")
                .addHeader("Cookie", "spid=1779477213192_b6cb33a075f9e588a2cc4bc371b72546_bd56pqmc67ead6rl; external_analytics_anonymous_id=1ab1de23-51d7-4f29-bc30-f858c61a0619; _pk_ref.6.ef9f=%5B%22%22%2C%22%22%2C1779477213%2C%22https%3A%2F%2Fwww.google.com%2F%22%5D; _pk_id.6.ef9f=101591c9475c3ce8.1779477213.; _pk_ses.6.ef9f=1; adtech_uid=d2d773b0-384d-4af7-a0e7-dbd627359c3a%3Akuper.ru; top100_id=t1.7588506.1915734970.1779477213775; iap.uid=04cb70935f7b408981ed7f72542fc456; tmr_lvid=c6ca3d8411a2a544392683651dae4bde; tmr_lvidTS=1779477213877; _ym_uid=1779477214113081109; _ym_d=1779477214; adrdel=1779477214225; adrcid=AiHEudJGfrguxkiKHDL2Tdg; _ym_isad=2; acs_3=%7B%22hash%22%3A%221aa3f9523ee6c2690cb34fc702d4143056487c0d%22%2C%22nst%22%3A1779563614402%2C%22sl%22%3A%7B%22224%22%3A1779477214402%2C%221228%22%3A1779477214402%7D%7D; rl_page_init_referrer=RS_ENC_v3_Imh0dHBzOi8vd3d3Lmdvb2dsZS5jb20vIg%3D%3D; rl_page_init_referring_domain=RS_ENC_v3_Ind3dy5nb29nbGUuY29tIg%3D%3D; rl_anonymous_id=RS_ENC_v3_IjFhYjFkZTIzLTUxZDctNGYyOS1iYzMwLWY4NThjNjFhMDYxOSI%3D; popmechanic_sbjs_migrations=popmechanic_1418474375998%3D1%7C%7C%7C1471519752600%3D1%7C%7C%7C1471519752605%3D1; uxs_uid=557929c0-5612-11f1-9f99-d13a685dd240; _sv=SV1.2d414af7-79f2-4834-8fac-e67c1db09032.1769788672; domain_sid=V-9TSTHIp7II0FYkEEVPV%3A1779477217226; sessionId=17794795602211060510; _sas.10bbed01ae3e509a5ddcae8d91c39ce909dc1b7528f75273f7c53559c846b098=SV1.2d414af7-79f2-4834-8fac-e67c1db09032.1769788672.1779479560; spjs=1779480170461_010a030a_013526a2_b6ea0788e79b3d479a5987749ddca08e_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; spsc=1779480170461_07cc791a542540c0b74c9c12ce195ef9_EimRifSPZPtzb8-Qa4Nx9W6rzQEWVZ10MH1K.YGDmA3t0u9x0YNwtvlPPOz051LaZ; rndcaptcha=eyJpdiI6IkxTMktkWkx3cDZ0aVZPNjQreElOTWc9PSIsInZhbHVlIjoid3ZkWWFJSU1mWjdJTVVLZWUyYmZtcGtxbDZQMlhqQmFoVU44SmwrMlJNOEpvVjUxOE82eFV5ekJia2FONitNMmcrSFlPNlJ5enhRdDVXd2JOemtCVHN4bm5xVVpWYk90dHpIMk9ONXZzZFU9IiwibWFjIjoiZjUyMjk5OWE3NThiMTU1Y2ZkODZlNmE5NDI2NDFkNTQ1YTBjMzI4ZDkxOTkwZmMyNDBiN2IwZTMxZDY3NmZiZiJ9; spca=1779480170461_aeb40ec4d1a7efb7073ff0ee8eeb6fe6_EimRifSPZPtzb8-Qa4Nx9W6rzQEWVZ10MH1K.YGDmA3t0u9x0YNwtvlPPOz051LaZ_Wod2UvXMCW4a5ujGn6YaXg; spcajs=invalidated; mobile-web-supernova=false; OnboardingState={%22state%22:{%22viewedOnboardingKeys%22:[]}%2C%22version%22:0}; mindboxDeviceUUID=db3a8a5d-cdbc-400c-9641-824c0fa0c308; directCrm-session=%7B%22deviceGuid%22%3A%22db3a8a5d-cdbc-400c-9641-824c0fa0c308%22%7D; tmr_detect=0%7C1779480194087; cookies_consented=yes; t3_sid_7588506=s1.1680344175.1779477213781.1779480205383.1.39.8.1..; ssr-breakpoint=sm; rl_trait=RS_ENC_v3_eyJzZXNzaW9uSWQiOiIxNzc5NDc5NTYwMjIxMTA2MDUxMCIsImV2ZW50VGVuYW50Ijoic2Jlcm1hcmtldCIsIm9wZW5Nb2RlIjoid2ViIiwiZW1iZWRkaW5nUGxhdGZvcm0iOiJ3ZWIiLCJzaGlwcGluZ01ldGhvZEtpbmQiOiJieV9jb3VyaWVyIiwib3JkZXJJZCI6IlIxNzg0NDkwOTYifQ%3D%3D; identified_address=true; rl_session=RS_ENC_v3_eyJhdXRvVHJhY2siOnRydWUsInRpbWVvdXQiOjE4MDAwMDAsImV4cGlyZXNBdCI6MTc3OTQ4MjA0NDE1OSwiaWQiOjE3Nzk0Nzk1NjAyNDAsInNlc3Npb25TdGFydCI6ZmFsc2V9; _808db7ba1248=%5B%7B%22source%22%3A%22www.google.com%22%2C%22medium%22%3A%22referral%22%2C%22cookie_changed_at%22%3A1779479779%7D%2C%7B%22source%22%3A%22kuper.ru%22%2C%22medium%22%3A%22referral%22%2C%22cookie_changed_at%22%3A1779480247%7D%5D; _Instamart_session=OE01YU93bVVNTHpkdzZ6K2NKNno5bStWZk1FMDRUSG1VYXMyYkxBYU9UNm1FbmhORlZRTWxKbkJqRkZXdVljM1ZTSGZWRUsvOGZ3MW1HMWdLY1F6STZvRkErM0N1UDlqWDhUSDJlK2ZTRVY5amdYRnVieHQyTmlzdEIya2N6ODFKN0JUZjIrQWMreWoyZld5OW1UVEdhSWtqcHhaUjM5b3FIZVI3TjRCZFNPRi9KKzVIQXcrMndQT1YybzFYZVIwMTN2MVFEdU5sMlhxdkdVdU5RaWQ2eEJuZWJKRDlRRmE1NklUdHNaRUMyd3oyNVZKSGhsb2E0V3BzRFVPdGQ4WTFmVERwU0ZHcnN5QlgxajRxY2hUN2k0Q3F2cDZmcFhia01mWEhjK3FOVFBsTC9qZ3BEMitYd3grT20zWUptYmktLTZiZ3N2aXJKVkM1Sy9ZTmowQ0ZuRlE9PQ%3D%3D--ec432d6d0f19f16258df090d3a87f54ad6431779; _808db7ba1248=%5B%7B%22source%22%3A%22www.r-ulybka.ru%22%2C%22medium%22%3A%22referral%22%2C%22cookie_changed_at%22%3A1779478447%7D%5D; external_analytics_anonymous_id=e43616ba-6db0-4c08-96ea-f3c212b06189; spid=1779478446964_cc869f04b3dc24726d1755bf7b051bf7_5k9jc85f89m3nrja; spsc=1779478827875_a7bf65c07183f2859ff4878951bfe5e4_P463gRFwGCqyyCLNeAIsoj4zsMVpcKZi5AnRY8THWPsZ")
                .build();
        Response response =
                client.newCall(request)
                        .execute();

        System.out.println(response.code());
        System.out.println(response.body().string());
    }

}