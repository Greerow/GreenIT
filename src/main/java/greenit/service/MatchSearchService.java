package greenit.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;

@Service
public class MatchSearchService {

    public String compareProduct(String query) {

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader("product_matching.csv"));

            reader.readLine(); // заголовок

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts.length < 5) {
                    continue;
                }

                String magnitName = parts[0];
                String ulybkaName = parts[2];

                if (magnitName.toLowerCase().contains(query.toLowerCase())
                        ||
                        ulybkaName.toLowerCase().contains(query.toLowerCase())) {

                    reader.close();

                    return
                            "Сравнение товара\n\n" +

                                    "Магнит:\n" +
                                    magnitName + "\n" +
                                    "Цена: " + parts[1] + " ₽\n\n" +

                                    "Улыбка Радуги:\n" +
                                    ulybkaName + "\n" +
                                    "Цена: " + parts[3] + " ₽\n\n" +

                                    "Совпадение: " +
                                    parts[4] + "%";
                }
            }

            reader.close();

            return "Товар не найден";

        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка чтения CSV";
        }
    }
}