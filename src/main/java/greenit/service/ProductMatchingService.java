package greenit.service;

import greenit.GreenitApplication;
import greenit.model.Product;
import greenit.repository.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import greenit.model.CsvProduct;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class ProductMatchingService {

    public static void main(String[] args) throws Exception {

//        ConfigurableApplicationContext context =
//                SpringApplication.run(GreenitApplication.class, args);
//
//        ProductRepository productRepository =
//                context.getBean(ProductRepository.class);
//
//        List<Product> products = productRepository.findAll();
//
//        for (int i = 0; i < products.size(); i++) {
//
//            for (int j = i + 1; j < products.size(); j++) {
//
//                Product product1 = products.get(i);
//                Product product2 = products.get(j);
//
//                int percent = calculateMatchPercent(
//                        product1.getName(),
//                        product2.getName()
//                );
//
//                if (percent >= 70) {
//                    System.out.println("MATCH FOUND: " + percent + "%");
//                    System.out.println("Товар 1: " + product1.getName());
//                    System.out.println("Товар 2: " + product2.getName());
//                    System.out.println("----------------------");
//                }
//            }
//        }
        List<CsvProduct> magnitProducts =
                readCsv("magnit_cosmetic.csv");

        List<CsvProduct> ulybkaProducts =
                readCsv("ulybka.csv");

        int matchCount = 0;
        for (CsvProduct magnit : magnitProducts) {

            for (CsvProduct ulybka : ulybkaProducts) {

                if (!magnit.getBrand()
                        .equalsIgnoreCase(ulybka.getBrand())) {
                    continue;
                }
                String shade1 = extractShade(magnit.getName());
                String shade2 = extractShade(ulybka.getName());

                if (!shade1.isEmpty()
                        && !shade2.isEmpty()
                        && !shade1.equals(shade2)) {
                    continue;
                }

                int percent = calculateMatchPercent(
                        magnit.getName(),
                        ulybka.getName()
                );

                if (percent >= 80) {
                    matchCount++;
                    double diffPercent = (ulybka.getPrice() - magnit.getPrice()) / magnit.getPrice() * 100;

                    System.out.println("MATCH FOUND: " + percent + "%");
                    System.out.println("BRAND: " + magnit.getBrand());
                    System.out.println("MAGNIT: " + magnit.getName());
                    System.out.println("ULYBKA: " + ulybka.getName());
                    System.out.println("MAGNIT PRICE: " + magnit.getPrice());
                    System.out.println("ULYBKA PRICE: " + ulybka.getPrice());
                    System.out.println("DIFF: " + String.format("%.2f", diffPercent) + "%");
                    System.out.println("----------------");
                }
            }
        }
        System.out.println();
        System.out.println("ВСЕГО МАТЧЕЙ: " + matchCount);
    }

    private static List<CsvProduct> readCsv(String fileName) throws Exception {

        List<CsvProduct> products = new ArrayList<>();

        BufferedReader reader =
                new BufferedReader(new FileReader(fileName));

        reader.readLine(); // пропускаем заголовок

        String line;

        while ((line = reader.readLine()) != null) {

            String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

            if (parts.length < 4) {
                continue;
            }

            String brand;
            String name;
            double price;
            double oldPrice;

            if (parts.length == 5) {

                // Магнит
                brand = parts[1].replace("\"", "");
                name = parts[2].replace("\"", "");
                price = Double.parseDouble(parts[3]);
                oldPrice = Double.parseDouble(parts[4]);

            } else {

                // Улыбка
                brand = parts[0].replace("\"", "");
                name = parts[1].replace("\"", "");
                price = Double.parseDouble(parts[2]);
                oldPrice = Double.parseDouble(parts[3]);
            }

            products.add(
                    new CsvProduct(
                            brand,
                            name,
                            price,
                            oldPrice
                    )
            );
        }

        reader.close();

        return products;
    }

    public static int calculateMatchPercent(String name1, String name2) {

        Set<String> words1 = normalizeToWords(name1);
        Set<String> words2 = normalizeToWords(name2);

        int sameWords = 0;

        for (String word : words1) {
            if (words2.contains(word)) {
                sameWords++;
            }
        }

        int minWords = Math.min(words1.size(), words2.size());

        if (minWords == 0) {
            return 0;
        }

        return sameWords * 100 / minWords;
    }

    private static String extractShade(String name) {

        String normalized = name
                .toLowerCase()
                .replace("тон", " тон ")
                .replaceAll("[^а-яa-z0-9 ]", " ")
                .replaceAll("\\s+", " ")
                .trim();

        String[] words = normalized.split(" ");

        for (int i = 0; i < words.length; i++) {

            if (words[i].equals("тон") && i + 1 < words.length) {
                return words[i + 1];
            }
        }

        return "";
    }

    private static Set<String> normalizeToWords(String name) {

        String normalized = name
                .toLowerCase()
                .replace("ё", "е")
                .replace("é", "e")
                .replace("'", "")
                .replace("\"", "")
                .replace("«", "")
                .replace("»", "")
                .replace("-", " ")
                .replaceAll("[^а-яa-z0-9 ]", " ")
                .replaceAll("\\s+", " ")
                .trim();

        return new HashSet<>(Arrays.asList(normalized.split(" ")));
    }
}