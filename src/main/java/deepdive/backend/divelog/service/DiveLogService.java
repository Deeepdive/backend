package deepdive.backend.divelog.service;

import deepdive.backend.divelog.domain.DiveLog;
import deepdive.backend.divelog.repository.DiveLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiveLogService {

    private final DiveLogRepository diveLogRepository;

    public DiveLog save(DiveLog diveLog) {
        return diveLogRepository.save(diveLog);
    }
}
