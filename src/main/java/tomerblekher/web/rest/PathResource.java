package tomerblekher.web.rest;

import tomerblekher.domain.Path;
import tomerblekher.repository.PathRepository;
import tomerblekher.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link tomerblekher.domain.Path}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PathResource {

    private final Logger log = LoggerFactory.getLogger(PathResource.class);

    private static final String ENTITY_NAME = "path";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PathRepository pathRepository;

    public PathResource(PathRepository pathRepository) {
        this.pathRepository = pathRepository;
    }

    /**
     * {@code POST  /paths} : Create a new path.
     *
     * @param path the path to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new path, or with status {@code 400 (Bad Request)} if the path has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paths")
    public ResponseEntity<Path> createPath(@RequestBody Path path) throws URISyntaxException {
        log.debug("REST request to save Path : {}", path);
        if (path.getId() != null) {
            throw new BadRequestAlertException("A new path cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Path result = pathRepository.save(path);
        return ResponseEntity.created(new URI("/api/paths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paths} : Updates an existing path.
     *
     * @param path the path to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated path,
     * or with status {@code 400 (Bad Request)} if the path is not valid,
     * or with status {@code 500 (Internal Server Error)} if the path couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paths")
    public ResponseEntity<Path> updatePath(@RequestBody Path path) throws URISyntaxException {
        log.debug("REST request to update Path : {}", path);
        if (path.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Path result = pathRepository.save(path);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, path.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /paths} : get all the paths.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paths in body.
     */
    @GetMapping("/paths")
    public ResponseEntity<List<Path>> getAllPaths(Pageable pageable) {
        log.debug("REST request to get a page of Paths");
        Page<Path> page = pathRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /paths/:id} : get the "id" path.
     *
     * @param id the id of the path to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the path, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paths/{id}")
    public ResponseEntity<Path> getPath(@PathVariable Long id) {
        log.debug("REST request to get Path : {}", id);
        Optional<Path> path = pathRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(path);
    }

    /**
     * {@code DELETE  /paths/:id} : delete the "id" path.
     *
     * @param id the id of the path to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paths/{id}")
    public ResponseEntity<Void> deletePath(@PathVariable Long id) {
        log.debug("REST request to delete Path : {}", id);
        pathRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
