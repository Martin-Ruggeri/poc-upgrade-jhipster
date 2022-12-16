package com.pae.well.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pae.well.IntegrationTest;
import com.pae.well.domain.ExtractionMethod;
import com.pae.well.repository.ExtractionMethodRepository;
import com.pae.well.service.dto.ExtractionMethodDTO;
import com.pae.well.service.mapper.ExtractionMethodMapper;
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
 * Integration tests for the {@link ExtractionMethodResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExtractionMethodResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/extraction-methods";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExtractionMethodRepository extractionMethodRepository;

    @Autowired
    private ExtractionMethodMapper extractionMethodMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExtractionMethodMockMvc;

    private ExtractionMethod extractionMethod;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtractionMethod createEntity(EntityManager em) {
        ExtractionMethod extractionMethod = new ExtractionMethod().name(DEFAULT_NAME);
        return extractionMethod;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtractionMethod createUpdatedEntity(EntityManager em) {
        ExtractionMethod extractionMethod = new ExtractionMethod().name(UPDATED_NAME);
        return extractionMethod;
    }

    @BeforeEach
    public void initTest() {
        extractionMethod = createEntity(em);
    }

    @Test
    @Transactional
    void createExtractionMethod() throws Exception {
        int databaseSizeBeforeCreate = extractionMethodRepository.findAll().size();
        // Create the ExtractionMethod
        ExtractionMethodDTO extractionMethodDTO = extractionMethodMapper.toDto(extractionMethod);
        restExtractionMethodMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(extractionMethodDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ExtractionMethod in the database
        List<ExtractionMethod> extractionMethodList = extractionMethodRepository.findAll();
        assertThat(extractionMethodList).hasSize(databaseSizeBeforeCreate + 1);
        ExtractionMethod testExtractionMethod = extractionMethodList.get(extractionMethodList.size() - 1);
        assertThat(testExtractionMethod.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createExtractionMethodWithExistingId() throws Exception {
        // Create the ExtractionMethod with an existing ID
        extractionMethod.setId(1L);
        ExtractionMethodDTO extractionMethodDTO = extractionMethodMapper.toDto(extractionMethod);

        int databaseSizeBeforeCreate = extractionMethodRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExtractionMethodMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(extractionMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExtractionMethod in the database
        List<ExtractionMethod> extractionMethodList = extractionMethodRepository.findAll();
        assertThat(extractionMethodList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = extractionMethodRepository.findAll().size();
        // set the field null
        extractionMethod.setName(null);

        // Create the ExtractionMethod, which fails.
        ExtractionMethodDTO extractionMethodDTO = extractionMethodMapper.toDto(extractionMethod);

        restExtractionMethodMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(extractionMethodDTO))
            )
            .andExpect(status().isBadRequest());

        List<ExtractionMethod> extractionMethodList = extractionMethodRepository.findAll();
        assertThat(extractionMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllExtractionMethods() throws Exception {
        // Initialize the database
        extractionMethodRepository.saveAndFlush(extractionMethod);

        // Get all the extractionMethodList
        restExtractionMethodMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(extractionMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getExtractionMethod() throws Exception {
        // Initialize the database
        extractionMethodRepository.saveAndFlush(extractionMethod);

        // Get the extractionMethod
        restExtractionMethodMockMvc
            .perform(get(ENTITY_API_URL_ID, extractionMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(extractionMethod.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingExtractionMethod() throws Exception {
        // Get the extractionMethod
        restExtractionMethodMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingExtractionMethod() throws Exception {
        // Initialize the database
        extractionMethodRepository.saveAndFlush(extractionMethod);

        int databaseSizeBeforeUpdate = extractionMethodRepository.findAll().size();

        // Update the extractionMethod
        ExtractionMethod updatedExtractionMethod = extractionMethodRepository.findById(extractionMethod.getId()).get();
        // Disconnect from session so that the updates on updatedExtractionMethod are not directly saved in db
        em.detach(updatedExtractionMethod);
        updatedExtractionMethod.name(UPDATED_NAME);
        ExtractionMethodDTO extractionMethodDTO = extractionMethodMapper.toDto(updatedExtractionMethod);

        restExtractionMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, extractionMethodDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(extractionMethodDTO))
            )
            .andExpect(status().isOk());

        // Validate the ExtractionMethod in the database
        List<ExtractionMethod> extractionMethodList = extractionMethodRepository.findAll();
        assertThat(extractionMethodList).hasSize(databaseSizeBeforeUpdate);
        ExtractionMethod testExtractionMethod = extractionMethodList.get(extractionMethodList.size() - 1);
        assertThat(testExtractionMethod.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingExtractionMethod() throws Exception {
        int databaseSizeBeforeUpdate = extractionMethodRepository.findAll().size();
        extractionMethod.setId(count.incrementAndGet());

        // Create the ExtractionMethod
        ExtractionMethodDTO extractionMethodDTO = extractionMethodMapper.toDto(extractionMethod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExtractionMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, extractionMethodDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(extractionMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExtractionMethod in the database
        List<ExtractionMethod> extractionMethodList = extractionMethodRepository.findAll();
        assertThat(extractionMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExtractionMethod() throws Exception {
        int databaseSizeBeforeUpdate = extractionMethodRepository.findAll().size();
        extractionMethod.setId(count.incrementAndGet());

        // Create the ExtractionMethod
        ExtractionMethodDTO extractionMethodDTO = extractionMethodMapper.toDto(extractionMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExtractionMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(extractionMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExtractionMethod in the database
        List<ExtractionMethod> extractionMethodList = extractionMethodRepository.findAll();
        assertThat(extractionMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExtractionMethod() throws Exception {
        int databaseSizeBeforeUpdate = extractionMethodRepository.findAll().size();
        extractionMethod.setId(count.incrementAndGet());

        // Create the ExtractionMethod
        ExtractionMethodDTO extractionMethodDTO = extractionMethodMapper.toDto(extractionMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExtractionMethodMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(extractionMethodDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExtractionMethod in the database
        List<ExtractionMethod> extractionMethodList = extractionMethodRepository.findAll();
        assertThat(extractionMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExtractionMethodWithPatch() throws Exception {
        // Initialize the database
        extractionMethodRepository.saveAndFlush(extractionMethod);

        int databaseSizeBeforeUpdate = extractionMethodRepository.findAll().size();

        // Update the extractionMethod using partial update
        ExtractionMethod partialUpdatedExtractionMethod = new ExtractionMethod();
        partialUpdatedExtractionMethod.setId(extractionMethod.getId());

        partialUpdatedExtractionMethod.name(UPDATED_NAME);

        restExtractionMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExtractionMethod.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExtractionMethod))
            )
            .andExpect(status().isOk());

        // Validate the ExtractionMethod in the database
        List<ExtractionMethod> extractionMethodList = extractionMethodRepository.findAll();
        assertThat(extractionMethodList).hasSize(databaseSizeBeforeUpdate);
        ExtractionMethod testExtractionMethod = extractionMethodList.get(extractionMethodList.size() - 1);
        assertThat(testExtractionMethod.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateExtractionMethodWithPatch() throws Exception {
        // Initialize the database
        extractionMethodRepository.saveAndFlush(extractionMethod);

        int databaseSizeBeforeUpdate = extractionMethodRepository.findAll().size();

        // Update the extractionMethod using partial update
        ExtractionMethod partialUpdatedExtractionMethod = new ExtractionMethod();
        partialUpdatedExtractionMethod.setId(extractionMethod.getId());

        partialUpdatedExtractionMethod.name(UPDATED_NAME);

        restExtractionMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExtractionMethod.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExtractionMethod))
            )
            .andExpect(status().isOk());

        // Validate the ExtractionMethod in the database
        List<ExtractionMethod> extractionMethodList = extractionMethodRepository.findAll();
        assertThat(extractionMethodList).hasSize(databaseSizeBeforeUpdate);
        ExtractionMethod testExtractionMethod = extractionMethodList.get(extractionMethodList.size() - 1);
        assertThat(testExtractionMethod.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingExtractionMethod() throws Exception {
        int databaseSizeBeforeUpdate = extractionMethodRepository.findAll().size();
        extractionMethod.setId(count.incrementAndGet());

        // Create the ExtractionMethod
        ExtractionMethodDTO extractionMethodDTO = extractionMethodMapper.toDto(extractionMethod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExtractionMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, extractionMethodDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(extractionMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExtractionMethod in the database
        List<ExtractionMethod> extractionMethodList = extractionMethodRepository.findAll();
        assertThat(extractionMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExtractionMethod() throws Exception {
        int databaseSizeBeforeUpdate = extractionMethodRepository.findAll().size();
        extractionMethod.setId(count.incrementAndGet());

        // Create the ExtractionMethod
        ExtractionMethodDTO extractionMethodDTO = extractionMethodMapper.toDto(extractionMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExtractionMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(extractionMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExtractionMethod in the database
        List<ExtractionMethod> extractionMethodList = extractionMethodRepository.findAll();
        assertThat(extractionMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExtractionMethod() throws Exception {
        int databaseSizeBeforeUpdate = extractionMethodRepository.findAll().size();
        extractionMethod.setId(count.incrementAndGet());

        // Create the ExtractionMethod
        ExtractionMethodDTO extractionMethodDTO = extractionMethodMapper.toDto(extractionMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExtractionMethodMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(extractionMethodDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExtractionMethod in the database
        List<ExtractionMethod> extractionMethodList = extractionMethodRepository.findAll();
        assertThat(extractionMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExtractionMethod() throws Exception {
        // Initialize the database
        extractionMethodRepository.saveAndFlush(extractionMethod);

        int databaseSizeBeforeDelete = extractionMethodRepository.findAll().size();

        // Delete the extractionMethod
        restExtractionMethodMockMvc
            .perform(delete(ENTITY_API_URL_ID, extractionMethod.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExtractionMethod> extractionMethodList = extractionMethodRepository.findAll();
        assertThat(extractionMethodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
