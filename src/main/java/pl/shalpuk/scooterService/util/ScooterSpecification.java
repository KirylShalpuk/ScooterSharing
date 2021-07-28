package pl.shalpuk.scooterService.util;

import org.springframework.data.jpa.domain.Specification;
import pl.shalpuk.scooterService.dto.ScooterSpecificationDto;
import pl.shalpuk.scooterService.model.Location;
import pl.shalpuk.scooterService.model.Scooter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ScooterSpecification implements Specification<Scooter> {

    private final ScooterSpecificationDto scooterSpecificationDto;

    public ScooterSpecification(ScooterSpecificationDto scooterSpecificationDto) {
        this.scooterSpecificationDto = scooterSpecificationDto;
    }

    @Override
    public Predicate toPredicate(Root<Scooter> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Set<String> manufacturers = scooterSpecificationDto.getManufacturers();
        Set<String> models = scooterSpecificationDto.getModels();
        int batteryChargeTo = scooterSpecificationDto.getBatteryChargeTo();
        Set<String> locations = scooterSpecificationDto.getLocationAddress();
        boolean isActive = scooterSpecificationDto.isActive();

        Join<Scooter, Location> locationJoin = root.join("currentLocation");

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(root.get("manufacturer").in(manufacturers));
        predicates.add(root.get("model").in(models));
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("batteryCharge"), scooterSpecificationDto.getBatteryChargeFrom()));
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("batteryCharge"), batteryChargeTo));
        predicates.add(locationJoin.get("street").in(locations));

        if (isActive) {
            predicates.add(root.get("active").in(true));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
