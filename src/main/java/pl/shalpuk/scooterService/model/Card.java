package pl.shalpuk.scooterService.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "cards")
public class Card extends AbstractPersistentObject implements Serializable {

    private static final long serialVersionUID = 1184791638320221263L;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_holder")
    private String cardHolder;

    @Column(name = "date_expiration")
    private String dateExpiration;

    @Column(name = "email")
    private String email;

    @Column(name = "main")
    private boolean main;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    @JsonBackReference
    private PaymentInformation paymentInformation;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public PaymentInformation getPaymentInformation() {
        return paymentInformation;
    }

    public void setPaymentInformation(PaymentInformation paymentInformation) {
        this.paymentInformation = paymentInformation;
    }
}
