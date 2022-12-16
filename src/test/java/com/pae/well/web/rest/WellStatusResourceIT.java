package com.pae.well.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pae.well.IntegrationTest;
import com.pae.well.domain.WellStatus;
import com.pae.well.repository.WellStatusRepository;
import com.pae.well.service.dto.WellStatusDTO;
import com.pae.well.service.mapper.WellStatusMapper;
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
 * Integration tests for the {@link WellStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WellStatusResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/well-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WellStatusRepository wellStatusRepository;

    @Autowired
    private WellStatusMapper wellStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWellStatusMockMvc;

    private WellStatus wellStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WellStatus createEntity(EntityManager em) {
        WellStatus wellStatus = new WellStatus().name(DEFAULT_NAME);
        return wellStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WellStatus createUpdatedEntity(EntityManager em) {
        WellStatus wellStatus = new WellStatus().name(UPDATED_NAME);
        return wellStatus;
    }

    @BeforeEach
    public void initTest() {
        wellStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createWellStatus() throws Exception {
        int databaseSizeBeforeCreate = wellStatusRepository.findAll().size();
        // Create the WellStatus
        WellStatusDTO wellStatusDTO = wellStatusMapper.toDto(wellStatus);
        restWellStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellStatusDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WellStatus in the database
        List<WellStatus> wellStatusList = wellStatusRepository.findAll();
        assertThat(wellStatusList).hasSize(databaseSizeBeforeCreate + 1);
        WellStatus testWellStatus = wellStatusList.get(wellStatusList.size() - 1);
        assertThat(testWellStatus.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createWellStatusWithExistingId() throws Exception {
        // Create the WellStatus with an existing ID
        wellStatus.setId(1L);
        WellStatusDTO wellStatusDTO = wellStatusMapper.toDto(wellStatus);

        int databaseSizeBeforeCreate = wellStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWellStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WellStatus in the database
        List<WellStatus> wellStatusList = wellStatusRepository.findAll();
        assertThat(wellStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = wellStatusRepository.findAll().size();
        // set the field null
        wellStatus.setName(null);

        // Create the WellStatus, which fails.
        WellStatusDTO wellStatusDTO = wellStatusMapper.toDto(wellStatus);

        restWellStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<WellStatus> wellStatusList = wellStatusRepository.findAll();
        assertThat(wellStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWellStatuses() throws Exception {
        // Initialize the database
        wellStatusRepository.saveAndFlush(wellStatus);

        // Get all the wellStatusList
        restWellStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wellStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getWellStatus() throws Exception {
        // Initialize the database
        wellStatusRepository.saveAndFlush(wellStatus);

        // Get the wellStatus
        restWellStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, wellStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wellStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingWellStatus() throws Exception {
        // Get the wellStatus
        restWellStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWellStatus() throws Exception {
        // Initialize the database
        wellStatusRepository.saveAndFlush(wellStatus);

        int databaseSizeBeforeUpdate = wellStatusRepository.findAll().size();

        // Update the wellStatus
        WellStatus updatedWellStatus = wellStatusRepository.findById(wellStatus.getId()).get();
        // Disconnect from session so that the updates on updatedWellStatus are not directly saved in db
        em.detach(updatedWellStatus);
        updatedWellStatus.name(UPDATED_NAME);
        WellStatusDTO wellStatusDTO = wellStatusMapper.toDto(updatedWellStatus);

        restWellStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wellStatusDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the WellStatus in the database
        List<WellStatus> wellStatusList = wellStatusRepository.findAll();
        assertThat(wellStatusList).hasSize(databaseSizeBeforeUpdate);
        WellStatus testWellStatus = wellStatusList.get(wellStatusList.size() - 1);
        assertThat(testWellStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingWellStatus() throws Exception {
        int databaseSizeBeforeUpdate = wellStatusRepository.findAll().size();
        wellStatus.setId(count.incrementAndGet());

        // Create the WellStatus
        WellStatusDTO wellStatusDTO = wellStatusMapper.toDto(wellStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWellStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wellStatusDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WellStatus in the database
        List<WellStatus> wellStatusList = wellStatusRepository.findAll();
        assertThat(wellStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWellStatus() throws Exception {
        int databaseSizeBeforeUpdate = wellStatusRepository.findAll().size();
        wellStatus.setId(count.incrementAndGet());

        // Create the WellStatus
        WellStatusDTO wellStatusDTO = wellStatusMapper.toDto(wellStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWellStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WellStatus in the database
        List<WellStatus> wellStatusList = wellStatusRepository.findAll();
        assertThat(wellStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWellStatus() throws Exception {
        int databaseSizeBeforeUpdate = wellStatusRepository.findAll().size();
        wellStatus.setId(count.incrementAndGet());

        // Create the WellStatus
        WellStatusDTO wellStatusDTO = wellStatusMapper.toDto(wellStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWellStatusMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WellStatus in the database
        List<WellStatus> wellStatusList = wellStatusRepository.findAll();
        assertThat(wellStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWellStatusWithPatch() throws Exception {
        // Initialize the database
        wellStatusRepository.saveAndFlush(wellStatus);

        int databaseSizeBeforeUpdate = wellStatusRepository.findAll().size();

        // Update the wellStatus using partial update
        WellStatus partialUpdatedWellStatus = new WellStatus();
        partialUpdatedWellStatus.setId(wellStatus.getId());

        partialUpdatedWellStatus.name(UPDATED_NAME);

        restWellStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWellStatus.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWellStatus))
            )
            .andExpect(status().isOk());

        // Validate the WellStatus in the database
        List<WellStatus> wellStatusList = wellStatusRepository.findAll();
        assertThat(wellStatusList).hasSize(databaseSizeBeforeUpdate);
        WellStatus testWellStatus = wellStatusList.get(wellStatusList.size() - 1);
        assertThat(testWellStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateWellStatusWithPatch() throws Exception {
        // Initialize the database
        wellStatusRepository.saveAndFlush(wellStatus);

        int databaseSizeBeforeUpdate = wellStatusRepository.findAll().size();

        // Update the wellStatus using partial update
        WellStatus partialUpdatedWellStatus = new WellStatus();
        partialUpdatedWellStatus.setId(wellStatus.getId());

        partialUpdatedWellStatus.name(UPDATED_NAME);

        restWellStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWellStatus.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWellStatus))
            )
            .andExpect(status().isOk());

        // Validate the WellStatus in the database
        List<WellStatus> wellStatusList = wellStatusRepository.findAll();
        assertThat(wellStatusList).hasSize(databaseSizeBeforeUpdate);
        WellStatus testWellStatus = wellStatusList.get(wellStatusList.size() - 1);
        assertThat(testWellStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingWellStatus() throws Exception {
        int databaseSizeBeforeUpdate = wellStatusRepository.findAll().size();
        wellStatus.setId(count.incrementAndGet());

        // Create the WellStatus
        WellStatusDTO wellStatusDTO = wellStatusMapper.toDto(wellStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWellStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wellStatusDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wellStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WellStatus in the database
        List<WellStatus> wellStatusList = wellStatusRepository.findAll();
        assertThat(wellStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWellStatus() throws Exception {
        int databaseSizeBeforeUpdate = wellStatusRepository.findAll().size();
        wellStatus.setId(count.incrementAndGet());

        // Create the WellStatus
        WellStatusDTO wellStatusDTO = wellStatusMapper.toDto(wellStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWellStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wellStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WellStatus in the database
        List<WellStatus> wellStatusList = wellStatusRepository.findAll();
        assertThat(wellStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWellStatus() throws Exception {
        int databaseSizeBeforeUpdate = wellStatusRepository.findAll().size();
        wellStatus.setId(count.incrementAndGet());

        // Create the WellStatus
        WellStatusDTO wellStatusDTO = wellStatusMapper.toDto(wellStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWellStatusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wellStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WellStatus in the database
        List<WellStatus> wellStatusList = wellStatusRepository.findAll();
        assertThat(wellStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWellStatus() throws Exception {
        // Initialize the database
        wellStatusRepository.saveAndFlush(wellStatus);

        int databaseSizeBeforeDelete = wellStatusRepository.findAll().size();

        // Delete the wellStatus
        restWellStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, wellStatus.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WellStatus> wellStatusList = wellStatusRepository.findAll();
        assertThat(wellStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
