package com.pae.well.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pae.well.IntegrationTest;
import com.pae.well.domain.ManagementUnit;
import com.pae.well.repository.ManagementUnitRepository;
import com.pae.well.service.dto.ManagementUnitDTO;
import com.pae.well.service.mapper.ManagementUnitMapper;
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
 * Integration tests for the {@link ManagementUnitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ManagementUnitResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/management-units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ManagementUnitRepository managementUnitRepository;

    @Autowired
    private ManagementUnitMapper managementUnitMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restManagementUnitMockMvc;

    private ManagementUnit managementUnit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ManagementUnit createEntity(EntityManager em) {
        ManagementUnit managementUnit = new ManagementUnit().name(DEFAULT_NAME);
        return managementUnit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ManagementUnit createUpdatedEntity(EntityManager em) {
        ManagementUnit managementUnit = new ManagementUnit().name(UPDATED_NAME);
        return managementUnit;
    }

    @BeforeEach
    public void initTest() {
        managementUnit = createEntity(em);
    }

    @Test
    @Transactional
    void createManagementUnit() throws Exception {
        int databaseSizeBeforeCreate = managementUnitRepository.findAll().size();
        // Create the ManagementUnit
        ManagementUnitDTO managementUnitDTO = managementUnitMapper.toDto(managementUnit);
        restManagementUnitMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managementUnitDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ManagementUnit in the database
        List<ManagementUnit> managementUnitList = managementUnitRepository.findAll();
        assertThat(managementUnitList).hasSize(databaseSizeBeforeCreate + 1);
        ManagementUnit testManagementUnit = managementUnitList.get(managementUnitList.size() - 1);
        assertThat(testManagementUnit.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createManagementUnitWithExistingId() throws Exception {
        // Create the ManagementUnit with an existing ID
        managementUnit.setId(1L);
        ManagementUnitDTO managementUnitDTO = managementUnitMapper.toDto(managementUnit);

        int databaseSizeBeforeCreate = managementUnitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restManagementUnitMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managementUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementUnit in the database
        List<ManagementUnit> managementUnitList = managementUnitRepository.findAll();
        assertThat(managementUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = managementUnitRepository.findAll().size();
        // set the field null
        managementUnit.setName(null);

        // Create the ManagementUnit, which fails.
        ManagementUnitDTO managementUnitDTO = managementUnitMapper.toDto(managementUnit);

        restManagementUnitMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managementUnitDTO))
            )
            .andExpect(status().isBadRequest());

        List<ManagementUnit> managementUnitList = managementUnitRepository.findAll();
        assertThat(managementUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllManagementUnits() throws Exception {
        // Initialize the database
        managementUnitRepository.saveAndFlush(managementUnit);

        // Get all the managementUnitList
        restManagementUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(managementUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getManagementUnit() throws Exception {
        // Initialize the database
        managementUnitRepository.saveAndFlush(managementUnit);

        // Get the managementUnit
        restManagementUnitMockMvc
            .perform(get(ENTITY_API_URL_ID, managementUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(managementUnit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingManagementUnit() throws Exception {
        // Get the managementUnit
        restManagementUnitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewManagementUnit() throws Exception {
        // Initialize the database
        managementUnitRepository.saveAndFlush(managementUnit);

        int databaseSizeBeforeUpdate = managementUnitRepository.findAll().size();

        // Update the managementUnit
        ManagementUnit updatedManagementUnit = managementUnitRepository.findById(managementUnit.getId()).get();
        // Disconnect from session so that the updates on updatedManagementUnit are not directly saved in db
        em.detach(updatedManagementUnit);
        updatedManagementUnit.name(UPDATED_NAME);
        ManagementUnitDTO managementUnitDTO = managementUnitMapper.toDto(updatedManagementUnit);

        restManagementUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, managementUnitDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managementUnitDTO))
            )
            .andExpect(status().isOk());

        // Validate the ManagementUnit in the database
        List<ManagementUnit> managementUnitList = managementUnitRepository.findAll();
        assertThat(managementUnitList).hasSize(databaseSizeBeforeUpdate);
        ManagementUnit testManagementUnit = managementUnitList.get(managementUnitList.size() - 1);
        assertThat(testManagementUnit.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingManagementUnit() throws Exception {
        int databaseSizeBeforeUpdate = managementUnitRepository.findAll().size();
        managementUnit.setId(count.incrementAndGet());

        // Create the ManagementUnit
        ManagementUnitDTO managementUnitDTO = managementUnitMapper.toDto(managementUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManagementUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, managementUnitDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managementUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementUnit in the database
        List<ManagementUnit> managementUnitList = managementUnitRepository.findAll();
        assertThat(managementUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchManagementUnit() throws Exception {
        int databaseSizeBeforeUpdate = managementUnitRepository.findAll().size();
        managementUnit.setId(count.incrementAndGet());

        // Create the ManagementUnit
        ManagementUnitDTO managementUnitDTO = managementUnitMapper.toDto(managementUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagementUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managementUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementUnit in the database
        List<ManagementUnit> managementUnitList = managementUnitRepository.findAll();
        assertThat(managementUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamManagementUnit() throws Exception {
        int databaseSizeBeforeUpdate = managementUnitRepository.findAll().size();
        managementUnit.setId(count.incrementAndGet());

        // Create the ManagementUnit
        ManagementUnitDTO managementUnitDTO = managementUnitMapper.toDto(managementUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagementUnitMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managementUnitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ManagementUnit in the database
        List<ManagementUnit> managementUnitList = managementUnitRepository.findAll();
        assertThat(managementUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateManagementUnitWithPatch() throws Exception {
        // Initialize the database
        managementUnitRepository.saveAndFlush(managementUnit);

        int databaseSizeBeforeUpdate = managementUnitRepository.findAll().size();

        // Update the managementUnit using partial update
        ManagementUnit partialUpdatedManagementUnit = new ManagementUnit();
        partialUpdatedManagementUnit.setId(managementUnit.getId());

        partialUpdatedManagementUnit.name(UPDATED_NAME);

        restManagementUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManagementUnit.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedManagementUnit))
            )
            .andExpect(status().isOk());

        // Validate the ManagementUnit in the database
        List<ManagementUnit> managementUnitList = managementUnitRepository.findAll();
        assertThat(managementUnitList).hasSize(databaseSizeBeforeUpdate);
        ManagementUnit testManagementUnit = managementUnitList.get(managementUnitList.size() - 1);
        assertThat(testManagementUnit.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateManagementUnitWithPatch() throws Exception {
        // Initialize the database
        managementUnitRepository.saveAndFlush(managementUnit);

        int databaseSizeBeforeUpdate = managementUnitRepository.findAll().size();

        // Update the managementUnit using partial update
        ManagementUnit partialUpdatedManagementUnit = new ManagementUnit();
        partialUpdatedManagementUnit.setId(managementUnit.getId());

        partialUpdatedManagementUnit.name(UPDATED_NAME);

        restManagementUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManagementUnit.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedManagementUnit))
            )
            .andExpect(status().isOk());

        // Validate the ManagementUnit in the database
        List<ManagementUnit> managementUnitList = managementUnitRepository.findAll();
        assertThat(managementUnitList).hasSize(databaseSizeBeforeUpdate);
        ManagementUnit testManagementUnit = managementUnitList.get(managementUnitList.size() - 1);
        assertThat(testManagementUnit.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingManagementUnit() throws Exception {
        int databaseSizeBeforeUpdate = managementUnitRepository.findAll().size();
        managementUnit.setId(count.incrementAndGet());

        // Create the ManagementUnit
        ManagementUnitDTO managementUnitDTO = managementUnitMapper.toDto(managementUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManagementUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, managementUnitDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(managementUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementUnit in the database
        List<ManagementUnit> managementUnitList = managementUnitRepository.findAll();
        assertThat(managementUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchManagementUnit() throws Exception {
        int databaseSizeBeforeUpdate = managementUnitRepository.findAll().size();
        managementUnit.setId(count.incrementAndGet());

        // Create the ManagementUnit
        ManagementUnitDTO managementUnitDTO = managementUnitMapper.toDto(managementUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagementUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(managementUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementUnit in the database
        List<ManagementUnit> managementUnitList = managementUnitRepository.findAll();
        assertThat(managementUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamManagementUnit() throws Exception {
        int databaseSizeBeforeUpdate = managementUnitRepository.findAll().size();
        managementUnit.setId(count.incrementAndGet());

        // Create the ManagementUnit
        ManagementUnitDTO managementUnitDTO = managementUnitMapper.toDto(managementUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagementUnitMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(managementUnitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ManagementUnit in the database
        List<ManagementUnit> managementUnitList = managementUnitRepository.findAll();
        assertThat(managementUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteManagementUnit() throws Exception {
        // Initialize the database
        managementUnitRepository.saveAndFlush(managementUnit);

        int databaseSizeBeforeDelete = managementUnitRepository.findAll().size();

        // Delete the managementUnit
        restManagementUnitMockMvc
            .perform(delete(ENTITY_API_URL_ID, managementUnit.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ManagementUnit> managementUnitList = managementUnitRepository.findAll();
        assertThat(managementUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
