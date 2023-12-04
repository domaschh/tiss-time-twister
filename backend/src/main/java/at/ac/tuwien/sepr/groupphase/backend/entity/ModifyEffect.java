package at.ac.tuwien.sepr.groupphase.backend.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;

import java.util.Optional;

@Entity
@AllArgsConstructor
public class ModifyEffect extends Effect {
    private String changedTitle;
    private String changedDescription;
    private String location;

    public ModifyEffect() {

    }

    @Override
    public Optional<Tevent> apply(Tevent toModify) {
        toModify.setDESCRIPTION(changedDescription);
        toModify.setSUMMARY(changedTitle);
        toModify.setLOCATION(location);
        return Optional.of(toModify);
    }
}
