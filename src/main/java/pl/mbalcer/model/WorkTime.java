package pl.mbalcer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WorkTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer dayOfMonth;

    @ManyToOne
    private MonthYear monthYear;

    public WorkTime(LocalTime startTime, LocalTime endTime, Integer dayOfMonth, MonthYear monthYear) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfMonth = dayOfMonth;
        this.monthYear = monthYear;
    }
}
