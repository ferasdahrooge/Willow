package ensf607.propertysearchengine.property;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ensf607.propertysearchengine.user.*;
import ensf607.propertysearchengine.pricehistory.*;
import ensf607.propertysearchengine.neighbourhood.*;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    @Autowired
    private NeighbourhoodRepository neighbourhoodRepository;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public String addNewProperty(Property property, String userEmail) {
        Optional<User> userByEmail = userRepository.findById(userEmail);
        if (!userByEmail.isPresent()) {
            throw new IllegalStateException("This user does not exist");
        }
        User user = userByEmail.get();

        property.setListingUser(user);
        
        Optional<Property> propertyByAddress = propertyRepository.findByAddress(property.getAddress());
        if (propertyByAddress.isPresent()) {
            throw new IllegalStateException("This address has already been listed!");
        }

        Optional<Neighbourhood> neighbourhood = neighbourhoodRepository.findById(property.getNeighbourhood().getId());
        if (!neighbourhood.isPresent()) {
            throw new IllegalStateException("The neighbourhood cannot be found!");
        }

        propertyRepository.save(property);

        PriceHistory newEntry = new PriceHistory(property);
        priceHistoryRepository.save(newEntry);

        return "Property has been added!";
    }

    public String addNewPropertyPostman(String address,
                                     String propertyType,
                                     int yearBuilt,
                                     int neighbourhoodID,
                                     double bathrooms,
                                     double bedrooms,
                                     int price) {

        Optional<User> userByEmail = userRepository.findById("ardit.baboci@gmail.com");
        if (!userByEmail.isPresent()) {
            throw new IllegalStateException("This user does not exist");
        }
        User user = userByEmail.get();

        Optional<Neighbourhood> neighbourhood = neighbourhoodRepository.findById(neighbourhoodID);
        if (!neighbourhood.isPresent()) {
            throw new IllegalStateException("The neighbourhood cannot be found!");
        }

        Property property = new Property(address, propertyType, yearBuilt, bedrooms, bathrooms, price, neighbourhood.get());

        Optional<Property> propertyByAddress = propertyRepository.findByAddress(property.getAddress());
        if (propertyByAddress.isPresent()) {
            throw new IllegalStateException("This address has already been listed!");
        }

        property.setListingUser(user);
        
        propertyRepository.save(property);

        PriceHistory newEntry = new PriceHistory(property);
        priceHistoryRepository.save(newEntry);

        return "Property has been added!";
    }

    public List<Property> viewAllListings() {
        return propertyRepository.findAll();
    }

    public List<Property> viewAllListingsSorted() {
        return propertyRepository.findAll(Sort.by(Sort.Direction.ASC, "Address"));
    }

    public String removeAListing(int mls, String userEmail) {

        Optional<Property> propertyByID = propertyRepository.findById(mls);
        if(!propertyByID.isPresent()) {
            throw new IllegalStateException("Requested property cannot be found!");
        }

        Property property = propertyByID.get();

        if(!property.getListingUserID().equals(userEmail)) {
            throw new IllegalStateException("This user did not create this listing!");
        }

        propertyRepository.delete(property);

        return "Requested listing has been deleted.";
        
    }


    public String updatePrice(int mls, int newPrice, String userEmail) {

        Optional<Property> propertyByID = propertyRepository.findById(mls);
        if(!propertyByID.isPresent()) {
            throw new IllegalStateException("Requested property cannot be found!");
        }

        Property property = propertyByID.get();

        if(!property.getListingUserID().equals(userEmail)) {
            throw new IllegalStateException("This user did not create this listing!");
        }

        property.setPrice(newPrice);
        propertyRepository.save(property);

        PriceHistory newEntry = new PriceHistory(property);
        priceHistoryRepository.save(newEntry);

        return "Price has been updated.";
    }

    public Optional<Property> viewListingByMLS(int mls) {
        return propertyRepository.findById(mls);
    }


}
