package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyBuilder;
import net.fortuna.ical4j.model.component.VEvent;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Effects")
public class Effect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String changedTitle;
    @Column
    private String changedDescription;
    @Column
    private String location;
    @Column
    private String tag;
    @Column
    private EffectType effectType;

    public Effect(String changedTitle, String changedDescription, String location, String tag, EffectType effectType) {
        this.changedTitle = changedTitle;
        this.changedDescription = changedDescription;
        this.location = location;
        this.tag = tag;
        this.effectType = effectType;
    }

    public Effect(EffectType effectType) {
        this.effectType = effectType;
    }

    public VEvent apply(VEvent toModify) {
        if (effectType == null || effectType.equals(EffectType.DELETE)) {
            return null;
        }

        if (effectType.equals(EffectType.TAG)) {
            return toModify;
        }

        if (changedTitle != null) {
            toModify.setSummary(changedTitle);
        }
        if (location != null) {
            if (toModify.getProperty(Property.LOCATION).isPresent()) {
                toModify.getProperty(Property.LOCATION).ifPresent(a -> a.setValue(location));
            } else {
                toModify.add(new PropertyBuilder().name(Property.LOCATION).value(location).build());
            }
        }
        if (changedDescription != null) {
            if (toModify.getProperty(Property.DESCRIPTION).isPresent()) {
                toModify.getProperty(Property.DESCRIPTION).ifPresent(a -> a.setValue(changedDescription));
            } else {
                toModify.add(new PropertyBuilder().name(Property.DESCRIPTION).value(changedDescription).build());
            }
        }

        return toModify;
    }

    public Boolean tagMatches(Tag tag) {
        if (!effectType.equals(EffectType.TAG)) {
            return false;
        }
        return tag.getTag().equals(this.tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Effect effect = (Effect) o;
        return id == effect.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
