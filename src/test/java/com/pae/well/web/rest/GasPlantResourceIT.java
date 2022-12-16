package com.pae.well.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pae.well.IntegrationTest;
import com.pae.well.domain.District;
import com.pae.well.domain.GasPlant;
import com.pae.well.repository.GasPlantRepository;
import com.pae.well.service.dto.GasPlantDTO;
import com.pae.well.service.mapper.GasPlantMapper;
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
 * Integration tests for the {@link GasPlantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GasPlantResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gas-plants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GasPlantRepository gasPlantRepository;

    @Autowired
    private GasPlantMapper gasPlantMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGasPlantMockMvc;

    private GasPlant gasPlant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GasPlant createEntity(EntityManager em) {
        GasPlant gasPlant = new GasPlant().name(DEFAULT_NAME);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        gasPlant.setDistrict(district);
        return gasPlant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GasPlant createUpdatedEntity(EntityManager em) {
        GasPlant gasPlant = new GasPlant().name(UPDATED_NAME);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createUpdatedEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        gasPlant.setDistrict(district);
        return gasPlant;
    }

    @BeforeEach
    public void initTest() {
        gasPlant = createEntity(em);
    }

    @Test
    @Transactional
    void createGasPlant() throws Exception {
        int databaseSizeBeforeCreate = gasPlantRepository.findAll().size();
        // Create the GasPlant
        GasPlantDTO gasPlantDTO = gasPlantMapper.toDto(gasPlant);
        restGasPlantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gasPlantDTO))
            )
            .andExpect(status().isCreated());

        // Validate the GasPlant in the database
        List<GasPlant> gasPlantList = gasPlantRepository.findAll();
        assertThat(gasPlantList).hasSize(databaseSizeBeforeCreate + 1);
        GasPlant testGasPlant = gasPlantList.get(gasPlantList.size() - 1);
        assertThat(testGasPlant.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createGasPlantWithExistingId() throws Exception {
        // Create the GasPlant with an existing ID
        gasPlant.setId(1L);
        GasPlantDTO gasPlantDTO = gasPlantMapper.toDto(gasPlant);

        int databaseSizeBeforeCreate = gasPlantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGasPlantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gasPlantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GasPlant in the database
        List<GasPlant> gasPlantList = gasPlantRepository.findAll();
        assertThat(gasPlantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = gasPlantRepository.findAll().size();
        // set the field null
        gasPlant.setName(null);

        // Create the GasPlant, which fails.
        GasPlantDTO gasPlantDTO = gasPlantMapper.toDto(gasPlant);

        restGasPlantMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gasPlantDTO))
            )
            .andExpect(status().isBadRequest());

        List<GasPlant> gasPlantList = gasPlantRepository.findAll();
        assertThat(gasPlantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGasPlants() throws Exception {
        // Initialize the database
        gasPlantRepository.saveAndFlush(gasPlant);

        // Get all the gasPlantList
        restGasPlantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gasPlant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getGasPlant() throws Exception {
        // Initialize the database
        gasPlantRepository.saveAndFlush(gasPlant);

        // Get the gasPlant
        restGasPlantMockMvc
            .perform(get(ENTITY_API_URL_ID, gasPlant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gasPlant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingGasPlant() throws Exception {
        // Get the gasPlant
        restGasPlantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGasPlant() throws Exception {
        // Initialize the database
        gasPlantRepository.saveAndFlush(gasPlant);

        int databaseSizeBeforeUpdate = gasPlantRepository.findAll().size();

        // Update the gasPlant
        GasPlant updatedGasPlant = gasPlantRepository.findById(gasPlant.getId()).get();
        // Disconnect from session so that the updates on updatedGasPlant are not directly saved in db
        em.detach(updatedGasPlant);
        updatedGasPlant.name(UPDATED_NAME);
        GasPlantDTO gasPlantDTO = gasPlantMapper.toDto(updatedGasPlant);

        restGasPlantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gasPlantDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gasPlantDTO))
            )
            .andExpect(status().isOk());

        // Validate the GasPlant in the database
        List<GasPlant> gasPlantList = gasPlantRepository.findAll();
        assertThat(gasPlantList).hasSize(databaseSizeBeforeUpdate);
        GasPlant testGasPlant = gasPlantList.get(gasPlantList.size() - 1);
        assertThat(testGasPlant.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingGasPlant() throws Exception {
        int databaseSizeBeforeUpdate = gasPlantRepository.findAll().size();
        gasPlant.setId(count.incrementAndGet());

        // Create the GasPlant
        GasPlantDTO gasPlantDTO = gasPlantMapper.toDto(gasPlant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGasPlantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gasPlantDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gasPlantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GasPlant in the database
        List<GasPlant> gasPlantList = gasPlantRepository.findAll();
        assertThat(gasPlantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGasPlant() throws Exception {
        int databaseSizeBeforeUpdate = gasPlantRepository.findAll().size();
        gasPlant.setId(count.incrementAndGet());

        // Create the GasPlant
        GasPlantDTO gasPlantDTO = gasPlantMapper.toDto(gasPlant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGasPlantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gasPlantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GasPlant in the database
        List<GasPlant> gasPlantList = gasPlantRepository.findAll();
        assertThat(gasPlantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGasPlant() throws Exception {
        int databaseSizeBeforeUpdate = gasPlantRepository.findAll().size();
        gasPlant.setId(count.incrementAndGet());

        // Create the GasPlant
        GasPlantDTO gasPlantDTO = gasPlantMapper.toDto(gasPlant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGasPlantMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gasPlantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GasPlant in the database
        List<GasPlant> gasPlantList = gasPlantRepository.findAll();
        assertThat(gasPlantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGasPlantWithPatch() throws Exception {
        // Initialize the database
        gasPlantRepository.saveAndFlush(gasPlant);

        int databaseSizeBeforeUpdate = gasPlantRepository.findAll().size();

        // Update the gasPlant using partial update
        GasPlant partialUpdatedGasPlant = new GasPlant();
        partialUpdatedGasPlant.setId(gasPlant.getId());

        partialUpdatedGasPlant.name(UPDATED_NAME);

        restGasPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGasPlant.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGasPlant))
            )
            .andExpect(status().isOk());

        // Validate the GasPlant in the database
        List<GasPlant> gasPlantList = gasPlantRepository.findAll();
        assertThat(gasPlantList).hasSize(databaseSizeBeforeUpdate);
        GasPlant testGasPlant = gasPlantList.get(gasPlantList.size() - 1);
        assertThat(testGasPlant.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateGasPlantWithPatch() throws Exception {
        // Initialize the database
        gasPlantRepository.saveAndFlush(gasPlant);

        int databaseSizeBeforeUpdate = gasPlantRepository.findAll().size();

        // Update the gasPlant using partial update
        GasPlant partialUpdatedGasPlant = new GasPlant();
        partialUpdatedGasPlant.setId(gasPlant.getId());

        partialUpdatedGasPlant.name(UPDATED_NAME);

        restGasPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGasPlant.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGasPlant))
            )
            .andExpect(status().isOk());

        // Validate the GasPlant in the database
        List<GasPlant> gasPlantList = gasPlantRepository.findAll();
        assertThat(gasPlantList).hasSize(databaseSizeBeforeUpdate);
        GasPlant testGasPlant = gasPlantList.get(gasPlantList.size() - 1);
        assertThat(testGasPlant.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingGasPlant() throws Exception {
        int databaseSizeBeforeUpdate = gasPlantRepository.findAll().size();
        gasPlant.setId(count.incrementAndGet());

        // Create the GasPlant
        GasPlantDTO gasPlantDTO = gasPlantMapper.toDto(gasPlant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGasPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gasPlantDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gasPlantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GasPlant in the database
        List<GasPlant> gasPlantList = gasPlantRepository.findAll();
        assertThat(gasPlantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGasPlant() throws Exception {
        int databaseSizeBeforeUpdate = gasPlantRepository.findAll().size();
        gasPlant.setId(count.incrementAndGet());

        // Create the GasPlant
        GasPlantDTO gasPlantDTO = gasPlantMapper.toDto(gasPlant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGasPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gasPlantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GasPlant in the database
        List<GasPlant> gasPlantList = gasPlantRepository.findAll();
        assertThat(gasPlantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGasPlant() throws Exception {
        int databaseSizeBeforeUpdate = gasPlantRepository.findAll().size();
        gasPlant.setId(count.incrementAndGet());

        // Create the GasPlant
        GasPlantDTO gasPlantDTO = gasPlantMapper.toDto(gasPlant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGasPlantMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gasPlantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GasPlant in the database
        List<GasPlant> gasPlantList = gasPlantRepository.findAll();
        assertThat(gasPlantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGasPlant() throws Exception {
        // Initialize the database
        gasPlantRepository.saveAndFlush(gasPlant);

        int databaseSizeBeforeDelete = gasPlantRepository.findAll().size();

        // Delete the gasPlant
        restGasPlantMockMvc
            .perform(delete(ENTITY_API_URL_ID, gasPlant.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GasPlant> gasPlantList = gasPlantRepository.findAll();
        assertThat(gasPlantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
