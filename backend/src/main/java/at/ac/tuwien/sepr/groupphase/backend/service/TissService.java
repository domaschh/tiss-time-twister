package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.LVADetail;
import at.ac.tuwien.sepr.groupphase.backend.TissRoom;

public interface TissService {
    TissRoom fetchCorrectLocation(String hoersaal);

    LVADetail mapLVANameShorthand(String longName);
}
