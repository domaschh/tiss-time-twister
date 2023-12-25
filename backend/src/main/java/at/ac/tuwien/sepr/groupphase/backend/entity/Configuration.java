package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Configurations")
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private String title;
    @Column()
    private String description;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    @ManyToMany()
    @JoinTable(
        name = "calendar_ref_config",
        joinColumns = @JoinColumn(name = "configuration_id"),
        inverseJoinColumns = @JoinColumn(name = "reference_id"))
    List<CalendarReference> calendarReferences;

    @OneToMany(mappedBy = "configuration", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Rule> rules = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }
}
