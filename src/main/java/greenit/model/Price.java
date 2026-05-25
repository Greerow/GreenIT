package greenit.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // цена со скидкой / текущая
    private Double price;

    // старая цена
    private Double oldPrice;

    // ссылка на товар
    private String productUrl;

    // когда обновили цену
    private LocalDateTime parsedAt;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Store store;

    public Price() {
    }

    public Price(Long id,
                 Double price,
                 Double oldPrice,
                 String productUrl,
                 LocalDateTime parsedAt,
                 Product product,
                 Store store) {

        this.id = id;
        this.price = price;
        this.oldPrice = oldPrice;
        this.productUrl = productUrl;
        this.parsedAt = parsedAt;
        this.product = product;
        this.store = store;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public LocalDateTime getParsedAt() {
        return parsedAt;
    }

    public void setParsedAt(LocalDateTime parsedAt) {
        this.parsedAt = parsedAt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}