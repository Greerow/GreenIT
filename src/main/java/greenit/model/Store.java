package greenit.model;

import jakarta.persistence.*;

@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // название магазина
    private String name;

    // адрес
    private String address;

    // город
    private String city;

    // тип магазина
    // например:
    // grocery
    // cosmetic
    private String storeType;

    public Store() {
    }

    public Store(Long id,
                 String name,
                 String address,
                 String city,
                 String storeType) {

        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.storeType = storeType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }
}