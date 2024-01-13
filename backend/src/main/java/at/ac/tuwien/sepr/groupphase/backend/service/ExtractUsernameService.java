package at.ac.tuwien.sepr.groupphase.backend.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface ExtractUsernameService {
    String getUsername(HttpServletRequest request);
}
