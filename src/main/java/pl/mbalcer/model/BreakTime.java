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
public class BreakTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime startBreak;
    private LocalTime endBreak;

    @ManyToOne
    private WorkTime workTime;

    public BreakTime(LocalTime startBreak, LocalTime endBreak, WorkTime workTime) {
        this.startBreak = startBreak;
        this.endBreak = endBreak;
        this.workTime = workTime;
    }
}
