package ensf607.propertysearchengine.pricehistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Integer> {

    @Query(value = "SELECT * FROM price_history u WHERE u.propertymls = ?1", nativeQuery = true)
    public List<PriceHistory> findByMLS(int mls);

}
