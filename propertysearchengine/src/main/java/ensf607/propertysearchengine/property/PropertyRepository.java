package ensf607.propertysearchengine.property;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

    @Query(value = "SELECT * FROM property u WHERE u.address = ?1", nativeQuery = true)
    public Optional<Property> findByAddress(String address);

}
