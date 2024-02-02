package bdo_demo.entities;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Address {
    private String city;
    private String postalCode;
    private String street;
    private String houseNumber;
}
