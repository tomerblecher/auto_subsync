package tomerblekher.repository;

import tomerblekher.domain.Path;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Path entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PathRepository extends JpaRepository<Path, Long> {

}
