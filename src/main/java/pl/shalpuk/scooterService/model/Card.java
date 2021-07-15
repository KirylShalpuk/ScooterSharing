package pl.shalpuk.scooterService.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cards")
public class Card extends AbstractPersistentObject {

    private String cardNumber;
    private String cardHolder;
    private String dateExpiration;
    private String email;
    private boolean main;

//    @ManyToOne
//    @JoinColumn(name = "card_id")
//    @JsonBackReference
//    private PaymentInformation paymentInformation;

}
