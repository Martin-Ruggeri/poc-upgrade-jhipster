package com.pae.well.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pae.well.IntegrationTest;
import com.pae.well.domain.AdditionalDataItemDescription;
import com.pae.well.repository.AdditionalDataItemDescriptionRepository;
import com.pae.well.service.dto.AdditionalDataItemDescriptionDTO;
import com.pae.well.service.mapper.AdditionalDataItemDescriptionMapper;
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
 * Integration tests for the {@link AdditionalDataItemDescriptionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdditionalDataItemDescriptionResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ARE_REQUIRED_DATE_USER_AND_COMMENT = false;
    private static final Boolean UPDATED_ARE_REQUIRED_DATE_USER_AND_COMMENT = true;

    private static final String ENTITY_API_URL = "/api/additional-data-item-descriptions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdditionalDataItemDescriptionRepository additionalDataItemDescriptionRepository;

    @Autowired
    private AdditionalDataItemDescriptionMapper additionalDataItemDescriptionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdditionalDataItemDescriptionMockMvc;

    private AdditionalDataItemDescription additionalDataItemDescription;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdditionalDataItemDescription createEntity(EntityManager em) {
        AdditionalDataItemDescription additionalDataItemDescription = new AdditionalDataItemDescription()
            .description(DEFAULT_DESCRIPTION)
            .areRequiredDateUserAndComment(DEFAULT_ARE_REQUIRED_DATE_USER_AND_COMMENT);
        return additionalDataItemDescription;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdditionalDataItemDescription createUpdatedEntity(EntityManager em) {
        AdditionalDataItemDescription additionalDataItemDescription = new AdditionalDataItemDescription()
            .description(UPDATED_DESCRIPTION)
            .areRequiredDateUserAndComment(UPDATED_ARE_REQUIRED_DATE_USER_AND_COMMENT);
        return additionalDataItemDescription;
    }

    @BeforeEach
    public void initTest() {
        additionalDataItemDescription = createEntity(em);
    }

    @Test
    @Transactional
    void createAdditionalDataItemDescription() throws Exception {
        int databaseSizeBeforeCreate = additionalDataItemDescriptionRepository.findAll().size();
        // Create the AdditionalDataItemDescription
        AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO = additionalDataItemDescriptionMapper.toDto(
            additionalDataItemDescription
        );
        restAdditionalDataItemDescriptionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDescriptionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AdditionalDataItemDescription in the database
        List<AdditionalDataItemDescription> additionalDataItemDescriptionList = additionalDataItemDescriptionRepository.findAll();
        assertThat(additionalDataItemDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        AdditionalDataItemDescription testAdditionalDataItemDescription = additionalDataItemDescriptionList.get(
            additionalDataItemDescriptionList.size() - 1
        );
        assertThat(testAdditionalDataItemDescription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAdditionalDataItemDescription.getAreRequiredDateUserAndComment())
            .isEqualTo(DEFAULT_ARE_REQUIRED_DATE_USER_AND_COMMENT);
    }

    @Test
    @Transactional
    void createAdditionalDataItemDescriptionWithExistingId() throws Exception {
        // Create the AdditionalDataItemDescription with an existing ID
        additionalDataItemDescription.setId(1L);
        AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO = additionalDataItemDescriptionMapper.toDto(
            additionalDataItemDescription
        );

        int databaseSizeBeforeCreate = additionalDataItemDescriptionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdditionalDataItemDescriptionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDescriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalDataItemDescription in the database
        List<AdditionalDataItemDescription> additionalDataItemDescriptionList = additionalDataItemDescriptionRepository.findAll();
        assertThat(additionalDataItemDescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = additionalDataItemDescriptionRepository.findAll().size();
        // set the field null
        additionalDataItemDescription.setDescription(null);

        // Create the AdditionalDataItemDescription, which fails.
        AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO = additionalDataItemDescriptionMapper.toDto(
            additionalDataItemDescription
        );

        restAdditionalDataItemDescriptionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDescriptionDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdditionalDataItemDescription> additionalDataItemDescriptionList = additionalDataItemDescriptionRepository.findAll();
        assertThat(additionalDataItemDescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdditionalDataItemDescriptions() throws Exception {
        // Initialize the database
        additionalDataItemDescriptionRepository.saveAndFlush(additionalDataItemDescription);

        // Get all the additionalDataItemDescriptionList
        restAdditionalDataItemDescriptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(additionalDataItemDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(
                jsonPath("$.[*].areRequiredDateUserAndComment").value(hasItem(DEFAULT_ARE_REQUIRED_DATE_USER_AND_COMMENT.booleanValue()))
            );
    }

    @Test
    @Transactional
    void getAdditionalDataItemDescription() throws Exception {
        // Initialize the database
        additionalDataItemDescriptionRepository.saveAndFlush(additionalDataItemDescription);

        // Get the additionalDataItemDescription
        restAdditionalDataItemDescriptionMockMvc
            .perform(get(ENTITY_API_URL_ID, additionalDataItemDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(additionalDataItemDescription.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.areRequiredDateUserAndComment").value(DEFAULT_ARE_REQUIRED_DATE_USER_AND_COMMENT.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAdditionalDataItemDescription() throws Exception {
        // Get the additionalDataItemDescription
        restAdditionalDataItemDescriptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAdditionalDataItemDescription() throws Exception {
        // Initialize the database
        additionalDataItemDescriptionRepository.saveAndFlush(additionalDataItemDescription);

        int databaseSizeBeforeUpdate = additionalDataItemDescriptionRepository.findAll().size();

        // Update the additionalDataItemDescription
        AdditionalDataItemDescription updatedAdditionalDataItemDescription = additionalDataItemDescriptionRepository
            .findById(additionalDataItemDescription.getId())
            .get();
        // Disconnect from session so that the updates on updatedAdditionalDataItemDescription are not directly saved in db
        em.detach(updatedAdditionalDataItemDescription);
        updatedAdditionalDataItemDescription
            .description(UPDATED_DESCRIPTION)
            .areRequiredDateUserAndComment(UPDATED_ARE_REQUIRED_DATE_USER_AND_COMMENT);
        AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO = additionalDataItemDescriptionMapper.toDto(
            updatedAdditionalDataItemDescription
        );

        restAdditionalDataItemDescriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, additionalDataItemDescriptionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDescriptionDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalDataItemDescription in the database
        List<AdditionalDataItemDescription> additionalDataItemDescriptionList = additionalDataItemDescriptionRepository.findAll();
        assertThat(additionalDataItemDescriptionList).hasSize(databaseSizeBeforeUpdate);
        AdditionalDataItemDescription testAdditionalDataItemDescription = additionalDataItemDescriptionList.get(
            additionalDataItemDescriptionList.size() - 1
        );
        assertThat(testAdditionalDataItemDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAdditionalDataItemDescription.getAreRequiredDateUserAndComment())
            .isEqualTo(UPDATED_ARE_REQUIRED_DATE_USER_AND_COMMENT);
    }

    @Test
    @Transactional
    void putNonExistingAdditionalDataItemDescription() throws Exception {
        int databaseSizeBeforeUpdate = additionalDataItemDescriptionRepository.findAll().size();
        additionalDataItemDescription.setId(count.incrementAndGet());

        // Create the AdditionalDataItemDescription
        AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO = additionalDataItemDescriptionMapper.toDto(
            additionalDataItemDescription
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdditionalDataItemDescriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, additionalDataItemDescriptionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDescriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalDataItemDescription in the database
        List<AdditionalDataItemDescription> additionalDataItemDescriptionList = additionalDataItemDescriptionRepository.findAll();
        assertThat(additionalDataItemDescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdditionalDataItemDescription() throws Exception {
        int databaseSizeBeforeUpdate = additionalDataItemDescriptionRepository.findAll().size();
        additionalDataItemDescription.setId(count.incrementAndGet());

        // Create the AdditionalDataItemDescription
        AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO = additionalDataItemDescriptionMapper.toDto(
            additionalDataItemDescription
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalDataItemDescriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDescriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalDataItemDescription in the database
        List<AdditionalDataItemDescription> additionalDataItemDescriptionList = additionalDataItemDescriptionRepository.findAll();
        assertThat(additionalDataItemDescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdditionalDataItemDescription() throws Exception {
        int databaseSizeBeforeUpdate = additionalDataItemDescriptionRepository.findAll().size();
        additionalDataItemDescription.setId(count.incrementAndGet());

        // Create the AdditionalDataItemDescription
        AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO = additionalDataItemDescriptionMapper.toDto(
            additionalDataItemDescription
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalDataItemDescriptionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDescriptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdditionalDataItemDescription in the database
        List<AdditionalDataItemDescription> additionalDataItemDescriptionList = additionalDataItemDescriptionRepository.findAll();
        assertThat(additionalDataItemDescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdditionalDataItemDescriptionWithPatch() throws Exception {
        // Initialize the database
        additionalDataItemDescriptionRepository.saveAndFlush(additionalDataItemDescription);

        int databaseSizeBeforeUpdate = additionalDataItemDescriptionRepository.findAll().size();

        // Update the additionalDataItemDescription using partial update
        AdditionalDataItemDescription partialUpdatedAdditionalDataItemDescription = new AdditionalDataItemDescription();
        partialUpdatedAdditionalDataItemDescription.setId(additionalDataItemDescription.getId());

        partialUpdatedAdditionalDataItemDescription
            .description(UPDATED_DESCRIPTION)
            .areRequiredDateUserAndComment(UPDATED_ARE_REQUIRED_DATE_USER_AND_COMMENT);

        restAdditionalDataItemDescriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdditionalDataItemDescription.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdditionalDataItemDescription))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalDataItemDescription in the database
        List<AdditionalDataItemDescription> additionalDataItemDescriptionList = additionalDataItemDescriptionRepository.findAll();
        assertThat(additionalDataItemDescriptionList).hasSize(databaseSizeBeforeUpdate);
        AdditionalDataItemDescription testAdditionalDataItemDescription = additionalDataItemDescriptionList.get(
            additionalDataItemDescriptionList.size() - 1
        );
        assertThat(testAdditionalDataItemDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAdditionalDataItemDescription.getAreRequiredDateUserAndComment())
            .isEqualTo(UPDATED_ARE_REQUIRED_DATE_USER_AND_COMMENT);
    }

    @Test
    @Transactional
    void fullUpdateAdditionalDataItemDescriptionWithPatch() throws Exception {
        // Initialize the database
        additionalDataItemDescriptionRepository.saveAndFlush(additionalDataItemDescription);

        int databaseSizeBeforeUpdate = additionalDataItemDescriptionRepository.findAll().size();

        // Update the additionalDataItemDescription using partial update
        AdditionalDataItemDescription partialUpdatedAdditionalDataItemDescription = new AdditionalDataItemDescription();
        partialUpdatedAdditionalDataItemDescription.setId(additionalDataItemDescription.getId());

        partialUpdatedAdditionalDataItemDescription
            .description(UPDATED_DESCRIPTION)
            .areRequiredDateUserAndComment(UPDATED_ARE_REQUIRED_DATE_USER_AND_COMMENT);

        restAdditionalDataItemDescriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdditionalDataItemDescription.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdditionalDataItemDescription))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalDataItemDescription in the database
        List<AdditionalDataItemDescription> additionalDataItemDescriptionList = additionalDataItemDescriptionRepository.findAll();
        assertThat(additionalDataItemDescriptionList).hasSize(databaseSizeBeforeUpdate);
        AdditionalDataItemDescription testAdditionalDataItemDescription = additionalDataItemDescriptionList.get(
            additionalDataItemDescriptionList.size() - 1
        );
        assertThat(testAdditionalDataItemDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAdditionalDataItemDescription.getAreRequiredDateUserAndComment())
            .isEqualTo(UPDATED_ARE_REQUIRED_DATE_USER_AND_COMMENT);
    }

    @Test
    @Transactional
    void patchNonExistingAdditionalDataItemDescription() throws Exception {
        int databaseSizeBeforeUpdate = additionalDataItemDescriptionRepository.findAll().size();
        additionalDataItemDescription.setId(count.incrementAndGet());

        // Create the AdditionalDataItemDescription
        AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO = additionalDataItemDescriptionMapper.toDto(
            additionalDataItemDescription
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdditionalDataItemDescriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, additionalDataItemDescriptionDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDescriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalDataItemDescription in the database
        List<AdditionalDataItemDescription> additionalDataItemDescriptionList = additionalDataItemDescriptionRepository.findAll();
        assertThat(additionalDataItemDescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdditionalDataItemDescription() throws Exception {
        int databaseSizeBeforeUpdate = additionalDataItemDescriptionRepository.findAll().size();
        additionalDataItemDescription.setId(count.incrementAndGet());

        // Create the AdditionalDataItemDescription
        AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO = additionalDataItemDescriptionMapper.toDto(
            additionalDataItemDescription
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalDataItemDescriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDescriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalDataItemDescription in the database
        List<AdditionalDataItemDescription> additionalDataItemDescriptionList = additionalDataItemDescriptionRepository.findAll();
        assertThat(additionalDataItemDescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdditionalDataItemDescription() throws Exception {
        int databaseSizeBeforeUpdate = additionalDataItemDescriptionRepository.findAll().size();
        additionalDataItemDescription.setId(count.incrementAndGet());

        // Create the AdditionalDataItemDescription
        AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO = additionalDataItemDescriptionMapper.toDto(
            additionalDataItemDescription
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalDataItemDescriptionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDescriptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdditionalDataItemDescription in the database
        List<AdditionalDataItemDescription> additionalDataItemDescriptionList = additionalDataItemDescriptionRepository.findAll();
        assertThat(additionalDataItemDescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdditionalDataItemDescription() throws Exception {
        // Initialize the database
        additionalDataItemDescriptionRepository.saveAndFlush(additionalDataItemDescription);

        int databaseSizeBeforeDelete = additionalDataItemDescriptionRepository.findAll().size();

        // Delete the additionalDataItemDescription
        restAdditionalDataItemDescriptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, additionalDataItemDescription.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdditionalDataItemDescription> additionalDataItemDescriptionList = additionalDataItemDescriptionRepository.findAll();
        assertThat(additionalDataItemDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
