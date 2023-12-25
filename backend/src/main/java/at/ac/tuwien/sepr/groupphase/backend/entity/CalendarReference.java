package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "Calendars")
public class CalendarReference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private String name;
    @Column()
    private String link;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }
}
