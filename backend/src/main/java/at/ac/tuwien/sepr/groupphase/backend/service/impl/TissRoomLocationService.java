package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.TissRoom;
import at.ac.tuwien.sepr.groupphase.backend.service.TissRoomLocationServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TissRoomLocationService implements TissRoomLocationServiceInter {
    private Map<String, TissRoom> tissRoomMap;

    @Autowired
    public void TissRoomConfig(Map<String, TissRoom> tissRoomMap) {
        this.tissRoomMap = tissRoomMap;
    }
    @Override
    public TissRoom fetchCorrectLocation(String hoersaal) {
        return tissRoomMap.get(hoersaal);
    }
}
