package at.ac.tuwien.sepr.groupphase.backend.service;

public interface TMapper<F, T> {
    T map(F from);
}
