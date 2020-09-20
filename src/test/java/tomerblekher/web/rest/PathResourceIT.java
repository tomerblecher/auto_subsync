package tomerblekher.web.rest;

import tomerblekher.SubtitleSyncApp;
import tomerblekher.domain.Path;
import tomerblekher.repository.PathRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PathResource} REST controller.
 */
@SpringBootTest(classes = SubtitleSyncApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PathResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    @Autowired
    private PathRepository pathRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPathMockMvc;

    private Path path;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Path createEntity(EntityManager em) {
        Path path = new Path()
            .name(DEFAULT_NAME)
            .path(DEFAULT_PATH);
        return path;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Path createUpdatedEntity(EntityManager em) {
        Path path = new Path()
            .name(UPDATED_NAME)
            .path(UPDATED_PATH);
        return path;
    }

    @BeforeEach
    public void initTest() {
        path = createEntity(em);
    }

    @Test
    @Transactional
    public void createPath() throws Exception {
        int databaseSizeBeforeCreate = pathRepository.findAll().size();
        // Create the Path
        restPathMockMvc.perform(post("/api/paths")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(path)))
            .andExpect(status().isCreated());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeCreate + 1);
        Path testPath = pathList.get(pathList.size() - 1);
        assertThat(testPath.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPath.getPath()).isEqualTo(DEFAULT_PATH);
    }

    @Test
    @Transactional
    public void createPathWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pathRepository.findAll().size();

        // Create the Path with an existing ID
        path.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPathMockMvc.perform(post("/api/paths")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(path)))
            .andExpect(status().isBadRequest());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPaths() throws Exception {
        // Initialize the database
        pathRepository.saveAndFlush(path);

        // Get all the pathList
        restPathMockMvc.perform(get("/api/paths?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(path.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)));
    }
    
    @Test
    @Transactional
    public void getPath() throws Exception {
        // Initialize the database
        pathRepository.saveAndFlush(path);

        // Get the path
        restPathMockMvc.perform(get("/api/paths/{id}", path.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(path.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH));
    }
    @Test
    @Transactional
    public void getNonExistingPath() throws Exception {
        // Get the path
        restPathMockMvc.perform(get("/api/paths/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePath() throws Exception {
        // Initialize the database
        pathRepository.saveAndFlush(path);

        int databaseSizeBeforeUpdate = pathRepository.findAll().size();

        // Update the path
        Path updatedPath = pathRepository.findById(path.getId()).get();
        // Disconnect from session so that the updates on updatedPath are not directly saved in db
        em.detach(updatedPath);
        updatedPath
            .name(UPDATED_NAME)
            .path(UPDATED_PATH);

        restPathMockMvc.perform(put("/api/paths")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPath)))
            .andExpect(status().isOk());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
        Path testPath = pathList.get(pathList.size() - 1);
        assertThat(testPath.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPath.getPath()).isEqualTo(UPDATED_PATH);
    }

    @Test
    @Transactional
    public void updateNonExistingPath() throws Exception {
        int databaseSizeBeforeUpdate = pathRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPathMockMvc.perform(put("/api/paths")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(path)))
            .andExpect(status().isBadRequest());

        // Validate the Path in the database
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePath() throws Exception {
        // Initialize the database
        pathRepository.saveAndFlush(path);

        int databaseSizeBeforeDelete = pathRepository.findAll().size();

        // Delete the path
        restPathMockMvc.perform(delete("/api/paths/{id}", path.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Path> pathList = pathRepository.findAll();
        assertThat(pathList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
