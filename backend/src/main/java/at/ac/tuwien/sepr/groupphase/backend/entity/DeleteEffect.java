package at.ac.tuwien.sepr.groupphase.backend.entity;
import jakarta.persistence.Entity;
import net.fortuna.ical4j.model.component.VEvent;

import java.util.Optional;

@Entity
public class DeleteEffect extends Effect {
    @Override
    public Optional<VEvent> apply(VEvent toModify) {
        return Optional.empty();
    }
}
