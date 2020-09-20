package tomerblekher.web.rest;

import tomerblekher.domain.Extension;
import tomerblekher.repository.ExtensionRepository;
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
 * REST controller for managing {@link tomerblekher.domain.Extension}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ExtensionResource {

    private final Logger log = LoggerFactory.getLogger(ExtensionResource.class);

    private static final String ENTITY_NAME = "extension";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExtensionRepository extensionRepository;

    public ExtensionResource(ExtensionRepository extensionRepository) {
        this.extensionRepository = extensionRepository;
    }

    /**
     * {@code POST  /extensions} : Create a new extension.
     *
     * @param extension the extension to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new extension, or with status {@code 400 (Bad Request)} if the extension has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/extensions")
    public ResponseEntity<Extension> createExtension(@RequestBody Extension extension) throws URISyntaxException {
        log.debug("REST request to save Extension : {}", extension);
        if (extension.getId() != null) {
            throw new BadRequestAlertException("A new extension cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Extension result = extensionRepository.save(extension);
        return ResponseEntity.created(new URI("/api/extensions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /extensions} : Updates an existing extension.
     *
     * @param extension the extension to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated extension,
     * or with status {@code 400 (Bad Request)} if the extension is not valid,
     * or with status {@code 500 (Internal Server Error)} if the extension couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/extensions")
    public ResponseEntity<Extension> updateExtension(@RequestBody Extension extension) throws URISyntaxException {
        log.debug("REST request to update Extension : {}", extension);
        if (extension.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Extension result = extensionRepository.save(extension);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, extension.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /extensions} : get all the extensions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of extensions in body.
     */
    @GetMapping("/extensions")
    public ResponseEntity<List<Extension>> getAllExtensions(Pageable pageable) {
        log.debug("REST request to get a page of Extensions");
        Page<Extension> page = extensionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /extensions/:id} : get the "id" extension.
     *
     * @param id the id of the extension to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the extension, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/extensions/{id}")
    public ResponseEntity<Extension> getExtension(@PathVariable Long id) {
        log.debug("REST request to get Extension : {}", id);
        Optional<Extension> extension = extensionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(extension);
    }

    /**
     * {@code DELETE  /extensions/:id} : delete the "id" extension.
     *
     * @param id the id of the extension to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/extensions/{id}")
    public ResponseEntity<Void> deleteExtension(@PathVariable Long id) {
        log.debug("REST request to delete Extension : {}", id);
        extensionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
