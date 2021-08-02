package pl.shalpuk.scooterService.service;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.shalpuk.scooterService.model.Location;
import pl.shalpuk.scooterService.model.Scooter;
import pl.shalpuk.scooterService.model.ScooterStatistic;
import pl.shalpuk.scooterService.model.ScooterStatus;
import pl.shalpuk.scooterService.repository.ScooterRepository;
import pl.shalpuk.scooterService.repository.ScooterStatisticRepository;
import pl.shalpuk.scooterService.util.LogUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        LogUtil.logInfo(logger, "Start generating scooter hour statistic ...");
        List<Scooter> scooters = scooterRepository.findAllByActiveIsTrueAndScooterStatus(ScooterStatus.READY);

        Map<Location, Integer> scooterCounter = new HashMap<>();

        scooters.forEach(scooter -> {
            Location currentLocation = scooter.getCurrentLocation();
            if (scooterCounter.containsKey(currentLocation)) {
                int counter = scooterCounter.get(currentLocation);
                scooterCounter.put(currentLocation, ++counter);
            } else {
                scooterCounter.put(currentLocation, 1);
            }
        });

        List<ScooterStatistic> scooterStatistics = new ArrayList<>();
        for (Map.Entry<Location, Integer> entry : scooterCounter.entrySet()) {
            Location location = entry.getKey();
            Integer counter = entry.getValue();
            ScooterStatistic scooterStatistic = new ScooterStatistic();
            scooterStatistic.setLocation(location);
            scooterStatistic.setCount(counter);
            scooterStatistic.setDate(LocalDate.now());
            scooterStatistic.setTime(LocalTime.now().truncatedTo((ChronoUnit.HOURS)));

            scooterStatistics.add(scooterStatistic);
        }

        scooterStatisticRepository.saveAll(scooterStatistics);

        LogUtil.logInfo(logger, "... Finish generating scooter hour statistic");
    }
}
