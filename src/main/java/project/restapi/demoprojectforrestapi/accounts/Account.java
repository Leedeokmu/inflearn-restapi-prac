package project.restapi.demoprojectforrestapi.accounts;

import lombok.*;
import project.restapi.demoprojectforrestapi.events.Event;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter @EqualsAndHashCode(of ="id")
@Builder @NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
public class Account {
    @Id @GeneratedValue
    @Column(name = "ACCOUNT_ID")
    private Integer id;
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles = new HashSet<>();

    @OneToMany(mappedBy = "account")
    private List<Event> events = new ArrayList<>();
}
