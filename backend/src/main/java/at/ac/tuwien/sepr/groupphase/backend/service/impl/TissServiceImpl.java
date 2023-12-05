package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.TissRoom;
import at.ac.tuwien.sepr.groupphase.backend.service.TissService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TissServiceImpl implements TissService {
    private Map<String, TissRoom> tissRoomMap;
    private Map<String, String> shorthandMap;

    @Autowired
    public void tissRoomConfig(Map<String, TissRoom> tissRoomMap) {
        this.tissRoomMap = tissRoomMap;
    }

    @Autowired
    public void shorthandConfig(Map<String, String> shorthandMap) {
        this.shorthandMap = shorthandMap;
    }

    @Override
    public TissRoom fetchCorrectLocation(String hoersaal) {
        return tissRoomMap.get(hoersaal);
    }

    @Override
    public String mapLVANameShorthand(String longName) {
        return shorthandMap.get(longName);
    }
}
