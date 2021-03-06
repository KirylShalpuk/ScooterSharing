package pl.shalpuk.scooterService.service;

import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.shalpuk.scooterService.model.Location;
import pl.shalpuk.scooterService.model.Scooter;
import pl.shalpuk.scooterService.model.ScooterStatistic;
import pl.shalpuk.scooterService.model.ScooterStatus;
import pl.shalpuk.scooterService.repository.ScooterRepository;
import pl.shalpuk.scooterService.repository.ScooterStatisticRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScheduleTasks {

    private final Logger logger;
    private final ScooterStatisticRepository scooterStatisticRepository;
    private final ScooterRepository scooterRepository;

    public ScheduleTasks(Logger logger,
                         ScooterStatisticRepository scooterStatisticRepository,
                         ScooterRepository scooterRepository) {
        this.logger = logger;
        this.scooterStatisticRepository = scooterStatisticRepository;
        this.scooterRepository = scooterRepository;
    }


    @Scheduled(cron = "0 30 * * * *")
    public void saveScootedHourStatistics() {
        logger.info("Start generating scooter hour statistic ...");
        List<Scooter> scooters = scooterRepository.findAllByActiveIsTrueAndScooterStatus(ScooterStatus.READY);

        Map<Location, Long> scooterCounter = scooters.stream()
                .collect(Collectors.groupingBy(Scooter::getCurrentLocation, Collectors.counting()));

        List<ScooterStatistic> scooterStatistics = new ArrayList<>();
        for (Map.Entry<Location, Long> entry : scooterCounter.entrySet()) {
            Location location = entry.getKey();
            Long counter = entry.getValue();
            ScooterStatistic scooterStatistic = new ScooterStatistic();
            scooterStatistic.setLocation(location);
            scooterStatistic.setCount(counter);
            scooterStatistic.setDate(LocalDate.now());
            scooterStatistic.setTime(LocalTime.now().truncatedTo((ChronoUnit.HOURS)));

            scooterStatistics.add(scooterStatistic);
        }

        scooterStatisticRepository.saveAll(scooterStatistics);

        logger.info("... Finish generating scooter hour statistic");
    }
}
