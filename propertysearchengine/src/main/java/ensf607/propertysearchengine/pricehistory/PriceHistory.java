package ensf607.propertysearchengine.pricehistory;

import ensf607.propertysearchengine.property.*;

import java.time.LocalDate;
import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Entity
@Table
public class PriceHistory implements Serializable {
    @Id
    @SequenceGenerator(
            name = "price_history_sequence",
            sequenceName = "price_history_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "price_history_sequence"
    )
    private int id;

    private LocalDate date;
    private int price;

    @ManyToOne
    @JoinColumn(name="PropertyMLS", referencedColumnName = "mls")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Property property;

    public PriceHistory() {
    }

    public PriceHistory(Property property) {
        date = LocalDate.now();
        this.price = property.getPrice();
        this.property = property;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
 
}
