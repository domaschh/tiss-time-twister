package at.ac.tuwien.sepr.groupphase.backend;

import java.util.List;

public record LVADetail(String shorthand, String examURl, String lvaNum, List<Registration> testRegistrations) {
}

