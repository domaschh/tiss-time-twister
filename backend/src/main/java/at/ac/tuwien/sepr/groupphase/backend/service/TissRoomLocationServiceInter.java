package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.TissRoom;

public interface TissRoomLocationServiceInter {
    TissRoom fetchCorrectLocation(String hoersaal);
}
