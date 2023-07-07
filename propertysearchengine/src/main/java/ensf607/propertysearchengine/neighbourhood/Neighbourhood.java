package ensf607.propertysearchengine.neighbourhood;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ensf607.propertysearchengine.property.*;

import ensf607.propertysearchengine.amenity.Amenity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



@Entity
@Table
public class Neighbourhood implements Serializable {
    
    @Id
    @SequenceGenerator(
            name = "id_sequence",
            sequenceName = "id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "id_sequence"
    )
    private int id;
    private int avgPropertyPrice;
    private int bikeScore;
    private int walkScore;

    @ManyToMany
    @JoinTable(
        name = "neighbourhood_amenities",
        joinColumns = @JoinColumn(name = "neighbourhood_id"),
        inverseJoinColumns = @JoinColumn(name = "amenity_amenity")
    )
    private Set<Amenity> amenities = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "neighbourhood")
    private Set<Property> listings = new HashSet<>();

    public Neighbourhood () {
    }

    public Neighbourhood(int id, int avgPropertyPrice, int bikeScore, int walkScore, List<Amenity> amenities) {
        this.id = id;
        this.avgPropertyPrice = avgPropertyPrice;
        this.bikeScore = bikeScore;
        this.walkScore = walkScore;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAvgPropertyPrice() {
        return this.avgPropertyPrice;
    }

    public void setAvgPropertyPrice(int avgPropertyPrice) {
        this.avgPropertyPrice = avgPropertyPrice;
    }

    public int getBikeScore() {
        return this.bikeScore;
    }

    public void setBikeScore(int bikeScore) {
        this.bikeScore = bikeScore;
    }

    public int getWalkScore() {
        return this.walkScore;
    }

    public void setWalkScore(int walkScore) {
        this.walkScore = walkScore;
    }

 
}
