package greenit.model;

public class CsvProduct {

    private String brand;
    private String name;
    private double price;
    private double oldPrice;

    public CsvProduct(String brand, String name,
                      double price, double oldPrice) {
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.oldPrice = oldPrice;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getOldPrice() {
        return oldPrice;
    }
}