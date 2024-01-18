package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Getter
@Setter
public class ApplicationUser {

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @ToString.Exclude
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false)
    private Boolean admin;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private PasswordResetToken passwordResetToken;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JoinColumn(name = "user_id")
    private List<Configuration> configurations;

    public ApplicationUser(String email, String password, Boolean admin) {
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    public ApplicationUser(String email, String password, Boolean admin, long id) {
        this.email = email;
        this.password = password;
        this.admin = admin;
    }
}
