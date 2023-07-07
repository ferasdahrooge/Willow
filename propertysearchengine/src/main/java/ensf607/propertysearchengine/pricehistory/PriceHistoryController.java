package ensf607.propertysearchengine.pricehistory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/v1/pricehistory")
public class PriceHistoryController {

    private final PriceHistoryService priceHistoryService;

    @Autowired
    public PriceHistoryController(PriceHistoryService priceHistoryService) {
        this.priceHistoryService = priceHistoryService;
    }

    @GetMapping("/viewpricehistory")
    public List<PriceHistory> viewPriceHistory(@RequestParam int mls) {
        return priceHistoryService.viewPriceHistory(mls);
    }

}

