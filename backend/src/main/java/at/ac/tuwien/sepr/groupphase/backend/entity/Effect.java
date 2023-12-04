package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.*;

import java.util.Optional;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Effect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public abstract Optional<Tevent> apply(Tevent toModify);
}
