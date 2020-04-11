package project.restapi.demoprojectforrestapi.events;

import lombok.*;
import org.springframework.util.StringUtils;
import project.restapi.demoprojectforrestapi.accounts.Account;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = {"id"})
@Entity
public class Event {
    @Id @GeneratedValue
    @Column(name = "EVENT_ID")
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location; // (optional) 이게 없으면 온라인 모임
    private int basePrice; // (optional)
    private int maxPrice; // (optional)
    private int limitOfEnrollment;
    private boolean offline;
    private boolean free;
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account manager;

    public void update() {
        this.free = this.basePrice == 0 && this.maxPrice == 0 ? true : false;
        this.offline = StringUtils.hasText(this.location) ? true : false;
    }
}
