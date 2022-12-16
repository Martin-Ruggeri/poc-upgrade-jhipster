package com.pae.well.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pae.well.IntegrationTest;
import com.pae.well.domain.AdditionalData;
import com.pae.well.domain.Well;
import com.pae.well.repository.AdditionalDataRepository;
import com.pae.well.service.dto.AdditionalDataDTO;
import com.pae.well.service.mapper.AdditionalDataMapper;
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
 * Integration tests for the {@link AdditionalDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdditionalDataResourceIT {

    private static final String DEFAULT_GENERAL_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_GENERAL_COMMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/additional-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdditionalDataRepository additionalDataRepository;

    @Autowired
    private AdditionalDataMapper additionalDataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdditionalDataMockMvc;

    private AdditionalData additionalData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdditionalData createEntity(EntityManager em) {
        AdditionalData additionalData = new AdditionalData().generalComment(DEFAULT_GENERAL_COMMENT);
        // Add required entity
        Well well;
        if (TestUtil.findAll(em, Well.class).isEmpty()) {
            well = WellResourceIT.createEntity(em);
            em.persist(well);
            em.flush();
        } else {
            well = TestUtil.findAll(em, Well.class).get(0);
        }
        additionalData.setWell(well);
        return additionalData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdditionalData createUpdatedEntity(EntityManager em) {
        AdditionalData additionalData = new AdditionalData().generalComment(UPDATED_GENERAL_COMMENT);
        // Add required entity
        Well well;
        if (TestUtil.findAll(em, Well.class).isEmpty()) {
            well = WellResourceIT.createUpdatedEntity(em);
            em.persist(well);
            em.flush();
        } else {
            well = TestUtil.findAll(em, Well.class).get(0);
        }
        additionalData.setWell(well);
        return additionalData;
    }

    @BeforeEach
    public void initTest() {
        additionalData = createEntity(em);
    }

    @Test
    @Transactional
    void createAdditionalData() throws Exception {
        int databaseSizeBeforeCreate = additionalDataRepository.findAll().size();
        // Create the AdditionalData
        AdditionalDataDTO additionalDataDTO = additionalDataMapper.toDto(additionalData);
        restAdditionalDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AdditionalData in the database
        List<AdditionalData> additionalDataList = additionalDataRepository.findAll();
        assertThat(additionalDataList).hasSize(databaseSizeBeforeCreate + 1);
        AdditionalData testAdditionalData = additionalDataList.get(additionalDataList.size() - 1);
        assertThat(testAdditionalData.getGeneralComment()).isEqualTo(DEFAULT_GENERAL_COMMENT);
    }

    @Test
    @Transactional
    void createAdditionalDataWithExistingId() throws Exception {
        // Create the AdditionalData with an existing ID
        additionalData.setId(1L);
        AdditionalDataDTO additionalDataDTO = additionalDataMapper.toDto(additionalData);

        int databaseSizeBeforeCreate = additionalDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdditionalDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalData in the database
        List<AdditionalData> additionalDataList = additionalDataRepository.findAll();
        assertThat(additionalDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdditionalData() throws Exception {
        // Initialize the database
        additionalDataRepository.saveAndFlush(additionalData);

        // Get all the additionalDataList
        restAdditionalDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(additionalData.getId().intValue())))
            .andExpect(jsonPath("$.[*].generalComment").value(hasItem(DEFAULT_GENERAL_COMMENT)));
    }

    @Test
    @Transactional
    void getAdditionalData() throws Exception {
        // Initialize the database
        additionalDataRepository.saveAndFlush(additionalData);

        // Get the additionalData
        restAdditionalDataMockMvc
            .perform(get(ENTITY_API_URL_ID, additionalData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(additionalData.getId().intValue()))
            .andExpect(jsonPath("$.generalComment").value(DEFAULT_GENERAL_COMMENT));
    }

    @Test
    @Transactional
    void getNonExistingAdditionalData() throws Exception {
        // Get the additionalData
        restAdditionalDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdditionalData() throws Exception {
        // Initialize the database
        additionalDataRepository.saveAndFlush(additionalData);

        int databaseSizeBeforeUpdate = additionalDataRepository.findAll().size();

        // Update the additionalData
        AdditionalData updatedAdditionalData = additionalDataRepository.findById(additionalData.getId()).get();
        // Disconnect from session so that the updates on updatedAdditionalData are not directly saved in db
        em.detach(updatedAdditionalData);
        updatedAdditionalData.generalComment(UPDATED_GENERAL_COMMENT);
        AdditionalDataDTO additionalDataDTO = additionalDataMapper.toDto(updatedAdditionalData);

        restAdditionalDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, additionalDataDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalData in the database
        List<AdditionalData> additionalDataList = additionalDataRepository.findAll();
        assertThat(additionalDataList).hasSize(databaseSizeBeforeUpdate);
        AdditionalData testAdditionalData = additionalDataList.get(additionalDataList.size() - 1);
        assertThat(testAdditionalData.getGeneralComment()).isEqualTo(UPDATED_GENERAL_COMMENT);
    }

    @Test
    @Transactional
    void putNonExistingAdditionalData() throws Exception {
        int databaseSizeBeforeUpdate = additionalDataRepository.findAll().size();
        additionalData.setId(count.incrementAndGet());

        // Create the AdditionalData
        AdditionalDataDTO additionalDataDTO = additionalDataMapper.toDto(additionalData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdditionalDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, additionalDataDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalData in the database
        List<AdditionalData> additionalDataList = additionalDataRepository.findAll();
        assertThat(additionalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdditionalData() throws Exception {
        int databaseSizeBeforeUpdate = additionalDataRepository.findAll().size();
        additionalData.setId(count.incrementAndGet());

        // Create the AdditionalData
        AdditionalDataDTO additionalDataDTO = additionalDataMapper.toDto(additionalData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalData in the database
        List<AdditionalData> additionalDataList = additionalDataRepository.findAll();
        assertThat(additionalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdditionalData() throws Exception {
        int databaseSizeBeforeUpdate = additionalDataRepository.findAll().size();
        additionalData.setId(count.incrementAndGet());

        // Create the AdditionalData
        AdditionalDataDTO additionalDataDTO = additionalDataMapper.toDto(additionalData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalDataMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdditionalData in the database
        List<AdditionalData> additionalDataList = additionalDataRepository.findAll();
        assertThat(additionalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdditionalDataWithPatch() throws Exception {
        // Initialize the database
        additionalDataRepository.saveAndFlush(additionalData);

        int databaseSizeBeforeUpdate = additionalDataRepository.findAll().size();

        // Update the additionalData using partial update
        AdditionalData partialUpdatedAdditionalData = new AdditionalData();
        partialUpdatedAdditionalData.setId(additionalData.getId());

        restAdditionalDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdditionalData.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdditionalData))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalData in the database
        List<AdditionalData> additionalDataList = additionalDataRepository.findAll();
        assertThat(additionalDataList).hasSize(databaseSizeBeforeUpdate);
        AdditionalData testAdditionalData = additionalDataList.get(additionalDataList.size() - 1);
        assertThat(testAdditionalData.getGeneralComment()).isEqualTo(DEFAULT_GENERAL_COMMENT);
    }

    @Test
    @Transactional
    void fullUpdateAdditionalDataWithPatch() throws Exception {
        // Initialize the database
        additionalDataRepository.saveAndFlush(additionalData);

        int databaseSizeBeforeUpdate = additionalDataRepository.findAll().size();

        // Update the additionalData using partial update
        AdditionalData partialUpdatedAdditionalData = new AdditionalData();
        partialUpdatedAdditionalData.setId(additionalData.getId());

        partialUpdatedAdditionalData.generalComment(UPDATED_GENERAL_COMMENT);

        restAdditionalDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdditionalData.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdditionalData))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalData in the database
        List<AdditionalData> additionalDataList = additionalDataRepository.findAll();
        assertThat(additionalDataList).hasSize(databaseSizeBeforeUpdate);
        AdditionalData testAdditionalData = additionalDataList.get(additionalDataList.size() - 1);
        assertThat(testAdditionalData.getGeneralComment()).isEqualTo(UPDATED_GENERAL_COMMENT);
    }

    @Test
    @Transactional
    void patchNonExistingAdditionalData() throws Exception {
        int databaseSizeBeforeUpdate = additionalDataRepository.findAll().size();
        additionalData.setId(count.incrementAndGet());

        // Create the AdditionalData
        AdditionalDataDTO additionalDataDTO = additionalDataMapper.toDto(additionalData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdditionalDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, additionalDataDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalData in the database
        List<AdditionalData> additionalDataList = additionalDataRepository.findAll();
        assertThat(additionalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdditionalData() throws Exception {
        int databaseSizeBeforeUpdate = additionalDataRepository.findAll().size();
        additionalData.setId(count.incrementAndGet());

        // Create the AdditionalData
        AdditionalDataDTO additionalDataDTO = additionalDataMapper.toDto(additionalData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalData in the database
        List<AdditionalData> additionalDataList = additionalDataRepository.findAll();
        assertThat(additionalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdditionalData() throws Exception {
        int databaseSizeBeforeUpdate = additionalDataRepository.findAll().size();
        additionalData.setId(count.incrementAndGet());

        // Create the AdditionalData
        AdditionalDataDTO additionalDataDTO = additionalDataMapper.toDto(additionalData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalDataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdditionalData in the database
        List<AdditionalData> additionalDataList = additionalDataRepository.findAll();
        assertThat(additionalDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdditionalData() throws Exception {
        // Initialize the database
        additionalDataRepository.saveAndFlush(additionalData);

        int databaseSizeBeforeDelete = additionalDataRepository.findAll().size();

        // Delete the additionalData
        restAdditionalDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, additionalData.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdditionalData> additionalDataList = additionalDataRepository.findAll();
        assertThat(additionalDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
