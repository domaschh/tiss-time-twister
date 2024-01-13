package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String tag;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    public Long getUserId() {
        return user.getId();
    }
}
