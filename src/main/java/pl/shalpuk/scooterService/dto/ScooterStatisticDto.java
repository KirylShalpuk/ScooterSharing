package pl.shalpuk.scooterService.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

public class ScooterStatisticDto {

    @JsonProperty("countAVG")
    private double countAVG;

    @JsonProperty("time")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime time;

    public double getCountAVG() {
        return countAVG;
    }

    public void setCountAVG(double countAVG) {
        this.countAVG = countAVG;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
