package ensf607.propertysearchengine.user;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import ensf607.propertysearchengine.property.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table
public class User implements Serializable {
    
    @Id
    private String email;

    private String name;
    private String password;
    private String phone;

    
    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
        name = "favourite_properties",
        joinColumns = @JoinColumn(name = "user_email"),
        inverseJoinColumns = @JoinColumn(name = "property_mls")
    )
    private Set<Property> favourites = new HashSet<>();

    @OneToMany(mappedBy = "listingUser")
    private Set<Property> listings = new HashSet<>();

    public User() {
    }

    public User(String name, String email, String password, String phone) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String ucid) {
        this.email = ucid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void addFavourite(Property property) {
        favourites.add(property);
    }

    public void removeFavourite(Property property) {
        favourites.remove(property);
    }

    public Set<Property> getFavourites() {
        return favourites;
    }

    public Set<Property> getListings() {
        return listings;
    }
}
