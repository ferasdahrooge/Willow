package ensf607.propertysearchengine.amenity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface amenityRepository extends JpaRepository<Amenity, String> {

}
