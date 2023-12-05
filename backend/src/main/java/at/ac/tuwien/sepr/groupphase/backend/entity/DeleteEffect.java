package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.Entity;
import net.fortuna.ical4j.model.component.VEvent;

@Entity
public class DeleteEffect extends Effect {
    @Override
    public VEvent apply(VEvent toModify) {
        return null;
    }
}
