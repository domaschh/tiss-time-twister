package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
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
}
