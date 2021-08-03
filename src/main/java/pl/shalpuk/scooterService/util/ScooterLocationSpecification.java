package pl.shalpuk.scooterService.util;

import org.springframework.data.jpa.domain.Specification;
import pl.shalpuk.scooterService.dto.ScooterLocationStatisticDto;
import pl.shalpuk.scooterService.model.Location;
import pl.shalpuk.scooterService.model.ScooterStatistic;
import pl.shalpuk.scooterService.repository.ScooterStatisticRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScooterLocationSpecification implements Specification<ScooterStatistic> {

    private final ScooterStatisticRepository scooterStatisticRepository;

    private static final long serialVersionUID = -3753173952047987831L;

    private final ScooterLocationStatisticDto statisticDto;

    public ScooterLocationSpecification(ScooterStatisticRepository scooterStatisticRepository,
                                        ScooterLocationStatisticDto statisticDto) {
        this.scooterStatisticRepository = scooterStatisticRepository;
        this.statisticDto = statisticDto;
    }


    @Override
    public Predicate toPredicate(Root<ScooterStatistic> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//        Join<ScooterStatistic, Location> locationJoin = root.join("location");
//
//        List<Predicate> predicates = new ArrayList<>();
//        predicates.add(criteriaBuilder.equal(locationJoin.get("street"), statisticDto.getStreet()));
//        predicates.add(criteriaBuilder.equal(locationJoin.get("building"), statisticDto.getBuilding()));
//        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), LocalDate.now().minusMonths(1)));
//
//        query.groupBy(locationJoin.get("street"), root.get("time"), root.get("id"));
////        query.multiselect(criteriaBuilder.avg(root.get("count")));
//        query.multiselect(criteriaBuilder.avg(root.get("count")));
//
//        return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));

//        Specification<ScooterStatistic> specification = (root1, query1, criteriaBuilder1) -> {
        Join<ScooterStatistic, Location> locationJoin1 = root.join("location");

        List<Predicate> predicates1 = new ArrayList<>();
        predicates1.add(criteriaBuilder.equal(locationJoin1.get("street"), statisticDto.getStreet()));
        predicates1.add(criteriaBuilder.equal(locationJoin1.get("building"), statisticDto.getBuilding()));
        predicates1.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), LocalDate.now().minusMonths(1)));

        return query.multiselect(criteriaBuilder.avg(root.get("count"))).where(criteriaBuilder.and(predicates1.toArray(new Predicate[]{}))).getRestriction();
    }

//    }
}
