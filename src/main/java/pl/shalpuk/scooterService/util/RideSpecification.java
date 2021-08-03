package pl.shalpuk.scooterService.util;

import org.springframework.data.jpa.domain.Specification;
import pl.shalpuk.scooterService.dto.RideSpecificationDto;
import pl.shalpuk.scooterService.model.Location;
import pl.shalpuk.scooterService.model.Ride;
import pl.shalpuk.scooterService.model.RideLocation;
import pl.shalpuk.scooterService.model.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RideSpecification implements Specification<Ride> {

    private static final long serialVersionUID = -5282481731409799949L;

    private final RideSpecificationDto rideSpecificationDto;

    public RideSpecification(RideSpecificationDto rideSpecificationDto) {
        this.rideSpecificationDto = rideSpecificationDto;
    }

    @Override
    public Predicate toPredicate(Root<Ride> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Set<String> userEmails = rideSpecificationDto.getUserEmails();
        Set<String> locations = rideSpecificationDto.getLocationAddress();

        Join<Ride, User> userJoin = root.join("user");
        Join<Ride, RideLocation> rideLocationJoin = root.join("rideLocations");
        Join<RideLocation, Location> locationJoin = rideLocationJoin.join("location");

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(userJoin.get("email").in(userEmails));
        predicates.add(locationJoin.get("street").in(locations));

        query.groupBy(root.get("id"), userJoin.get("email"));

        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
