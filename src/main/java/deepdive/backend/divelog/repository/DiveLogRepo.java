package deepdive.backend.divelog.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DiveLogRepo {

    private final EntityManager em;

}
