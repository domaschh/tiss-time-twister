package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.Entity;
import net.fortuna.ical4j.model.PropertyBuilder;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Categories;

import java.util.Optional;

@Entity
public class ModifyEffect extends Effect {
    private String changedTitle;
    private String changedDescription;
    private String location;

    public ModifyEffect(String changedTitle, String changedDescription, String location) {
        this.changedTitle = changedTitle;
        this.changedDescription = changedDescription;
        this.location = location;
    }

    public ModifyEffect() {
    }

    @Override
    public Optional<VEvent> apply(VEvent toModify) {
        if (changedTitle != null) {
            toModify.setSummary(changedTitle);
        }
        if (location != null) {
            if (toModify.getProperty(Categories.LOCATION).isPresent()) {
                toModify.getProperty(Categories.LOCATION).ifPresent(a -> a.setValue(location));
            } else {
                toModify.add(new PropertyBuilder().name(Categories.LOCATION).value(location).build());
            }
        }
        if (changedDescription != null) {
            if (toModify.getProperty(Categories.DESCRIPTION).isPresent()) {
                toModify.getProperty(Categories.DESCRIPTION).ifPresent(a -> a.setValue(changedDescription));
            } else {
                toModify.add(new PropertyBuilder().name(Categories.DESCRIPTION).value(changedDescription).build());
            }
        }

        return Optional.of(toModify);
    }
}
