package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.*;
import net.fortuna.ical4j.model.PropertyBuilder;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Categories;

@Entity
@Table(name = "Effects")
public class Effect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String changedTitle;
    @Column
    private String changedDescription;
    @Column
    private String location;
    @Column
    private EffectType effectType;

    public Effect(String changedTitle, String changedDescription, String location) {
        this.changedTitle = changedTitle;
        this.changedDescription = changedDescription;
        this.location = location;
    }

    public Effect() {
    }

    public VEvent apply(VEvent toModify) {
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

        return toModify;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChangedTitle() {
        return changedTitle;
    }

    public void setChangedTitle(String changedTitle) {
        this.changedTitle = changedTitle;
    }

    public String getChangedDescription() {
        return changedDescription;
    }

    public void setChangedDescription(String changedDescription) {
        this.changedDescription = changedDescription;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    public void setEffectType(EffectType effectType) {
        this.effectType = effectType;
    }
}
