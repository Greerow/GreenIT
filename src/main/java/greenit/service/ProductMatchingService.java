package greenit.service;

import greenit.GreenitApplication;
import greenit.model.Product;
import greenit.repository.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductMatchingService {

    public static void main(String[] args) {

        ConfigurableApplicationContext context =
                SpringApplication.run(GreenitApplication.class, args);

        ProductRepository productRepository =
                context.getBean(ProductRepository.class);

        List<Product> products = productRepository.findAll();

        for (int i = 0; i < products.size(); i++) {

            for (int j = i + 1; j < products.size(); j++) {

                Product product1 = products.get(i);
                Product product2 = products.get(j);

                int percent = calculateMatchPercent(
                        product1.getName(),
                        product2.getName()
                );

                if (percent >= 70) {
                    System.out.println("MATCH FOUND: " + percent + "%");
                    System.out.println("Товар 1: " + product1.getName());
                    System.out.println("Товар 2: " + product2.getName());
                    System.out.println("----------------------");
                }
            }
        }
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