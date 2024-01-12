package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.*;
import lombok.*;

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
