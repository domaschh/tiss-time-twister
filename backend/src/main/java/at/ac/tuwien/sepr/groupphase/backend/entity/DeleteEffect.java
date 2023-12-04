package at.ac.tuwien.sepr.groupphase.backend.entity;
import jakarta.persistence.Entity;

import java.util.Optional;

@Entity
public class DeleteEffect extends Effect {
    @Override
    public Optional<Tevent> apply(Tevent toModify) {
        return Optional.empty();
    }
}
