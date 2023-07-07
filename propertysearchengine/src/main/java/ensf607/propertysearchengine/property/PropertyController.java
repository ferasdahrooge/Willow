package ensf607.propertysearchengine.property;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/v1/property")
public class PropertyController {

    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }


    @GetMapping("/viewalllistings")
    public List<Property> viewAllListings() {
       return propertyService.viewAllListings();
    }

    @GetMapping("/viewalllistingssorted")
    public List<Property> viewAllListingsSorted() {
       return propertyService.viewAllListingsSorted();
    }

    @GetMapping("/viewalisting")
    public Property viewAListing(@RequestParam int mls) {
        Optional<Property> propertyByID = propertyService.viewListingByMLS(mls);
        
        if(!propertyByID.isPresent()) {
            throw new IllegalStateException("Requested property cannot be found!");
        }
        else {
            return propertyByID.get();
        }
    }

    @GetMapping("/remove")
    public String removeAListing(@RequestParam int mls, Principal principal) {
        String userEmail = principal.getName();
        return propertyService.removeAListing(mls, userEmail);
    }

    @GetMapping("/remove_postman")
    public String removeAListing(@RequestParam int mls) {
        String userEmail = "ardit.baboci@gmail.com";
        return propertyService.removeAListing(mls, userEmail);
    }

    @GetMapping("/update")
    public String updatePrice(@RequestParam int mls, @RequestParam int newprice, Principal principal) {
        String userEmail = principal.getName();
        return propertyService.updatePrice(mls, newprice, userEmail);
    }

    @GetMapping("/update_price_postman")
    public String updatePricePostman(@RequestParam int mls, @RequestParam int newprice) {
        String userEmail = "ardit.baboci@gmail.com";
        return propertyService.updatePrice(mls, newprice, userEmail);
    }

    @PostMapping("/add_property")
    public String registerNewProperty(Property property, Principal principal) {
        String userEmail = principal.getName();
        return propertyService.addNewProperty(property, userEmail);
    }

    @PostMapping("/add_property_postman")
    public String registerNewPropertyPostman(@RequestBody Map<String, String> json) {
        return propertyService.addNewPropertyPostman(json.get("address"), json.get("propertyType"), Integer.parseInt(json.get("yearBuilt")), Integer.parseInt(json.get("neighbourhood")), Double.parseDouble(json.get("bathrooms")), Double.parseDouble(json.get("bedrooms")), Integer.parseInt(json.get("price")));
    }



}

