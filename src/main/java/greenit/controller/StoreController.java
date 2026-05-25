package greenit.controller;

import greenit.model.Store;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import greenit.repository.StoreRepository;

import java.util.List;

@RestController
public class StoreController {

    private final StoreRepository storeRepository;

    public StoreController(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @GetMapping("/stores")
    public List<Store> getStores() {

        return storeRepository.findAll();
    }

    @PostMapping("/stores")
    public Store createStore(@RequestBody Store store) {
        return storeRepository.save(store);
    }

    @GetMapping("/test-store")
    public Store testStore() {
        return new Store(4L, "Фреш", "ул Ленина 10", "Севастополь", "grocery");
    }
}