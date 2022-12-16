package com.pae.well.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pae.well.IntegrationTest;
import com.pae.well.domain.District;
import com.pae.well.domain.SaltWaterInjectionPlant;
import com.pae.well.repository.SaltWaterInjectionPlantRepository;
import com.pae.well.service.dto.SaltWaterInjectionPlantDTO;
import com.pae.well.service.mapper.SaltWaterInjectionPlantMapper;
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
 * Integration tests for the {@link SaltWaterInjectionPlantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SaltWaterInjectionPlantResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/salt-water-injection-plants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SaltWaterInjectionPlantRepository saltWaterInjectionPlantRepository;

    @Autowired
    private SaltWaterInjectionPlantMapper saltWaterInjectionPlantMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSaltWaterInjectionPlantMockMvc;

    private SaltWaterInjectionPlant saltWaterInjectionPlant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaltWaterInjectionPlant createEntity(EntityManager em) {
        SaltWaterInjectionPlant saltWaterInjectionPlant = new SaltWaterInjectionPlant().name(DEFAULT_NAME);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        saltWaterInjectionPlant.setDistrict(district);
        return saltWaterInjectionPlant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SaltWaterInjectionPlant createUpdatedEntity(EntityManager em) {
        SaltWaterInjectionPlant saltWaterInjectionPlant = new SaltWaterInjectionPlant().name(UPDATED_NAME);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createUpdatedEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        saltWaterInjectionPlant.setDistrict(district);
        return saltWaterInjectionPlant;
    }

    @BeforeEach
    public void initTest() {
        saltWaterInjectionPlant = createEntity(em);
    }

    @Test
    @Transactional
    void createSaltWaterInjectionPlant() throws Exception {
        int databaseSizeBeforeCreate = saltWaterInjectionPlantRepository.findAll().size();
        // Create the SaltWaterInjectionPlant
        SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO = saltWaterInjectionPlantMapper.toDto(saltWaterInjectionPlant);
        restSaltWaterInjectionPlantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saltWaterInjectionPlantDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SaltWaterInjectionPlant in the database
        List<SaltWaterInjectionPlant> saltWaterInjectionPlantList = saltWaterInjectionPlantRepository.findAll();
        assertThat(saltWaterInjectionPlantList).hasSize(databaseSizeBeforeCreate + 1);
        SaltWaterInjectionPlant testSaltWaterInjectionPlant = saltWaterInjectionPlantList.get(saltWaterInjectionPlantList.size() - 1);
        assertThat(testSaltWaterInjectionPlant.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createSaltWaterInjectionPlantWithExistingId() throws Exception {
        // Create the SaltWaterInjectionPlant with an existing ID
        saltWaterInjectionPlant.setId(1L);
        SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO = saltWaterInjectionPlantMapper.toDto(saltWaterInjectionPlant);

        int databaseSizeBeforeCreate = saltWaterInjectionPlantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaltWaterInjectionPlantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saltWaterInjectionPlantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaltWaterInjectionPlant in the database
        List<SaltWaterInjectionPlant> saltWaterInjectionPlantList = saltWaterInjectionPlantRepository.findAll();
        assertThat(saltWaterInjectionPlantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = saltWaterInjectionPlantRepository.findAll().size();
        // set the field null
        saltWaterInjectionPlant.setName(null);

        // Create the SaltWaterInjectionPlant, which fails.
        SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO = saltWaterInjectionPlantMapper.toDto(saltWaterInjectionPlant);

        restSaltWaterInjectionPlantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saltWaterInjectionPlantDTO))
            )
            .andExpect(status().isBadRequest());

        List<SaltWaterInjectionPlant> saltWaterInjectionPlantList = saltWaterInjectionPlantRepository.findAll();
        assertThat(saltWaterInjectionPlantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSaltWaterInjectionPlants() throws Exception {
        // Initialize the database
        saltWaterInjectionPlantRepository.saveAndFlush(saltWaterInjectionPlant);

        // Get all the saltWaterInjectionPlantList
        restSaltWaterInjectionPlantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saltWaterInjectionPlant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getSaltWaterInjectionPlant() throws Exception {
        // Initialize the database
        saltWaterInjectionPlantRepository.saveAndFlush(saltWaterInjectionPlant);

        // Get the saltWaterInjectionPlant
        restSaltWaterInjectionPlantMockMvc
            .perform(get(ENTITY_API_URL_ID, saltWaterInjectionPlant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(saltWaterInjectionPlant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingSaltWaterInjectionPlant() throws Exception {
        // Get the saltWaterInjectionPlant
        restSaltWaterInjectionPlantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSaltWaterInjectionPlant() throws Exception {
        // Initialize the database
        saltWaterInjectionPlantRepository.saveAndFlush(saltWaterInjectionPlant);

        int databaseSizeBeforeUpdate = saltWaterInjectionPlantRepository.findAll().size();

        // Update the saltWaterInjectionPlant
        SaltWaterInjectionPlant updatedSaltWaterInjectionPlant = saltWaterInjectionPlantRepository
            .findById(saltWaterInjectionPlant.getId())
            .get();
        // Disconnect from session so that the updates on updatedSaltWaterInjectionPlant are not directly saved in db
        em.detach(updatedSaltWaterInjectionPlant);
        updatedSaltWaterInjectionPlant.name(UPDATED_NAME);
        SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO = saltWaterInjectionPlantMapper.toDto(updatedSaltWaterInjectionPlant);

        restSaltWaterInjectionPlantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, saltWaterInjectionPlantDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saltWaterInjectionPlantDTO))
            )
            .andExpect(status().isOk());

        // Validate the SaltWaterInjectionPlant in the database
        List<SaltWaterInjectionPlant> saltWaterInjectionPlantList = saltWaterInjectionPlantRepository.findAll();
        assertThat(saltWaterInjectionPlantList).hasSize(databaseSizeBeforeUpdate);
        SaltWaterInjectionPlant testSaltWaterInjectionPlant = saltWaterInjectionPlantList.get(saltWaterInjectionPlantList.size() - 1);
        assertThat(testSaltWaterInjectionPlant.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingSaltWaterInjectionPlant() throws Exception {
        int databaseSizeBeforeUpdate = saltWaterInjectionPlantRepository.findAll().size();
        saltWaterInjectionPlant.setId(count.incrementAndGet());

        // Create the SaltWaterInjectionPlant
        SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO = saltWaterInjectionPlantMapper.toDto(saltWaterInjectionPlant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaltWaterInjectionPlantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, saltWaterInjectionPlantDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saltWaterInjectionPlantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaltWaterInjectionPlant in the database
        List<SaltWaterInjectionPlant> saltWaterInjectionPlantList = saltWaterInjectionPlantRepository.findAll();
        assertThat(saltWaterInjectionPlantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSaltWaterInjectionPlant() throws Exception {
        int databaseSizeBeforeUpdate = saltWaterInjectionPlantRepository.findAll().size();
        saltWaterInjectionPlant.setId(count.incrementAndGet());

        // Create the SaltWaterInjectionPlant
        SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO = saltWaterInjectionPlantMapper.toDto(saltWaterInjectionPlant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaltWaterInjectionPlantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saltWaterInjectionPlantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaltWaterInjectionPlant in the database
        List<SaltWaterInjectionPlant> saltWaterInjectionPlantList = saltWaterInjectionPlantRepository.findAll();
        assertThat(saltWaterInjectionPlantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSaltWaterInjectionPlant() throws Exception {
        int databaseSizeBeforeUpdate = saltWaterInjectionPlantRepository.findAll().size();
        saltWaterInjectionPlant.setId(count.incrementAndGet());

        // Create the SaltWaterInjectionPlant
        SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO = saltWaterInjectionPlantMapper.toDto(saltWaterInjectionPlant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaltWaterInjectionPlantMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saltWaterInjectionPlantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaltWaterInjectionPlant in the database
        List<SaltWaterInjectionPlant> saltWaterInjectionPlantList = saltWaterInjectionPlantRepository.findAll();
        assertThat(saltWaterInjectionPlantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSaltWaterInjectionPlantWithPatch() throws Exception {
        // Initialize the database
        saltWaterInjectionPlantRepository.saveAndFlush(saltWaterInjectionPlant);

        int databaseSizeBeforeUpdate = saltWaterInjectionPlantRepository.findAll().size();

        // Update the saltWaterInjectionPlant using partial update
        SaltWaterInjectionPlant partialUpdatedSaltWaterInjectionPlant = new SaltWaterInjectionPlant();
        partialUpdatedSaltWaterInjectionPlant.setId(saltWaterInjectionPlant.getId());

        partialUpdatedSaltWaterInjectionPlant.name(UPDATED_NAME);

        restSaltWaterInjectionPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaltWaterInjectionPlant.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSaltWaterInjectionPlant))
            )
            .andExpect(status().isOk());

        // Validate the SaltWaterInjectionPlant in the database
        List<SaltWaterInjectionPlant> saltWaterInjectionPlantList = saltWaterInjectionPlantRepository.findAll();
        assertThat(saltWaterInjectionPlantList).hasSize(databaseSizeBeforeUpdate);
        SaltWaterInjectionPlant testSaltWaterInjectionPlant = saltWaterInjectionPlantList.get(saltWaterInjectionPlantList.size() - 1);
        assertThat(testSaltWaterInjectionPlant.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateSaltWaterInjectionPlantWithPatch() throws Exception {
        // Initialize the database
        saltWaterInjectionPlantRepository.saveAndFlush(saltWaterInjectionPlant);

        int databaseSizeBeforeUpdate = saltWaterInjectionPlantRepository.findAll().size();

        // Update the saltWaterInjectionPlant using partial update
        SaltWaterInjectionPlant partialUpdatedSaltWaterInjectionPlant = new SaltWaterInjectionPlant();
        partialUpdatedSaltWaterInjectionPlant.setId(saltWaterInjectionPlant.getId());

        partialUpdatedSaltWaterInjectionPlant.name(UPDATED_NAME);

        restSaltWaterInjectionPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSaltWaterInjectionPlant.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSaltWaterInjectionPlant))
            )
            .andExpect(status().isOk());

        // Validate the SaltWaterInjectionPlant in the database
        List<SaltWaterInjectionPlant> saltWaterInjectionPlantList = saltWaterInjectionPlantRepository.findAll();
        assertThat(saltWaterInjectionPlantList).hasSize(databaseSizeBeforeUpdate);
        SaltWaterInjectionPlant testSaltWaterInjectionPlant = saltWaterInjectionPlantList.get(saltWaterInjectionPlantList.size() - 1);
        assertThat(testSaltWaterInjectionPlant.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingSaltWaterInjectionPlant() throws Exception {
        int databaseSizeBeforeUpdate = saltWaterInjectionPlantRepository.findAll().size();
        saltWaterInjectionPlant.setId(count.incrementAndGet());

        // Create the SaltWaterInjectionPlant
        SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO = saltWaterInjectionPlantMapper.toDto(saltWaterInjectionPlant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaltWaterInjectionPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saltWaterInjectionPlantDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saltWaterInjectionPlantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaltWaterInjectionPlant in the database
        List<SaltWaterInjectionPlant> saltWaterInjectionPlantList = saltWaterInjectionPlantRepository.findAll();
        assertThat(saltWaterInjectionPlantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSaltWaterInjectionPlant() throws Exception {
        int databaseSizeBeforeUpdate = saltWaterInjectionPlantRepository.findAll().size();
        saltWaterInjectionPlant.setId(count.incrementAndGet());

        // Create the SaltWaterInjectionPlant
        SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO = saltWaterInjectionPlantMapper.toDto(saltWaterInjectionPlant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaltWaterInjectionPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saltWaterInjectionPlantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SaltWaterInjectionPlant in the database
        List<SaltWaterInjectionPlant> saltWaterInjectionPlantList = saltWaterInjectionPlantRepository.findAll();
        assertThat(saltWaterInjectionPlantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSaltWaterInjectionPlant() throws Exception {
        int databaseSizeBeforeUpdate = saltWaterInjectionPlantRepository.findAll().size();
        saltWaterInjectionPlant.setId(count.incrementAndGet());

        // Create the SaltWaterInjectionPlant
        SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO = saltWaterInjectionPlantMapper.toDto(saltWaterInjectionPlant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaltWaterInjectionPlantMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saltWaterInjectionPlantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SaltWaterInjectionPlant in the database
        List<SaltWaterInjectionPlant> saltWaterInjectionPlantList = saltWaterInjectionPlantRepository.findAll();
        assertThat(saltWaterInjectionPlantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSaltWaterInjectionPlant() throws Exception {
        // Initialize the database
        saltWaterInjectionPlantRepository.saveAndFlush(saltWaterInjectionPlant);

        int databaseSizeBeforeDelete = saltWaterInjectionPlantRepository.findAll().size();

        // Delete the saltWaterInjectionPlant
        restSaltWaterInjectionPlantMockMvc
            .perform(delete(ENTITY_API_URL_ID, saltWaterInjectionPlant.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SaltWaterInjectionPlant> saltWaterInjectionPlantList = saltWaterInjectionPlantRepository.findAll();
        assertThat(saltWaterInjectionPlantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
