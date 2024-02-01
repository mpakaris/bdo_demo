package bdo_demo.user;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "cityName", column = @Column(name = "address_cityName")),
            @AttributeOverride( name = "postalCode", column = @Column(name = "address_postalCode")),
            @AttributeOverride( name = "streetName", column = @Column(name = "address_streetName")),
            @AttributeOverride( name = "houseNumber", column = @Column(name = "address_houseNumber"))
    })
    private Address address;
    public User(Long id, String name, String email, String password, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
    }
    public User() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) { this.password = password; }
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
}
