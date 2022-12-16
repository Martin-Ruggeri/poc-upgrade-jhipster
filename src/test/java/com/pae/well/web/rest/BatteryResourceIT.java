package com.pae.well.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pae.well.IntegrationTest;
import com.pae.well.domain.Battery;
import com.pae.well.domain.District;
import com.pae.well.repository.BatteryRepository;
import com.pae.well.service.dto.BatteryDTO;
import com.pae.well.service.mapper.BatteryMapper;
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
 * Integration tests for the {@link BatteryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BatteryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/batteries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BatteryRepository batteryRepository;

    @Autowired
    private BatteryMapper batteryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBatteryMockMvc;

    private Battery battery;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Battery createEntity(EntityManager em) {
        Battery battery = new Battery().name(DEFAULT_NAME);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        battery.setDistrict(district);
        return battery;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Battery createUpdatedEntity(EntityManager em) {
        Battery battery = new Battery().name(UPDATED_NAME);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createUpdatedEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        battery.setDistrict(district);
        return battery;
    }

    @BeforeEach
    public void initTest() {
        battery = createEntity(em);
    }

    @Test
    @Transactional
    void createBattery() throws Exception {
        int databaseSizeBeforeCreate = batteryRepository.findAll().size();
        // Create the Battery
        BatteryDTO batteryDTO = batteryMapper.toDto(battery);
        restBatteryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(batteryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Battery in the database
        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeCreate + 1);
        Battery testBattery = batteryList.get(batteryList.size() - 1);
        assertThat(testBattery.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createBatteryWithExistingId() throws Exception {
        // Create the Battery with an existing ID
        battery.setId(1L);
        BatteryDTO batteryDTO = batteryMapper.toDto(battery);

        int databaseSizeBeforeCreate = batteryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBatteryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(batteryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Battery in the database
        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = batteryRepository.findAll().size();
        // set the field null
        battery.setName(null);

        // Create the Battery, which fails.
        BatteryDTO batteryDTO = batteryMapper.toDto(battery);

        restBatteryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(batteryDTO))
            )
            .andExpect(status().isBadRequest());

        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBatteries() throws Exception {
        // Initialize the database
        batteryRepository.saveAndFlush(battery);

        // Get all the batteryList
        restBatteryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(battery.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getBattery() throws Exception {
        // Initialize the database
        batteryRepository.saveAndFlush(battery);

        // Get the battery
        restBatteryMockMvc
            .perform(get(ENTITY_API_URL_ID, battery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(battery.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingBattery() throws Exception {
        // Get the battery
        restBatteryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBattery() throws Exception {
        // Initialize the database
        batteryRepository.saveAndFlush(battery);

        int databaseSizeBeforeUpdate = batteryRepository.findAll().size();

        // Update the battery
        Battery updatedBattery = batteryRepository.findById(battery.getId()).get();
        // Disconnect from session so that the updates on updatedBattery are not directly saved in db
        em.detach(updatedBattery);
        updatedBattery.name(UPDATED_NAME);
        BatteryDTO batteryDTO = batteryMapper.toDto(updatedBattery);

        restBatteryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, batteryDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(batteryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Battery in the database
        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeUpdate);
        Battery testBattery = batteryList.get(batteryList.size() - 1);
        assertThat(testBattery.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingBattery() throws Exception {
        int databaseSizeBeforeUpdate = batteryRepository.findAll().size();
        battery.setId(count.incrementAndGet());

        // Create the Battery
        BatteryDTO batteryDTO = batteryMapper.toDto(battery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBatteryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, batteryDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(batteryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Battery in the database
        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBattery() throws Exception {
        int databaseSizeBeforeUpdate = batteryRepository.findAll().size();
        battery.setId(count.incrementAndGet());

        // Create the Battery
        BatteryDTO batteryDTO = batteryMapper.toDto(battery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatteryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(batteryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Battery in the database
        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBattery() throws Exception {
        int databaseSizeBeforeUpdate = batteryRepository.findAll().size();
        battery.setId(count.incrementAndGet());

        // Create the Battery
        BatteryDTO batteryDTO = batteryMapper.toDto(battery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatteryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(batteryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Battery in the database
        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBatteryWithPatch() throws Exception {
        // Initialize the database
        batteryRepository.saveAndFlush(battery);

        int databaseSizeBeforeUpdate = batteryRepository.findAll().size();

        // Update the battery using partial update
        Battery partialUpdatedBattery = new Battery();
        partialUpdatedBattery.setId(battery.getId());

        restBatteryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBattery.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBattery))
            )
            .andExpect(status().isOk());

        // Validate the Battery in the database
        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeUpdate);
        Battery testBattery = batteryList.get(batteryList.size() - 1);
        assertThat(testBattery.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateBatteryWithPatch() throws Exception {
        // Initialize the database
        batteryRepository.saveAndFlush(battery);

        int databaseSizeBeforeUpdate = batteryRepository.findAll().size();

        // Update the battery using partial update
        Battery partialUpdatedBattery = new Battery();
        partialUpdatedBattery.setId(battery.getId());

        partialUpdatedBattery.name(UPDATED_NAME);

        restBatteryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBattery.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBattery))
            )
            .andExpect(status().isOk());

        // Validate the Battery in the database
        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeUpdate);
        Battery testBattery = batteryList.get(batteryList.size() - 1);
        assertThat(testBattery.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingBattery() throws Exception {
        int databaseSizeBeforeUpdate = batteryRepository.findAll().size();
        battery.setId(count.incrementAndGet());

        // Create the Battery
        BatteryDTO batteryDTO = batteryMapper.toDto(battery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBatteryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, batteryDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(batteryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Battery in the database
        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBattery() throws Exception {
        int databaseSizeBeforeUpdate = batteryRepository.findAll().size();
        battery.setId(count.incrementAndGet());

        // Create the Battery
        BatteryDTO batteryDTO = batteryMapper.toDto(battery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatteryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(batteryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Battery in the database
        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBattery() throws Exception {
        int databaseSizeBeforeUpdate = batteryRepository.findAll().size();
        battery.setId(count.incrementAndGet());

        // Create the Battery
        BatteryDTO batteryDTO = batteryMapper.toDto(battery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBatteryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(batteryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Battery in the database
        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBattery() throws Exception {
        // Initialize the database
        batteryRepository.saveAndFlush(battery);

        int databaseSizeBeforeDelete = batteryRepository.findAll().size();

        // Delete the battery
        restBatteryMockMvc
            .perform(delete(ENTITY_API_URL_ID, battery.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Battery> batteryList = batteryRepository.findAll();
        assertThat(batteryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
