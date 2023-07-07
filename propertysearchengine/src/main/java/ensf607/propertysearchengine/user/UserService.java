package ensf607.propertysearchengine.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ensf607.propertysearchengine.property.PropertyRepository;
import ensf607.propertysearchengine.property.*;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PropertyRepository propertyRepository;


    public Optional<User> getUserByEmail(String email) {
        return userRepository.findById(email);
    }

    public String addFavourite(String userEmail, Integer propertyMLS) {
        Optional<User> userByEmail = getUserByEmail(userEmail);
        if (!userByEmail.isPresent()) {
            throw new IllegalStateException("This user does not exist");
        }
        User user = userByEmail.get();

        Optional<Property> propertyById = propertyRepository.findById(propertyMLS);
        if (!propertyById.isPresent()) {
            throw new IllegalStateException("This property does not exist");
        }
        Property property = propertyById.get();

        user.addFavourite(property);
        userRepository.save(user);

        return "MLS " + propertyMLS + " has been added to your favourites!";
    }


    public String removeFavourite(String userEmail, Integer propertyMLS) {
        Optional<User> userByEmail = getUserByEmail(userEmail);
        if (!userByEmail.isPresent()) {
            throw new IllegalStateException("This user does not exist");
        }
        User user = userByEmail.get();

        Optional<Property> propertyById = propertyRepository.findById(propertyMLS);
        if (!propertyById.isPresent()) {
            throw new IllegalStateException("This property does not exist");
        }
        Property property = propertyById.get();

        user.removeFavourite(property);
        userRepository.save(user);

        return "MLS " + propertyMLS + " has been removed from your favourites!";
    }

    public Set<Property> getUsersFavourite (String userEmail){
        Optional<User> userByEmail = getUserByEmail(userEmail);
        User user = userByEmail.get();
        return user.getFavourites();
    }

    public Set<Property> getUsersListings (String userEmail){
        Optional<User> userByEmail = getUserByEmail(userEmail);
        User user = userByEmail.get();
        return user.getListings();
    }
}
