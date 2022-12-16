package com.pae.well.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pae.well.IntegrationTest;
import com.pae.well.domain.AdditionalData;
import com.pae.well.domain.AdditionalDataItem;
import com.pae.well.domain.AdditionalDataItemDescription;
import com.pae.well.repository.AdditionalDataItemRepository;
import com.pae.well.service.dto.AdditionalDataItemDTO;
import com.pae.well.service.mapper.AdditionalDataItemMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link AdditionalDataItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdditionalDataItemResourceIT {

    private static final Integer DEFAULT_ID_USER = 1;
    private static final Integer UPDATED_ID_USER = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/additional-data-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdditionalDataItemRepository additionalDataItemRepository;

    @Autowired
    private AdditionalDataItemMapper additionalDataItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdditionalDataItemMockMvc;

    private AdditionalDataItem additionalDataItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdditionalDataItem createEntity(EntityManager em) {
        AdditionalDataItem additionalDataItem = new AdditionalDataItem()
            .idUser(DEFAULT_ID_USER)
            .date(DEFAULT_DATE)
            .comment(DEFAULT_COMMENT);
        // Add required entity
        AdditionalData additionalData;
        if (TestUtil.findAll(em, AdditionalData.class).isEmpty()) {
            additionalData = AdditionalDataResourceIT.createEntity(em);
            em.persist(additionalData);
            em.flush();
        } else {
            additionalData = TestUtil.findAll(em, AdditionalData.class).get(0);
        }
        additionalDataItem.setAdditionalData(additionalData);
        // Add required entity
        AdditionalDataItemDescription additionalDataItemDescription;
        if (TestUtil.findAll(em, AdditionalDataItemDescription.class).isEmpty()) {
            additionalDataItemDescription = AdditionalDataItemDescriptionResourceIT.createEntity(em);
            em.persist(additionalDataItemDescription);
            em.flush();
        } else {
            additionalDataItemDescription = TestUtil.findAll(em, AdditionalDataItemDescription.class).get(0);
        }
        additionalDataItem.setAdditionalDataItemDescription(additionalDataItemDescription);
        return additionalDataItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdditionalDataItem createUpdatedEntity(EntityManager em) {
        AdditionalDataItem additionalDataItem = new AdditionalDataItem()
            .idUser(UPDATED_ID_USER)
            .date(UPDATED_DATE)
            .comment(UPDATED_COMMENT);
        // Add required entity
        AdditionalData additionalData;
        if (TestUtil.findAll(em, AdditionalData.class).isEmpty()) {
            additionalData = AdditionalDataResourceIT.createUpdatedEntity(em);
            em.persist(additionalData);
            em.flush();
        } else {
            additionalData = TestUtil.findAll(em, AdditionalData.class).get(0);
        }
        additionalDataItem.setAdditionalData(additionalData);
        // Add required entity
        AdditionalDataItemDescription additionalDataItemDescription;
        if (TestUtil.findAll(em, AdditionalDataItemDescription.class).isEmpty()) {
            additionalDataItemDescription = AdditionalDataItemDescriptionResourceIT.createUpdatedEntity(em);
            em.persist(additionalDataItemDescription);
            em.flush();
        } else {
            additionalDataItemDescription = TestUtil.findAll(em, AdditionalDataItemDescription.class).get(0);
        }
        additionalDataItem.setAdditionalDataItemDescription(additionalDataItemDescription);
        return additionalDataItem;
    }

    @BeforeEach
    public void initTest() {
        additionalDataItem = createEntity(em);
    }

    @Test
    @Transactional
    void createAdditionalDataItem() throws Exception {
        int databaseSizeBeforeCreate = additionalDataItemRepository.findAll().size();
        // Create the AdditionalDataItem
        AdditionalDataItemDTO additionalDataItemDTO = additionalDataItemMapper.toDto(additionalDataItem);
        restAdditionalDataItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AdditionalDataItem in the database
        List<AdditionalDataItem> additionalDataItemList = additionalDataItemRepository.findAll();
        assertThat(additionalDataItemList).hasSize(databaseSizeBeforeCreate + 1);
        AdditionalDataItem testAdditionalDataItem = additionalDataItemList.get(additionalDataItemList.size() - 1);
        assertThat(testAdditionalDataItem.getIdUser()).isEqualTo(DEFAULT_ID_USER);
        assertThat(testAdditionalDataItem.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAdditionalDataItem.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    void createAdditionalDataItemWithExistingId() throws Exception {
        // Create the AdditionalDataItem with an existing ID
        additionalDataItem.setId(1L);
        AdditionalDataItemDTO additionalDataItemDTO = additionalDataItemMapper.toDto(additionalDataItem);

        int databaseSizeBeforeCreate = additionalDataItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdditionalDataItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalDataItem in the database
        List<AdditionalDataItem> additionalDataItemList = additionalDataItemRepository.findAll();
        assertThat(additionalDataItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = additionalDataItemRepository.findAll().size();
        // set the field null
        additionalDataItem.setIdUser(null);

        // Create the AdditionalDataItem, which fails.
        AdditionalDataItemDTO additionalDataItemDTO = additionalDataItemMapper.toDto(additionalDataItem);

        restAdditionalDataItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdditionalDataItem> additionalDataItemList = additionalDataItemRepository.findAll();
        assertThat(additionalDataItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = additionalDataItemRepository.findAll().size();
        // set the field null
        additionalDataItem.setDate(null);

        // Create the AdditionalDataItem, which fails.
        AdditionalDataItemDTO additionalDataItemDTO = additionalDataItemMapper.toDto(additionalDataItem);

        restAdditionalDataItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<AdditionalDataItem> additionalDataItemList = additionalDataItemRepository.findAll();
        assertThat(additionalDataItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdditionalDataItems() throws Exception {
        // Initialize the database
        additionalDataItemRepository.saveAndFlush(additionalDataItem);

        // Get all the additionalDataItemList
        restAdditionalDataItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(additionalDataItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUser").value(hasItem(DEFAULT_ID_USER)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));
    }

    @Test
    @Transactional
    void getAdditionalDataItem() throws Exception {
        // Initialize the database
        additionalDataItemRepository.saveAndFlush(additionalDataItem);

        // Get the additionalDataItem
        restAdditionalDataItemMockMvc
            .perform(get(ENTITY_API_URL_ID, additionalDataItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(additionalDataItem.getId().intValue()))
            .andExpect(jsonPath("$.idUser").value(DEFAULT_ID_USER))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT));
    }

    @Test
    @Transactional
    void getNonExistingAdditionalDataItem() throws Exception {
        // Get the additionalDataItem
        restAdditionalDataItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAdditionalDataItem() throws Exception {
        // Initialize the database
        additionalDataItemRepository.saveAndFlush(additionalDataItem);

        int databaseSizeBeforeUpdate = additionalDataItemRepository.findAll().size();

        // Update the additionalDataItem
        AdditionalDataItem updatedAdditionalDataItem = additionalDataItemRepository.findById(additionalDataItem.getId()).get();
        // Disconnect from session so that the updates on updatedAdditionalDataItem are not directly saved in db
        em.detach(updatedAdditionalDataItem);
        updatedAdditionalDataItem.idUser(UPDATED_ID_USER).date(UPDATED_DATE).comment(UPDATED_COMMENT);
        AdditionalDataItemDTO additionalDataItemDTO = additionalDataItemMapper.toDto(updatedAdditionalDataItem);

        restAdditionalDataItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, additionalDataItemDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalDataItem in the database
        List<AdditionalDataItem> additionalDataItemList = additionalDataItemRepository.findAll();
        assertThat(additionalDataItemList).hasSize(databaseSizeBeforeUpdate);
        AdditionalDataItem testAdditionalDataItem = additionalDataItemList.get(additionalDataItemList.size() - 1);
        assertThat(testAdditionalDataItem.getIdUser()).isEqualTo(UPDATED_ID_USER);
        assertThat(testAdditionalDataItem.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAdditionalDataItem.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void putNonExistingAdditionalDataItem() throws Exception {
        int databaseSizeBeforeUpdate = additionalDataItemRepository.findAll().size();
        additionalDataItem.setId(count.incrementAndGet());

        // Create the AdditionalDataItem
        AdditionalDataItemDTO additionalDataItemDTO = additionalDataItemMapper.toDto(additionalDataItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdditionalDataItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, additionalDataItemDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalDataItem in the database
        List<AdditionalDataItem> additionalDataItemList = additionalDataItemRepository.findAll();
        assertThat(additionalDataItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdditionalDataItem() throws Exception {
        int databaseSizeBeforeUpdate = additionalDataItemRepository.findAll().size();
        additionalDataItem.setId(count.incrementAndGet());

        // Create the AdditionalDataItem
        AdditionalDataItemDTO additionalDataItemDTO = additionalDataItemMapper.toDto(additionalDataItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalDataItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalDataItem in the database
        List<AdditionalDataItem> additionalDataItemList = additionalDataItemRepository.findAll();
        assertThat(additionalDataItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdditionalDataItem() throws Exception {
        int databaseSizeBeforeUpdate = additionalDataItemRepository.findAll().size();
        additionalDataItem.setId(count.incrementAndGet());

        // Create the AdditionalDataItem
        AdditionalDataItemDTO additionalDataItemDTO = additionalDataItemMapper.toDto(additionalDataItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalDataItemMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdditionalDataItem in the database
        List<AdditionalDataItem> additionalDataItemList = additionalDataItemRepository.findAll();
        assertThat(additionalDataItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdditionalDataItemWithPatch() throws Exception {
        // Initialize the database
        additionalDataItemRepository.saveAndFlush(additionalDataItem);

        int databaseSizeBeforeUpdate = additionalDataItemRepository.findAll().size();

        // Update the additionalDataItem using partial update
        AdditionalDataItem partialUpdatedAdditionalDataItem = new AdditionalDataItem();
        partialUpdatedAdditionalDataItem.setId(additionalDataItem.getId());

        partialUpdatedAdditionalDataItem.date(UPDATED_DATE);

        restAdditionalDataItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdditionalDataItem.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdditionalDataItem))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalDataItem in the database
        List<AdditionalDataItem> additionalDataItemList = additionalDataItemRepository.findAll();
        assertThat(additionalDataItemList).hasSize(databaseSizeBeforeUpdate);
        AdditionalDataItem testAdditionalDataItem = additionalDataItemList.get(additionalDataItemList.size() - 1);
        assertThat(testAdditionalDataItem.getIdUser()).isEqualTo(DEFAULT_ID_USER);
        assertThat(testAdditionalDataItem.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAdditionalDataItem.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    void fullUpdateAdditionalDataItemWithPatch() throws Exception {
        // Initialize the database
        additionalDataItemRepository.saveAndFlush(additionalDataItem);

        int databaseSizeBeforeUpdate = additionalDataItemRepository.findAll().size();

        // Update the additionalDataItem using partial update
        AdditionalDataItem partialUpdatedAdditionalDataItem = new AdditionalDataItem();
        partialUpdatedAdditionalDataItem.setId(additionalDataItem.getId());

        partialUpdatedAdditionalDataItem.idUser(UPDATED_ID_USER).date(UPDATED_DATE).comment(UPDATED_COMMENT);

        restAdditionalDataItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdditionalDataItem.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdditionalDataItem))
            )
            .andExpect(status().isOk());

        // Validate the AdditionalDataItem in the database
        List<AdditionalDataItem> additionalDataItemList = additionalDataItemRepository.findAll();
        assertThat(additionalDataItemList).hasSize(databaseSizeBeforeUpdate);
        AdditionalDataItem testAdditionalDataItem = additionalDataItemList.get(additionalDataItemList.size() - 1);
        assertThat(testAdditionalDataItem.getIdUser()).isEqualTo(UPDATED_ID_USER);
        assertThat(testAdditionalDataItem.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAdditionalDataItem.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void patchNonExistingAdditionalDataItem() throws Exception {
        int databaseSizeBeforeUpdate = additionalDataItemRepository.findAll().size();
        additionalDataItem.setId(count.incrementAndGet());

        // Create the AdditionalDataItem
        AdditionalDataItemDTO additionalDataItemDTO = additionalDataItemMapper.toDto(additionalDataItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdditionalDataItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, additionalDataItemDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalDataItem in the database
        List<AdditionalDataItem> additionalDataItemList = additionalDataItemRepository.findAll();
        assertThat(additionalDataItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdditionalDataItem() throws Exception {
        int databaseSizeBeforeUpdate = additionalDataItemRepository.findAll().size();
        additionalDataItem.setId(count.incrementAndGet());

        // Create the AdditionalDataItem
        AdditionalDataItemDTO additionalDataItemDTO = additionalDataItemMapper.toDto(additionalDataItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalDataItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdditionalDataItem in the database
        List<AdditionalDataItem> additionalDataItemList = additionalDataItemRepository.findAll();
        assertThat(additionalDataItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdditionalDataItem() throws Exception {
        int databaseSizeBeforeUpdate = additionalDataItemRepository.findAll().size();
        additionalDataItem.setId(count.incrementAndGet());

        // Create the AdditionalDataItem
        AdditionalDataItemDTO additionalDataItemDTO = additionalDataItemMapper.toDto(additionalDataItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdditionalDataItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(additionalDataItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdditionalDataItem in the database
        List<AdditionalDataItem> additionalDataItemList = additionalDataItemRepository.findAll();
        assertThat(additionalDataItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdditionalDataItem() throws Exception {
        // Initialize the database
        additionalDataItemRepository.saveAndFlush(additionalDataItem);

        int databaseSizeBeforeDelete = additionalDataItemRepository.findAll().size();

        // Delete the additionalDataItem
        restAdditionalDataItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, additionalDataItem.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdditionalDataItem> additionalDataItemList = additionalDataItemRepository.findAll();
        assertThat(additionalDataItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
