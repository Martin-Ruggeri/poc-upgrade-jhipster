package com.pae.well.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pae.well.IntegrationTest;
import com.pae.well.domain.District;
import com.pae.well.domain.PetroleumPlant;
import com.pae.well.repository.PetroleumPlantRepository;
import com.pae.well.service.dto.PetroleumPlantDTO;
import com.pae.well.service.mapper.PetroleumPlantMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PetroleumPlantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PetroleumPlantResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/petroleum-plants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PetroleumPlantRepository petroleumPlantRepository;

    @Autowired
    private PetroleumPlantMapper petroleumPlantMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPetroleumPlantMockMvc;

    private PetroleumPlant petroleumPlant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PetroleumPlant createEntity(EntityManager em) {
        PetroleumPlant petroleumPlant = new PetroleumPlant().name(DEFAULT_NAME);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        petroleumPlant.setDistrict(district);
        return petroleumPlant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PetroleumPlant createUpdatedEntity(EntityManager em) {
        PetroleumPlant petroleumPlant = new PetroleumPlant().name(UPDATED_NAME);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createUpdatedEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        petroleumPlant.setDistrict(district);
        return petroleumPlant;
    }

    @BeforeEach
    public void initTest() {
        petroleumPlant = createEntity(em);
    }

    @Test
    @Transactional
    void createPetroleumPlant() throws Exception {
        int databaseSizeBeforeCreate = petroleumPlantRepository.findAll().size();
        // Create the PetroleumPlant
        PetroleumPlantDTO petroleumPlantDTO = petroleumPlantMapper.toDto(petroleumPlant);
        restPetroleumPlantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petroleumPlantDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PetroleumPlant in the database
        List<PetroleumPlant> petroleumPlantList = petroleumPlantRepository.findAll();
        assertThat(petroleumPlantList).hasSize(databaseSizeBeforeCreate + 1);
        PetroleumPlant testPetroleumPlant = petroleumPlantList.get(petroleumPlantList.size() - 1);
        assertThat(testPetroleumPlant.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createPetroleumPlantWithExistingId() throws Exception {
        // Create the PetroleumPlant with an existing ID
        petroleumPlant.setId(1L);
        PetroleumPlantDTO petroleumPlantDTO = petroleumPlantMapper.toDto(petroleumPlant);

        int databaseSizeBeforeCreate = petroleumPlantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPetroleumPlantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petroleumPlantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PetroleumPlant in the database
        List<PetroleumPlant> petroleumPlantList = petroleumPlantRepository.findAll();
        assertThat(petroleumPlantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = petroleumPlantRepository.findAll().size();
        // set the field null
        petroleumPlant.setName(null);

        // Create the PetroleumPlant, which fails.
        PetroleumPlantDTO petroleumPlantDTO = petroleumPlantMapper.toDto(petroleumPlant);

        restPetroleumPlantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petroleumPlantDTO))
            )
            .andExpect(status().isBadRequest());

        List<PetroleumPlant> petroleumPlantList = petroleumPlantRepository.findAll();
        assertThat(petroleumPlantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPetroleumPlants() throws Exception {
        // Initialize the database
        petroleumPlantRepository.saveAndFlush(petroleumPlant);

        // Get all the petroleumPlantList
        restPetroleumPlantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(petroleumPlant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getPetroleumPlant() throws Exception {
        // Initialize the database
        petroleumPlantRepository.saveAndFlush(petroleumPlant);

        // Get the petroleumPlant
        restPetroleumPlantMockMvc
            .perform(get(ENTITY_API_URL_ID, petroleumPlant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(petroleumPlant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingPetroleumPlant() throws Exception {
        // Get the petroleumPlant
        restPetroleumPlantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPetroleumPlant() throws Exception {
        // Initialize the database
        petroleumPlantRepository.saveAndFlush(petroleumPlant);

        int databaseSizeBeforeUpdate = petroleumPlantRepository.findAll().size();

        // Update the petroleumPlant
        PetroleumPlant updatedPetroleumPlant = petroleumPlantRepository.findById(petroleumPlant.getId()).get();
        // Disconnect from session so that the updates on updatedPetroleumPlant are not directly saved in db
        em.detach(updatedPetroleumPlant);
        updatedPetroleumPlant.name(UPDATED_NAME);
        PetroleumPlantDTO petroleumPlantDTO = petroleumPlantMapper.toDto(updatedPetroleumPlant);

        restPetroleumPlantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, petroleumPlantDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petroleumPlantDTO))
            )
            .andExpect(status().isOk());

        // Validate the PetroleumPlant in the database
        List<PetroleumPlant> petroleumPlantList = petroleumPlantRepository.findAll();
        assertThat(petroleumPlantList).hasSize(databaseSizeBeforeUpdate);
        PetroleumPlant testPetroleumPlant = petroleumPlantList.get(petroleumPlantList.size() - 1);
        assertThat(testPetroleumPlant.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingPetroleumPlant() throws Exception {
        int databaseSizeBeforeUpdate = petroleumPlantRepository.findAll().size();
        petroleumPlant.setId(count.incrementAndGet());

        // Create the PetroleumPlant
        PetroleumPlantDTO petroleumPlantDTO = petroleumPlantMapper.toDto(petroleumPlant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPetroleumPlantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, petroleumPlantDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petroleumPlantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PetroleumPlant in the database
        List<PetroleumPlant> petroleumPlantList = petroleumPlantRepository.findAll();
        assertThat(petroleumPlantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPetroleumPlant() throws Exception {
        int databaseSizeBeforeUpdate = petroleumPlantRepository.findAll().size();
        petroleumPlant.setId(count.incrementAndGet());

        // Create the PetroleumPlant
        PetroleumPlantDTO petroleumPlantDTO = petroleumPlantMapper.toDto(petroleumPlant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPetroleumPlantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petroleumPlantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PetroleumPlant in the database
        List<PetroleumPlant> petroleumPlantList = petroleumPlantRepository.findAll();
        assertThat(petroleumPlantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPetroleumPlant() throws Exception {
        int databaseSizeBeforeUpdate = petroleumPlantRepository.findAll().size();
        petroleumPlant.setId(count.incrementAndGet());

        // Create the PetroleumPlant
        PetroleumPlantDTO petroleumPlantDTO = petroleumPlantMapper.toDto(petroleumPlant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPetroleumPlantMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petroleumPlantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PetroleumPlant in the database
        List<PetroleumPlant> petroleumPlantList = petroleumPlantRepository.findAll();
        assertThat(petroleumPlantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePetroleumPlantWithPatch() throws Exception {
        // Initialize the database
        petroleumPlantRepository.saveAndFlush(petroleumPlant);

        int databaseSizeBeforeUpdate = petroleumPlantRepository.findAll().size();

        // Update the petroleumPlant using partial update
        PetroleumPlant partialUpdatedPetroleumPlant = new PetroleumPlant();
        partialUpdatedPetroleumPlant.setId(petroleumPlant.getId());

        partialUpdatedPetroleumPlant.name(UPDATED_NAME);

        restPetroleumPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPetroleumPlant.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPetroleumPlant))
            )
            .andExpect(status().isOk());

        // Validate the PetroleumPlant in the database
        List<PetroleumPlant> petroleumPlantList = petroleumPlantRepository.findAll();
        assertThat(petroleumPlantList).hasSize(databaseSizeBeforeUpdate);
        PetroleumPlant testPetroleumPlant = petroleumPlantList.get(petroleumPlantList.size() - 1);
        assertThat(testPetroleumPlant.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdatePetroleumPlantWithPatch() throws Exception {
        // Initialize the database
        petroleumPlantRepository.saveAndFlush(petroleumPlant);

        int databaseSizeBeforeUpdate = petroleumPlantRepository.findAll().size();

        // Update the petroleumPlant using partial update
        PetroleumPlant partialUpdatedPetroleumPlant = new PetroleumPlant();
        partialUpdatedPetroleumPlant.setId(petroleumPlant.getId());

        partialUpdatedPetroleumPlant.name(UPDATED_NAME);

        restPetroleumPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPetroleumPlant.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPetroleumPlant))
            )
            .andExpect(status().isOk());

        // Validate the PetroleumPlant in the database
        List<PetroleumPlant> petroleumPlantList = petroleumPlantRepository.findAll();
        assertThat(petroleumPlantList).hasSize(databaseSizeBeforeUpdate);
        PetroleumPlant testPetroleumPlant = petroleumPlantList.get(petroleumPlantList.size() - 1);
        assertThat(testPetroleumPlant.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingPetroleumPlant() throws Exception {
        int databaseSizeBeforeUpdate = petroleumPlantRepository.findAll().size();
        petroleumPlant.setId(count.incrementAndGet());

        // Create the PetroleumPlant
        PetroleumPlantDTO petroleumPlantDTO = petroleumPlantMapper.toDto(petroleumPlant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPetroleumPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, petroleumPlantDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(petroleumPlantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PetroleumPlant in the database
        List<PetroleumPlant> petroleumPlantList = petroleumPlantRepository.findAll();
        assertThat(petroleumPlantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPetroleumPlant() throws Exception {
        int databaseSizeBeforeUpdate = petroleumPlantRepository.findAll().size();
        petroleumPlant.setId(count.incrementAndGet());

        // Create the PetroleumPlant
        PetroleumPlantDTO petroleumPlantDTO = petroleumPlantMapper.toDto(petroleumPlant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPetroleumPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(petroleumPlantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PetroleumPlant in the database
        List<PetroleumPlant> petroleumPlantList = petroleumPlantRepository.findAll();
        assertThat(petroleumPlantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPetroleumPlant() throws Exception {
        int databaseSizeBeforeUpdate = petroleumPlantRepository.findAll().size();
        petroleumPlant.setId(count.incrementAndGet());

        // Create the PetroleumPlant
        PetroleumPlantDTO petroleumPlantDTO = petroleumPlantMapper.toDto(petroleumPlant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPetroleumPlantMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(petroleumPlantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PetroleumPlant in the database
        List<PetroleumPlant> petroleumPlantList = petroleumPlantRepository.findAll();
        assertThat(petroleumPlantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePetroleumPlant() throws Exception {
        // Initialize the database
        petroleumPlantRepository.saveAndFlush(petroleumPlant);

        int databaseSizeBeforeDelete = petroleumPlantRepository.findAll().size();

        // Delete the petroleumPlant
        restPetroleumPlantMockMvc
            .perform(delete(ENTITY_API_URL_ID, petroleumPlant.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PetroleumPlant> petroleumPlantList = petroleumPlantRepository.findAll();
        assertThat(petroleumPlantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
