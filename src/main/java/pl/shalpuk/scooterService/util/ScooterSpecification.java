package pl.shalpuk.scooterService.util;

import org.springframework.data.jpa.domain.Specification;
import pl.shalpuk.scooterService.dto.ScooterSpecificationDto;
import pl.shalpuk.scooterService.model.Scooter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(root.get("manufacturer").in(manufacturers));
        predicates.add(root.get("model").in(models));
        predicates.add(criteriaBuilder.greaterThan(root.get("batteryCharge"), scooterSpecificationDto.getBatteryChargeFrom()));
        predicates.add(criteriaBuilder.lessThan(root.get("batteryCharge"), batteryChargeTo));
//        predicates.add(root.get("currentLocation.address").in(locations));

        if (isActive) {
            predicates.add(root.get("active").in(true));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
    }
}
