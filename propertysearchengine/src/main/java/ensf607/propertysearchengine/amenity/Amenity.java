package ensf607.propertysearchengine.amenity;

// import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ensf607.propertysearchengine.neighbourhood.Neighbourhood;

import java.io.Serializable;
import java.util.List;



@Entity
@Table
public class Amenity implements Serializable {
    @Id
    private String amenity;

    @JsonIgnore
    @ManyToMany(mappedBy = "amenities")
    private List<Neighbourhood> neighbourhoodAmenities;
}
