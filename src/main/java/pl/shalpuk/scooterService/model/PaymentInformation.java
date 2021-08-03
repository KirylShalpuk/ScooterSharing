package pl.shalpuk.scooterService.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.collect.Sets;
import org.springframework.util.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "payments")
public class PaymentInformation extends AbstractPersistentObject implements Serializable {

    private static final long serialVersionUID = -7332629177249099373L;

    @Column(name = "country")
    private String country;

    @Column(name = "address")
    private String address;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "paymentInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Card> cards;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        if (this.cards == null) {
            this.cards = cards;
        } else {
            this.cards.clear();
            this.cards.addAll(cards);
        }

        this.cards.forEach(card -> card.setPaymentInformation(this));
    }

    public void addCard(Card card) {
        card.setPaymentInformation(this);

        if (CollectionUtils.isEmpty(getCards())) {
            this.cards = Sets.newHashSet(card);
        } else {
            this.cards.add(card);
        }
    }
}
