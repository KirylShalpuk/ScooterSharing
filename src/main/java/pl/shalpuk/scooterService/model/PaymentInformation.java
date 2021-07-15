package pl.shalpuk.scooterService.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "payment")
public class PaymentInformation extends AbstractPersistentObject {

    private String country;
    private String address;
    private String phoneNumber;

//    @OneToMany(mappedBy = "paymentInformation", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private Set<Card> cards;
//
//    @OneToOne(mappedBy = "paymentInformation")
//    @JsonBackReference
//    private User user;
}
