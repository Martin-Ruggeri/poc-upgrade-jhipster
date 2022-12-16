package com.pae.well.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pae.well.IntegrationTest;
import com.pae.well.domain.ManagementUnit;
import com.pae.well.domain.Rig;
import com.pae.well.repository.RigRepository;
import com.pae.well.service.dto.RigDTO;
import com.pae.well.service.mapper.RigMapper;
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
 * Integration tests for the {@link RigResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RigResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rigs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RigRepository rigRepository;

    @Autowired
    private RigMapper rigMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRigMockMvc;

    private Rig rig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rig createEntity(EntityManager em) {
        Rig rig = new Rig().name(DEFAULT_NAME);
        // Add required entity
        ManagementUnit managementUnit;
        if (TestUtil.findAll(em, ManagementUnit.class).isEmpty()) {
            managementUnit = ManagementUnitResourceIT.createEntity(em);
            em.persist(managementUnit);
            em.flush();
        } else {
            managementUnit = TestUtil.findAll(em, ManagementUnit.class).get(0);
        }
        rig.setManagementUnit(managementUnit);
        return rig;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rig createUpdatedEntity(EntityManager em) {
        Rig rig = new Rig().name(UPDATED_NAME);
        // Add required entity
        ManagementUnit managementUnit;
        if (TestUtil.findAll(em, ManagementUnit.class).isEmpty()) {
            managementUnit = ManagementUnitResourceIT.createUpdatedEntity(em);
            em.persist(managementUnit);
            em.flush();
        } else {
            managementUnit = TestUtil.findAll(em, ManagementUnit.class).get(0);
        }
        rig.setManagementUnit(managementUnit);
        return rig;
    }

    @BeforeEach
    public void initTest() {
        rig = createEntity(em);
    }

    @Test
    @Transactional
    void createRig() throws Exception {
        int databaseSizeBeforeCreate = rigRepository.findAll().size();
        // Create the Rig
        RigDTO rigDTO = rigMapper.toDto(rig);
        restRigMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rigDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Rig in the database
        List<Rig> rigList = rigRepository.findAll();
        assertThat(rigList).hasSize(databaseSizeBeforeCreate + 1);
        Rig testRig = rigList.get(rigList.size() - 1);
        assertThat(testRig.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createRigWithExistingId() throws Exception {
        // Create the Rig with an existing ID
        rig.setId(1L);
        RigDTO rigDTO = rigMapper.toDto(rig);

        int databaseSizeBeforeCreate = rigRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRigMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rig in the database
        List<Rig> rigList = rigRepository.findAll();
        assertThat(rigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rigRepository.findAll().size();
        // set the field null
        rig.setName(null);

        // Create the Rig, which fails.
        RigDTO rigDTO = rigMapper.toDto(rig);

        restRigMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rigDTO))
            )
            .andExpect(status().isBadRequest());

        List<Rig> rigList = rigRepository.findAll();
        assertThat(rigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRigs() throws Exception {
        // Initialize the database
        rigRepository.saveAndFlush(rig);

        // Get all the rigList
        restRigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rig.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getRig() throws Exception {
        // Initialize the database
        rigRepository.saveAndFlush(rig);

        // Get the rig
        restRigMockMvc
            .perform(get(ENTITY_API_URL_ID, rig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rig.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingRig() throws Exception {
        // Get the rig
        restRigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRig() throws Exception {
        // Initialize the database
        rigRepository.saveAndFlush(rig);

        int databaseSizeBeforeUpdate = rigRepository.findAll().size();

        // Update the rig
        Rig updatedRig = rigRepository.findById(rig.getId()).get();
        // Disconnect from session so that the updates on updatedRig are not directly saved in db
        em.detach(updatedRig);
        updatedRig.name(UPDATED_NAME);
        RigDTO rigDTO = rigMapper.toDto(updatedRig);

        restRigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rigDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rigDTO))
            )
            .andExpect(status().isOk());

        // Validate the Rig in the database
        List<Rig> rigList = rigRepository.findAll();
        assertThat(rigList).hasSize(databaseSizeBeforeUpdate);
        Rig testRig = rigList.get(rigList.size() - 1);
        assertThat(testRig.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingRig() throws Exception {
        int databaseSizeBeforeUpdate = rigRepository.findAll().size();
        rig.setId(count.incrementAndGet());

        // Create the Rig
        RigDTO rigDTO = rigMapper.toDto(rig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rigDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rig in the database
        List<Rig> rigList = rigRepository.findAll();
        assertThat(rigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRig() throws Exception {
        int databaseSizeBeforeUpdate = rigRepository.findAll().size();
        rig.setId(count.incrementAndGet());

        // Create the Rig
        RigDTO rigDTO = rigMapper.toDto(rig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rig in the database
        List<Rig> rigList = rigRepository.findAll();
        assertThat(rigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRig() throws Exception {
        int databaseSizeBeforeUpdate = rigRepository.findAll().size();
        rig.setId(count.incrementAndGet());

        // Create the Rig
        RigDTO rigDTO = rigMapper.toDto(rig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRigMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rigDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rig in the database
        List<Rig> rigList = rigRepository.findAll();
        assertThat(rigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRigWithPatch() throws Exception {
        // Initialize the database
        rigRepository.saveAndFlush(rig);

        int databaseSizeBeforeUpdate = rigRepository.findAll().size();

        // Update the rig using partial update
        Rig partialUpdatedRig = new Rig();
        partialUpdatedRig.setId(rig.getId());

        restRigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRig.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRig))
            )
            .andExpect(status().isOk());

        // Validate the Rig in the database
        List<Rig> rigList = rigRepository.findAll();
        assertThat(rigList).hasSize(databaseSizeBeforeUpdate);
        Rig testRig = rigList.get(rigList.size() - 1);
        assertThat(testRig.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateRigWithPatch() throws Exception {
        // Initialize the database
        rigRepository.saveAndFlush(rig);

        int databaseSizeBeforeUpdate = rigRepository.findAll().size();

        // Update the rig using partial update
        Rig partialUpdatedRig = new Rig();
        partialUpdatedRig.setId(rig.getId());

        partialUpdatedRig.name(UPDATED_NAME);

        restRigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRig.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRig))
            )
            .andExpect(status().isOk());

        // Validate the Rig in the database
        List<Rig> rigList = rigRepository.findAll();
        assertThat(rigList).hasSize(databaseSizeBeforeUpdate);
        Rig testRig = rigList.get(rigList.size() - 1);
        assertThat(testRig.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingRig() throws Exception {
        int databaseSizeBeforeUpdate = rigRepository.findAll().size();
        rig.setId(count.incrementAndGet());

        // Create the Rig
        RigDTO rigDTO = rigMapper.toDto(rig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rigDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rig in the database
        List<Rig> rigList = rigRepository.findAll();
        assertThat(rigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRig() throws Exception {
        int databaseSizeBeforeUpdate = rigRepository.findAll().size();
        rig.setId(count.incrementAndGet());

        // Create the Rig
        RigDTO rigDTO = rigMapper.toDto(rig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rig in the database
        List<Rig> rigList = rigRepository.findAll();
        assertThat(rigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRig() throws Exception {
        int databaseSizeBeforeUpdate = rigRepository.findAll().size();
        rig.setId(count.incrementAndGet());

        // Create the Rig
        RigDTO rigDTO = rigMapper.toDto(rig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRigMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rigDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rig in the database
        List<Rig> rigList = rigRepository.findAll();
        assertThat(rigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRig() throws Exception {
        // Initialize the database
        rigRepository.saveAndFlush(rig);

        int databaseSizeBeforeDelete = rigRepository.findAll().size();

        // Delete the rig
        restRigMockMvc
            .perform(delete(ENTITY_API_URL_ID, rig.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rig> rigList = rigRepository.findAll();
        assertThat(rigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
