package com.pae.well.web.rest;

import static com.pae.well.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pae.well.IntegrationTest;
import com.pae.well.domain.ExtractionMethod;
import com.pae.well.domain.Well;
import com.pae.well.domain.WellChangeExtractionMethod;
import com.pae.well.repository.WellChangeExtractionMethodRepository;
import com.pae.well.service.dto.WellChangeExtractionMethodDTO;
import com.pae.well.service.mapper.WellChangeExtractionMethodMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link WellChangeExtractionMethodResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WellChangeExtractionMethodResourceIT {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/well-change-extraction-methods";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WellChangeExtractionMethodRepository wellChangeExtractionMethodRepository;

    @Autowired
    private WellChangeExtractionMethodMapper wellChangeExtractionMethodMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWellChangeExtractionMethodMockMvc;

    private WellChangeExtractionMethod wellChangeExtractionMethod;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WellChangeExtractionMethod createEntity(EntityManager em) {
        WellChangeExtractionMethod wellChangeExtractionMethod = new WellChangeExtractionMethod().date(DEFAULT_DATE);
        // Add required entity
        Well well;
        if (TestUtil.findAll(em, Well.class).isEmpty()) {
            well = WellResourceIT.createEntity(em);
            em.persist(well);
            em.flush();
        } else {
            well = TestUtil.findAll(em, Well.class).get(0);
        }
        wellChangeExtractionMethod.setWell(well);
        // Add required entity
        ExtractionMethod extractionMethod;
        if (TestUtil.findAll(em, ExtractionMethod.class).isEmpty()) {
            extractionMethod = ExtractionMethodResourceIT.createEntity(em);
            em.persist(extractionMethod);
            em.flush();
        } else {
            extractionMethod = TestUtil.findAll(em, ExtractionMethod.class).get(0);
        }
        wellChangeExtractionMethod.setExtractionMethod(extractionMethod);
        return wellChangeExtractionMethod;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WellChangeExtractionMethod createUpdatedEntity(EntityManager em) {
        WellChangeExtractionMethod wellChangeExtractionMethod = new WellChangeExtractionMethod().date(UPDATED_DATE);
        // Add required entity
        Well well;
        if (TestUtil.findAll(em, Well.class).isEmpty()) {
            well = WellResourceIT.createUpdatedEntity(em);
            em.persist(well);
            em.flush();
        } else {
            well = TestUtil.findAll(em, Well.class).get(0);
        }
        wellChangeExtractionMethod.setWell(well);
        // Add required entity
        ExtractionMethod extractionMethod;
        if (TestUtil.findAll(em, ExtractionMethod.class).isEmpty()) {
            extractionMethod = ExtractionMethodResourceIT.createUpdatedEntity(em);
            em.persist(extractionMethod);
            em.flush();
        } else {
            extractionMethod = TestUtil.findAll(em, ExtractionMethod.class).get(0);
        }
        wellChangeExtractionMethod.setExtractionMethod(extractionMethod);
        return wellChangeExtractionMethod;
    }

    @BeforeEach
    public void initTest() {
        wellChangeExtractionMethod = createEntity(em);
    }

    @Test
    @Transactional
    void createWellChangeExtractionMethod() throws Exception {
        int databaseSizeBeforeCreate = wellChangeExtractionMethodRepository.findAll().size();
        // Create the WellChangeExtractionMethod
        WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO = wellChangeExtractionMethodMapper.toDto(wellChangeExtractionMethod);
        restWellChangeExtractionMethodMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellChangeExtractionMethodDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WellChangeExtractionMethod in the database
        List<WellChangeExtractionMethod> wellChangeExtractionMethodList = wellChangeExtractionMethodRepository.findAll();
        assertThat(wellChangeExtractionMethodList).hasSize(databaseSizeBeforeCreate + 1);
        WellChangeExtractionMethod testWellChangeExtractionMethod = wellChangeExtractionMethodList.get(
            wellChangeExtractionMethodList.size() - 1
        );
        assertThat(testWellChangeExtractionMethod.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createWellChangeExtractionMethodWithExistingId() throws Exception {
        // Create the WellChangeExtractionMethod with an existing ID
        wellChangeExtractionMethod.setId(1L);
        WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO = wellChangeExtractionMethodMapper.toDto(wellChangeExtractionMethod);

        int databaseSizeBeforeCreate = wellChangeExtractionMethodRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWellChangeExtractionMethodMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellChangeExtractionMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WellChangeExtractionMethod in the database
        List<WellChangeExtractionMethod> wellChangeExtractionMethodList = wellChangeExtractionMethodRepository.findAll();
        assertThat(wellChangeExtractionMethodList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = wellChangeExtractionMethodRepository.findAll().size();
        // set the field null
        wellChangeExtractionMethod.setDate(null);

        // Create the WellChangeExtractionMethod, which fails.
        WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO = wellChangeExtractionMethodMapper.toDto(wellChangeExtractionMethod);

        restWellChangeExtractionMethodMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellChangeExtractionMethodDTO))
            )
            .andExpect(status().isBadRequest());

        List<WellChangeExtractionMethod> wellChangeExtractionMethodList = wellChangeExtractionMethodRepository.findAll();
        assertThat(wellChangeExtractionMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWellChangeExtractionMethods() throws Exception {
        // Initialize the database
        wellChangeExtractionMethodRepository.saveAndFlush(wellChangeExtractionMethod);

        // Get all the wellChangeExtractionMethodList
        restWellChangeExtractionMethodMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wellChangeExtractionMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    void getWellChangeExtractionMethod() throws Exception {
        // Initialize the database
        wellChangeExtractionMethodRepository.saveAndFlush(wellChangeExtractionMethod);

        // Get the wellChangeExtractionMethod
        restWellChangeExtractionMethodMockMvc
            .perform(get(ENTITY_API_URL_ID, wellChangeExtractionMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wellChangeExtractionMethod.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingWellChangeExtractionMethod() throws Exception {
        // Get the wellChangeExtractionMethod
        restWellChangeExtractionMethodMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWellChangeExtractionMethod() throws Exception {
        // Initialize the database
        wellChangeExtractionMethodRepository.saveAndFlush(wellChangeExtractionMethod);

        int databaseSizeBeforeUpdate = wellChangeExtractionMethodRepository.findAll().size();

        // Update the wellChangeExtractionMethod
        WellChangeExtractionMethod updatedWellChangeExtractionMethod = wellChangeExtractionMethodRepository
            .findById(wellChangeExtractionMethod.getId())
            .get();
        // Disconnect from session so that the updates on updatedWellChangeExtractionMethod are not directly saved in db
        em.detach(updatedWellChangeExtractionMethod);
        updatedWellChangeExtractionMethod.date(UPDATED_DATE);
        WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO = wellChangeExtractionMethodMapper.toDto(
            updatedWellChangeExtractionMethod
        );

        restWellChangeExtractionMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wellChangeExtractionMethodDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellChangeExtractionMethodDTO))
            )
            .andExpect(status().isOk());

        // Validate the WellChangeExtractionMethod in the database
        List<WellChangeExtractionMethod> wellChangeExtractionMethodList = wellChangeExtractionMethodRepository.findAll();
        assertThat(wellChangeExtractionMethodList).hasSize(databaseSizeBeforeUpdate);
        WellChangeExtractionMethod testWellChangeExtractionMethod = wellChangeExtractionMethodList.get(
            wellChangeExtractionMethodList.size() - 1
        );
        assertThat(testWellChangeExtractionMethod.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingWellChangeExtractionMethod() throws Exception {
        int databaseSizeBeforeUpdate = wellChangeExtractionMethodRepository.findAll().size();
        wellChangeExtractionMethod.setId(count.incrementAndGet());

        // Create the WellChangeExtractionMethod
        WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO = wellChangeExtractionMethodMapper.toDto(wellChangeExtractionMethod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWellChangeExtractionMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wellChangeExtractionMethodDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellChangeExtractionMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WellChangeExtractionMethod in the database
        List<WellChangeExtractionMethod> wellChangeExtractionMethodList = wellChangeExtractionMethodRepository.findAll();
        assertThat(wellChangeExtractionMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWellChangeExtractionMethod() throws Exception {
        int databaseSizeBeforeUpdate = wellChangeExtractionMethodRepository.findAll().size();
        wellChangeExtractionMethod.setId(count.incrementAndGet());

        // Create the WellChangeExtractionMethod
        WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO = wellChangeExtractionMethodMapper.toDto(wellChangeExtractionMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWellChangeExtractionMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellChangeExtractionMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WellChangeExtractionMethod in the database
        List<WellChangeExtractionMethod> wellChangeExtractionMethodList = wellChangeExtractionMethodRepository.findAll();
        assertThat(wellChangeExtractionMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWellChangeExtractionMethod() throws Exception {
        int databaseSizeBeforeUpdate = wellChangeExtractionMethodRepository.findAll().size();
        wellChangeExtractionMethod.setId(count.incrementAndGet());

        // Create the WellChangeExtractionMethod
        WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO = wellChangeExtractionMethodMapper.toDto(wellChangeExtractionMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWellChangeExtractionMethodMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellChangeExtractionMethodDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WellChangeExtractionMethod in the database
        List<WellChangeExtractionMethod> wellChangeExtractionMethodList = wellChangeExtractionMethodRepository.findAll();
        assertThat(wellChangeExtractionMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWellChangeExtractionMethodWithPatch() throws Exception {
        // Initialize the database
        wellChangeExtractionMethodRepository.saveAndFlush(wellChangeExtractionMethod);

        int databaseSizeBeforeUpdate = wellChangeExtractionMethodRepository.findAll().size();

        // Update the wellChangeExtractionMethod using partial update
        WellChangeExtractionMethod partialUpdatedWellChangeExtractionMethod = new WellChangeExtractionMethod();
        partialUpdatedWellChangeExtractionMethod.setId(wellChangeExtractionMethod.getId());

        partialUpdatedWellChangeExtractionMethod.date(UPDATED_DATE);

        restWellChangeExtractionMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWellChangeExtractionMethod.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWellChangeExtractionMethod))
            )
            .andExpect(status().isOk());

        // Validate the WellChangeExtractionMethod in the database
        List<WellChangeExtractionMethod> wellChangeExtractionMethodList = wellChangeExtractionMethodRepository.findAll();
        assertThat(wellChangeExtractionMethodList).hasSize(databaseSizeBeforeUpdate);
        WellChangeExtractionMethod testWellChangeExtractionMethod = wellChangeExtractionMethodList.get(
            wellChangeExtractionMethodList.size() - 1
        );
        assertThat(testWellChangeExtractionMethod.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateWellChangeExtractionMethodWithPatch() throws Exception {
        // Initialize the database
        wellChangeExtractionMethodRepository.saveAndFlush(wellChangeExtractionMethod);

        int databaseSizeBeforeUpdate = wellChangeExtractionMethodRepository.findAll().size();

        // Update the wellChangeExtractionMethod using partial update
        WellChangeExtractionMethod partialUpdatedWellChangeExtractionMethod = new WellChangeExtractionMethod();
        partialUpdatedWellChangeExtractionMethod.setId(wellChangeExtractionMethod.getId());

        partialUpdatedWellChangeExtractionMethod.date(UPDATED_DATE);

        restWellChangeExtractionMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWellChangeExtractionMethod.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWellChangeExtractionMethod))
            )
            .andExpect(status().isOk());

        // Validate the WellChangeExtractionMethod in the database
        List<WellChangeExtractionMethod> wellChangeExtractionMethodList = wellChangeExtractionMethodRepository.findAll();
        assertThat(wellChangeExtractionMethodList).hasSize(databaseSizeBeforeUpdate);
        WellChangeExtractionMethod testWellChangeExtractionMethod = wellChangeExtractionMethodList.get(
            wellChangeExtractionMethodList.size() - 1
        );
        assertThat(testWellChangeExtractionMethod.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingWellChangeExtractionMethod() throws Exception {
        int databaseSizeBeforeUpdate = wellChangeExtractionMethodRepository.findAll().size();
        wellChangeExtractionMethod.setId(count.incrementAndGet());

        // Create the WellChangeExtractionMethod
        WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO = wellChangeExtractionMethodMapper.toDto(wellChangeExtractionMethod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWellChangeExtractionMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wellChangeExtractionMethodDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wellChangeExtractionMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WellChangeExtractionMethod in the database
        List<WellChangeExtractionMethod> wellChangeExtractionMethodList = wellChangeExtractionMethodRepository.findAll();
        assertThat(wellChangeExtractionMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWellChangeExtractionMethod() throws Exception {
        int databaseSizeBeforeUpdate = wellChangeExtractionMethodRepository.findAll().size();
        wellChangeExtractionMethod.setId(count.incrementAndGet());

        // Create the WellChangeExtractionMethod
        WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO = wellChangeExtractionMethodMapper.toDto(wellChangeExtractionMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWellChangeExtractionMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wellChangeExtractionMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WellChangeExtractionMethod in the database
        List<WellChangeExtractionMethod> wellChangeExtractionMethodList = wellChangeExtractionMethodRepository.findAll();
        assertThat(wellChangeExtractionMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWellChangeExtractionMethod() throws Exception {
        int databaseSizeBeforeUpdate = wellChangeExtractionMethodRepository.findAll().size();
        wellChangeExtractionMethod.setId(count.incrementAndGet());

        // Create the WellChangeExtractionMethod
        WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO = wellChangeExtractionMethodMapper.toDto(wellChangeExtractionMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWellChangeExtractionMethodMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wellChangeExtractionMethodDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WellChangeExtractionMethod in the database
        List<WellChangeExtractionMethod> wellChangeExtractionMethodList = wellChangeExtractionMethodRepository.findAll();
        assertThat(wellChangeExtractionMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWellChangeExtractionMethod() throws Exception {
        // Initialize the database
        wellChangeExtractionMethodRepository.saveAndFlush(wellChangeExtractionMethod);

        int databaseSizeBeforeDelete = wellChangeExtractionMethodRepository.findAll().size();

        // Delete the wellChangeExtractionMethod
        restWellChangeExtractionMethodMockMvc
            .perform(delete(ENTITY_API_URL_ID, wellChangeExtractionMethod.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WellChangeExtractionMethod> wellChangeExtractionMethodList = wellChangeExtractionMethodRepository.findAll();
        assertThat(wellChangeExtractionMethodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
