package greenit.controller;

import greenit.model.Price;
import greenit.repository.PriceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PriceController {

    private final PriceRepository priceRepository;

    public PriceController(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @GetMapping("/prices")
    public List<Price> getPrices() {
        return priceRepository.findAll();
    }

    @PostMapping("/prices")
    public Price createPrice(@RequestBody Price price) {
        return priceRepository.save(price);
    }
}