package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.*;
import net.fortuna.ical4j.model.component.VEvent;

import java.util.Optional;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Effect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public abstract Optional<VEvent> apply(VEvent toModify);
}
