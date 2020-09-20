package tomerblekher.repository;

import tomerblekher.domain.Extension;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Extension entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtensionRepository extends JpaRepository<Extension, Long> {
}
