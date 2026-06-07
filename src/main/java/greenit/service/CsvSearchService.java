package greenit.service;

import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CsvSearchService {

    public String findProduct(String product) {

        StringBuilder result = new StringBuilder();

        result.append("🔎 Результаты поиска: ")
                .append(product)
                .append("\n\n");

        int magnitCount = searchMagnit(product, result);
        int ulybkaCount = searchUlybka(product, result);

        if (magnitCount == 0 && ulybkaCount == 0) {
            return "Товар не найден";
        }

        return result.toString();
    }
    public String compareProduct(String product) {

        String magnitResult = findFirstInMagnit(product);
        String ulybkaResult = findFirstInUlybka(product);

        if (magnitResult == null && ulybkaResult == null) {
            return "Товар не найден для сравнения";
        }

        StringBuilder result = new StringBuilder();

        result.append("Сравнение: ")
                .append(product)
                .append("\n\n");

        if (magnitResult != null) {
            result.append("Магнит Косметик:\n")
                    .append(magnitResult)
                    .append("\n\n");
        } else {
            result.append("Магнит Косметик: не найден\n\n");
        }

        if (ulybkaResult != null) {
            result.append("Улыбка Радуги:\n")
                    .append(ulybkaResult)
                    .append("\n\n");
        } else {
            result.append("Улыбка Радуги: не найден\n\n");
        }

        return result.toString();
    }
    private String findFirstInMagnit(String product) {

        try {
            List<String> lines = Files.readAllLines(Paths.get("magnit_cosmetic.csv"));

            String search = product.toLowerCase();

            for (String line : lines) {

                if (!line.toLowerCase().contains(search)) {
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length >= 5) {
                    String brand = parts[1];
                    String name = parts[2];
                    String price = parts[3];

                    return name + "\nЦена: " + price + " ₽\nБренд: " + brand;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    private String findFirstInUlybka(String product) {

        try {
            List<String> lines = Files.readAllLines(Paths.get("ulybka.csv"));

            String search = product.toLowerCase();

            for (String line : lines) {

                if (!line.toLowerCase().contains(search)) {
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length >= 4) {
                    String brand = parts[0];
                    String name = parts[1];
                    String price = parts[2];

                    return name + "\nЦена: " + price + " ₽\nБренд: " + brand;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private int searchMagnit(String product, StringBuilder result) {

        try {
            List<String> lines = Files.readAllLines(Paths.get("magnit_cosmetic.csv"));

            String search = product.toLowerCase();
            int count = 0;

            for (String line : lines) {

                if (!line.toLowerCase().contains(search)) {
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length >= 5) {

                    String brand = parts[1];
                    String name = parts[2];
                    String price = parts[3];

                    appendProduct(result, "Магнит Косметик", name, price, brand);

                    count++;

                    if (count >= 5) {
                        break;
                    }
                }
            }

            return count;

        } catch (Exception e) {
            e.printStackTrace();
            result.append("Ошибка чтения magnit_cosmetic.csv\n\n");
            return 0;
        }
    }

    private int searchUlybka(String product, StringBuilder result) {

        try {
            List<String> lines = Files.readAllLines(Paths.get("ulybka.csv"));

            String search = product.toLowerCase();
            int count = 0;

            for (String line : lines) {

                if (!line.toLowerCase().contains(search)) {
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length >= 4) {

                    String brand = parts[0];
                    String name = parts[1];
                    String price = parts[2];

                    appendProduct(result, "Улыбка Радуги", name, price, brand);

                    count++;

                    if (count >= 5) {
                        break;
                    }
                }
            }

            return count;

        } catch (Exception e) {
            e.printStackTrace();
            result.append("Ошибка чтения ulybka.csv\n\n");
            return 0;
        }
    }

    private void appendProduct(
            StringBuilder result,
            String storeName,
            String name,
            String price,
            String brand
    ) {
        result.append(name)
                .append("\n")
                .append("Цена: ")
                .append(price)
                .append(" ₽")
                .append("\n")
                .append("Бренд: ")
                .append(brand)
                .append("\n")
                .append("Магазин: ")
                .append(storeName)
                .append("\n\n");
    }

}