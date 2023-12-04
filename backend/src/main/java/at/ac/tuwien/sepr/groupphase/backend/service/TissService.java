package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.TissRoom;

public interface TissService {
    TissRoom fetchCorrectLocation(String hoersaal);
    String mapLVANameShorthand(String longName);
}
