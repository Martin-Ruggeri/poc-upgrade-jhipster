package com.pae.well.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pae.well.IntegrationTest;
import com.pae.well.domain.Battery;
import com.pae.well.domain.District;
import com.pae.well.domain.Project;
import com.pae.well.domain.Rig;
import com.pae.well.domain.Well;
import com.pae.well.repository.WellRepository;
import com.pae.well.service.dto.WellDTO;
import com.pae.well.service.mapper.WellMapper;
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
 * Integration tests for the {@link WellResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WellResourceIT {

    private static final Integer DEFAULT_ID_UWI = 1;
    private static final Integer UPDATED_ID_UWI = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WELL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_WELL_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PUMP_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_PUMP_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_MANIFOLD = "AAAAAAAAAA";
    private static final String UPDATED_MANIFOLD = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_DEPOSIT = "AAAAAAAAAA";
    private static final String UPDATED_DEPOSIT = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAMPAIGN_YEAR = 1;
    private static final Integer UPDATED_CAMPAIGN_YEAR = 2;

    private static final LocalDate DEFAULT_START_UP_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_UP_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_INJECTOR = false;
    private static final Boolean UPDATED_IS_INJECTOR = true;

    private static final Double DEFAULT_STEM_TORQUE = 1D;
    private static final Double UPDATED_STEM_TORQUE = 2D;

    private static final Double DEFAULT_PUMP_CONSTANT = 1D;
    private static final Double UPDATED_PUMP_CONSTANT = 2D;

    private static final Double DEFAULT_STEM_RPM = 1D;
    private static final Double UPDATED_STEM_RPM = 2D;

    private static final Double DEFAULT_VARIATOR_FREQUENCY = 1D;
    private static final Double UPDATED_VARIATOR_FREQUENCY = 2D;

    private static final String DEFAULT_EXTRACTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EXTRACTION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRACTION_SUBTYPE = "AAAAAAAAAA";
    private static final String UPDATED_EXTRACTION_SUBTYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/wells";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WellRepository wellRepository;

    @Autowired
    private WellMapper wellMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWellMockMvc;

    private Well well;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Well createEntity(EntityManager em) {
        Well well = new Well()
            .idUwi(DEFAULT_ID_UWI)
            .name(DEFAULT_NAME)
            .wellType(DEFAULT_WELL_TYPE)
            .pumpModel(DEFAULT_PUMP_MODEL)
            .manifold(DEFAULT_MANIFOLD)
            .province(DEFAULT_PROVINCE)
            .deposit(DEFAULT_DEPOSIT)
            .campaignYear(DEFAULT_CAMPAIGN_YEAR)
            .startUpDate(DEFAULT_START_UP_DATE)
            .isInjector(DEFAULT_IS_INJECTOR)
            .stemTorque(DEFAULT_STEM_TORQUE)
            .pumpConstant(DEFAULT_PUMP_CONSTANT)
            .stemRPM(DEFAULT_STEM_RPM)
            .variatorFrequency(DEFAULT_VARIATOR_FREQUENCY)
            .extractionType(DEFAULT_EXTRACTION_TYPE)
            .extractionSubtype(DEFAULT_EXTRACTION_SUBTYPE);
        // Add required entity
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createEntity(em);
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        well.setProject(project);
        // Add required entity
        Rig rig;
        if (TestUtil.findAll(em, Rig.class).isEmpty()) {
            rig = RigResourceIT.createEntity(em);
            em.persist(rig);
            em.flush();
        } else {
            rig = TestUtil.findAll(em, Rig.class).get(0);
        }
        well.setRig(rig);
        // Add required entity
        Battery battery;
        if (TestUtil.findAll(em, Battery.class).isEmpty()) {
            battery = BatteryResourceIT.createEntity(em);
            em.persist(battery);
            em.flush();
        } else {
            battery = TestUtil.findAll(em, Battery.class).get(0);
        }
        well.setBattery(battery);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        well.setDistrict(district);
        return well;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Well createUpdatedEntity(EntityManager em) {
        Well well = new Well()
            .idUwi(UPDATED_ID_UWI)
            .name(UPDATED_NAME)
            .wellType(UPDATED_WELL_TYPE)
            .pumpModel(UPDATED_PUMP_MODEL)
            .manifold(UPDATED_MANIFOLD)
            .province(UPDATED_PROVINCE)
            .deposit(UPDATED_DEPOSIT)
            .campaignYear(UPDATED_CAMPAIGN_YEAR)
            .startUpDate(UPDATED_START_UP_DATE)
            .isInjector(UPDATED_IS_INJECTOR)
            .stemTorque(UPDATED_STEM_TORQUE)
            .pumpConstant(UPDATED_PUMP_CONSTANT)
            .stemRPM(UPDATED_STEM_RPM)
            .variatorFrequency(UPDATED_VARIATOR_FREQUENCY)
            .extractionType(UPDATED_EXTRACTION_TYPE)
            .extractionSubtype(UPDATED_EXTRACTION_SUBTYPE);
        // Add required entity
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createUpdatedEntity(em);
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        well.setProject(project);
        // Add required entity
        Rig rig;
        if (TestUtil.findAll(em, Rig.class).isEmpty()) {
            rig = RigResourceIT.createUpdatedEntity(em);
            em.persist(rig);
            em.flush();
        } else {
            rig = TestUtil.findAll(em, Rig.class).get(0);
        }
        well.setRig(rig);
        // Add required entity
        Battery battery;
        if (TestUtil.findAll(em, Battery.class).isEmpty()) {
            battery = BatteryResourceIT.createUpdatedEntity(em);
            em.persist(battery);
            em.flush();
        } else {
            battery = TestUtil.findAll(em, Battery.class).get(0);
        }
        well.setBattery(battery);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createUpdatedEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        well.setDistrict(district);
        return well;
    }

    @BeforeEach
    public void initTest() {
        well = createEntity(em);
    }

    @Test
    @Transactional
    void createWell() throws Exception {
        int databaseSizeBeforeCreate = wellRepository.findAll().size();
        // Create the Well
        WellDTO wellDTO = wellMapper.toDto(well);
        restWellMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Well in the database
        List<Well> wellList = wellRepository.findAll();
        assertThat(wellList).hasSize(databaseSizeBeforeCreate + 1);
        Well testWell = wellList.get(wellList.size() - 1);
        assertThat(testWell.getIdUwi()).isEqualTo(DEFAULT_ID_UWI);
        assertThat(testWell.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWell.getWellType()).isEqualTo(DEFAULT_WELL_TYPE);
        assertThat(testWell.getPumpModel()).isEqualTo(DEFAULT_PUMP_MODEL);
        assertThat(testWell.getManifold()).isEqualTo(DEFAULT_MANIFOLD);
        assertThat(testWell.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testWell.getDeposit()).isEqualTo(DEFAULT_DEPOSIT);
        assertThat(testWell.getCampaignYear()).isEqualTo(DEFAULT_CAMPAIGN_YEAR);
        assertThat(testWell.getStartUpDate()).isEqualTo(DEFAULT_START_UP_DATE);
        assertThat(testWell.getIsInjector()).isEqualTo(DEFAULT_IS_INJECTOR);
        assertThat(testWell.getStemTorque()).isEqualTo(DEFAULT_STEM_TORQUE);
        assertThat(testWell.getPumpConstant()).isEqualTo(DEFAULT_PUMP_CONSTANT);
        assertThat(testWell.getStemRPM()).isEqualTo(DEFAULT_STEM_RPM);
        assertThat(testWell.getVariatorFrequency()).isEqualTo(DEFAULT_VARIATOR_FREQUENCY);
        assertThat(testWell.getExtractionType()).isEqualTo(DEFAULT_EXTRACTION_TYPE);
        assertThat(testWell.getExtractionSubtype()).isEqualTo(DEFAULT_EXTRACTION_SUBTYPE);
    }

    @Test
    @Transactional
    void createWellWithExistingId() throws Exception {
        // Create the Well with an existing ID
        well.setId(1L);
        WellDTO wellDTO = wellMapper.toDto(well);

        int databaseSizeBeforeCreate = wellRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWellMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Well in the database
        List<Well> wellList = wellRepository.findAll();
        assertThat(wellList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdUwiIsRequired() throws Exception {
        int databaseSizeBeforeTest = wellRepository.findAll().size();
        // set the field null
        well.setIdUwi(null);

        // Create the Well, which fails.
        WellDTO wellDTO = wellMapper.toDto(well);

        restWellMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellDTO))
            )
            .andExpect(status().isBadRequest());

        List<Well> wellList = wellRepository.findAll();
        assertThat(wellList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWells() throws Exception {
        // Initialize the database
        wellRepository.saveAndFlush(well);

        // Get all the wellList
        restWellMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(well.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUwi").value(hasItem(DEFAULT_ID_UWI)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].wellType").value(hasItem(DEFAULT_WELL_TYPE)))
            .andExpect(jsonPath("$.[*].pumpModel").value(hasItem(DEFAULT_PUMP_MODEL)))
            .andExpect(jsonPath("$.[*].manifold").value(hasItem(DEFAULT_MANIFOLD)))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE)))
            .andExpect(jsonPath("$.[*].deposit").value(hasItem(DEFAULT_DEPOSIT)))
            .andExpect(jsonPath("$.[*].campaignYear").value(hasItem(DEFAULT_CAMPAIGN_YEAR)))
            .andExpect(jsonPath("$.[*].startUpDate").value(hasItem(DEFAULT_START_UP_DATE.toString())))
            .andExpect(jsonPath("$.[*].isInjector").value(hasItem(DEFAULT_IS_INJECTOR.booleanValue())))
            .andExpect(jsonPath("$.[*].stemTorque").value(hasItem(DEFAULT_STEM_TORQUE.doubleValue())))
            .andExpect(jsonPath("$.[*].pumpConstant").value(hasItem(DEFAULT_PUMP_CONSTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].stemRPM").value(hasItem(DEFAULT_STEM_RPM.doubleValue())))
            .andExpect(jsonPath("$.[*].variatorFrequency").value(hasItem(DEFAULT_VARIATOR_FREQUENCY.doubleValue())))
            .andExpect(jsonPath("$.[*].extractionType").value(hasItem(DEFAULT_EXTRACTION_TYPE)))
            .andExpect(jsonPath("$.[*].extractionSubtype").value(hasItem(DEFAULT_EXTRACTION_SUBTYPE)));
    }

    @Test
    @Transactional
    void getWell() throws Exception {
        // Initialize the database
        wellRepository.saveAndFlush(well);

        // Get the well
        restWellMockMvc
            .perform(get(ENTITY_API_URL_ID, well.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(well.getId().intValue()))
            .andExpect(jsonPath("$.idUwi").value(DEFAULT_ID_UWI))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.wellType").value(DEFAULT_WELL_TYPE))
            .andExpect(jsonPath("$.pumpModel").value(DEFAULT_PUMP_MODEL))
            .andExpect(jsonPath("$.manifold").value(DEFAULT_MANIFOLD))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE))
            .andExpect(jsonPath("$.deposit").value(DEFAULT_DEPOSIT))
            .andExpect(jsonPath("$.campaignYear").value(DEFAULT_CAMPAIGN_YEAR))
            .andExpect(jsonPath("$.startUpDate").value(DEFAULT_START_UP_DATE.toString()))
            .andExpect(jsonPath("$.isInjector").value(DEFAULT_IS_INJECTOR.booleanValue()))
            .andExpect(jsonPath("$.stemTorque").value(DEFAULT_STEM_TORQUE.doubleValue()))
            .andExpect(jsonPath("$.pumpConstant").value(DEFAULT_PUMP_CONSTANT.doubleValue()))
            .andExpect(jsonPath("$.stemRPM").value(DEFAULT_STEM_RPM.doubleValue()))
            .andExpect(jsonPath("$.variatorFrequency").value(DEFAULT_VARIATOR_FREQUENCY.doubleValue()))
            .andExpect(jsonPath("$.extractionType").value(DEFAULT_EXTRACTION_TYPE))
            .andExpect(jsonPath("$.extractionSubtype").value(DEFAULT_EXTRACTION_SUBTYPE));
    }

    @Test
    @Transactional
    void getNonExistingWell() throws Exception {
        // Get the well
        restWellMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWell() throws Exception {
        // Initialize the database
        wellRepository.saveAndFlush(well);

        int databaseSizeBeforeUpdate = wellRepository.findAll().size();

        // Update the well
        Well updatedWell = wellRepository.findById(well.getId()).get();
        // Disconnect from session so that the updates on updatedWell are not directly saved in db
        em.detach(updatedWell);
        updatedWell
            .idUwi(UPDATED_ID_UWI)
            .name(UPDATED_NAME)
            .wellType(UPDATED_WELL_TYPE)
            .pumpModel(UPDATED_PUMP_MODEL)
            .manifold(UPDATED_MANIFOLD)
            .province(UPDATED_PROVINCE)
            .deposit(UPDATED_DEPOSIT)
            .campaignYear(UPDATED_CAMPAIGN_YEAR)
            .startUpDate(UPDATED_START_UP_DATE)
            .isInjector(UPDATED_IS_INJECTOR)
            .stemTorque(UPDATED_STEM_TORQUE)
            .pumpConstant(UPDATED_PUMP_CONSTANT)
            .stemRPM(UPDATED_STEM_RPM)
            .variatorFrequency(UPDATED_VARIATOR_FREQUENCY)
            .extractionType(UPDATED_EXTRACTION_TYPE)
            .extractionSubtype(UPDATED_EXTRACTION_SUBTYPE);
        WellDTO wellDTO = wellMapper.toDto(updatedWell);

        restWellMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wellDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellDTO))
            )
            .andExpect(status().isOk());

        // Validate the Well in the database
        List<Well> wellList = wellRepository.findAll();
        assertThat(wellList).hasSize(databaseSizeBeforeUpdate);
        Well testWell = wellList.get(wellList.size() - 1);
        assertThat(testWell.getIdUwi()).isEqualTo(UPDATED_ID_UWI);
        assertThat(testWell.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWell.getWellType()).isEqualTo(UPDATED_WELL_TYPE);
        assertThat(testWell.getPumpModel()).isEqualTo(UPDATED_PUMP_MODEL);
        assertThat(testWell.getManifold()).isEqualTo(UPDATED_MANIFOLD);
        assertThat(testWell.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testWell.getDeposit()).isEqualTo(UPDATED_DEPOSIT);
        assertThat(testWell.getCampaignYear()).isEqualTo(UPDATED_CAMPAIGN_YEAR);
        assertThat(testWell.getStartUpDate()).isEqualTo(UPDATED_START_UP_DATE);
        assertThat(testWell.getIsInjector()).isEqualTo(UPDATED_IS_INJECTOR);
        assertThat(testWell.getStemTorque()).isEqualTo(UPDATED_STEM_TORQUE);
        assertThat(testWell.getPumpConstant()).isEqualTo(UPDATED_PUMP_CONSTANT);
        assertThat(testWell.getStemRPM()).isEqualTo(UPDATED_STEM_RPM);
        assertThat(testWell.getVariatorFrequency()).isEqualTo(UPDATED_VARIATOR_FREQUENCY);
        assertThat(testWell.getExtractionType()).isEqualTo(UPDATED_EXTRACTION_TYPE);
        assertThat(testWell.getExtractionSubtype()).isEqualTo(UPDATED_EXTRACTION_SUBTYPE);
    }

    @Test
    @Transactional
    void putNonExistingWell() throws Exception {
        int databaseSizeBeforeUpdate = wellRepository.findAll().size();
        well.setId(count.incrementAndGet());

        // Create the Well
        WellDTO wellDTO = wellMapper.toDto(well);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWellMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wellDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Well in the database
        List<Well> wellList = wellRepository.findAll();
        assertThat(wellList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWell() throws Exception {
        int databaseSizeBeforeUpdate = wellRepository.findAll().size();
        well.setId(count.incrementAndGet());

        // Create the Well
        WellDTO wellDTO = wellMapper.toDto(well);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWellMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wellDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Well in the database
        List<Well> wellList = wellRepository.findAll();
        assertThat(wellList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWell() throws Exception {
        int databaseSizeBeforeUpdate = wellRepository.findAll().size();
        well.setId(count.incrementAndGet());

        // Create the Well
        WellDTO wellDTO = wellMapper.toDto(well);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWellMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wellDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Well in the database
        List<Well> wellList = wellRepository.findAll();
        assertThat(wellList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWellWithPatch() throws Exception {
        // Initialize the database
        wellRepository.saveAndFlush(well);

        int databaseSizeBeforeUpdate = wellRepository.findAll().size();

        // Update the well using partial update
        Well partialUpdatedWell = new Well();
        partialUpdatedWell.setId(well.getId());

        partialUpdatedWell
            .idUwi(UPDATED_ID_UWI)
            .name(UPDATED_NAME)
            .wellType(UPDATED_WELL_TYPE)
            .pumpModel(UPDATED_PUMP_MODEL)
            .manifold(UPDATED_MANIFOLD)
            .campaignYear(UPDATED_CAMPAIGN_YEAR)
            .stemTorque(UPDATED_STEM_TORQUE)
            .variatorFrequency(UPDATED_VARIATOR_FREQUENCY);

        restWellMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWell.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWell))
            )
            .andExpect(status().isOk());

        // Validate the Well in the database
        List<Well> wellList = wellRepository.findAll();
        assertThat(wellList).hasSize(databaseSizeBeforeUpdate);
        Well testWell = wellList.get(wellList.size() - 1);
        assertThat(testWell.getIdUwi()).isEqualTo(UPDATED_ID_UWI);
        assertThat(testWell.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWell.getWellType()).isEqualTo(UPDATED_WELL_TYPE);
        assertThat(testWell.getPumpModel()).isEqualTo(UPDATED_PUMP_MODEL);
        assertThat(testWell.getManifold()).isEqualTo(UPDATED_MANIFOLD);
        assertThat(testWell.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testWell.getDeposit()).isEqualTo(DEFAULT_DEPOSIT);
        assertThat(testWell.getCampaignYear()).isEqualTo(UPDATED_CAMPAIGN_YEAR);
        assertThat(testWell.getStartUpDate()).isEqualTo(DEFAULT_START_UP_DATE);
        assertThat(testWell.getIsInjector()).isEqualTo(DEFAULT_IS_INJECTOR);
        assertThat(testWell.getStemTorque()).isEqualTo(UPDATED_STEM_TORQUE);
        assertThat(testWell.getPumpConstant()).isEqualTo(DEFAULT_PUMP_CONSTANT);
        assertThat(testWell.getStemRPM()).isEqualTo(DEFAULT_STEM_RPM);
        assertThat(testWell.getVariatorFrequency()).isEqualTo(UPDATED_VARIATOR_FREQUENCY);
        assertThat(testWell.getExtractionType()).isEqualTo(DEFAULT_EXTRACTION_TYPE);
        assertThat(testWell.getExtractionSubtype()).isEqualTo(DEFAULT_EXTRACTION_SUBTYPE);
    }

    @Test
    @Transactional
    void fullUpdateWellWithPatch() throws Exception {
        // Initialize the database
        wellRepository.saveAndFlush(well);

        int databaseSizeBeforeUpdate = wellRepository.findAll().size();

        // Update the well using partial update
        Well partialUpdatedWell = new Well();
        partialUpdatedWell.setId(well.getId());

        partialUpdatedWell
            .idUwi(UPDATED_ID_UWI)
            .name(UPDATED_NAME)
            .wellType(UPDATED_WELL_TYPE)
            .pumpModel(UPDATED_PUMP_MODEL)
            .manifold(UPDATED_MANIFOLD)
            .province(UPDATED_PROVINCE)
            .deposit(UPDATED_DEPOSIT)
            .campaignYear(UPDATED_CAMPAIGN_YEAR)
            .startUpDate(UPDATED_START_UP_DATE)
            .isInjector(UPDATED_IS_INJECTOR)
            .stemTorque(UPDATED_STEM_TORQUE)
            .pumpConstant(UPDATED_PUMP_CONSTANT)
            .stemRPM(UPDATED_STEM_RPM)
            .variatorFrequency(UPDATED_VARIATOR_FREQUENCY)
            .extractionType(UPDATED_EXTRACTION_TYPE)
            .extractionSubtype(UPDATED_EXTRACTION_SUBTYPE);

        restWellMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWell.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWell))
            )
            .andExpect(status().isOk());

        // Validate the Well in the database
        List<Well> wellList = wellRepository.findAll();
        assertThat(wellList).hasSize(databaseSizeBeforeUpdate);
        Well testWell = wellList.get(wellList.size() - 1);
        assertThat(testWell.getIdUwi()).isEqualTo(UPDATED_ID_UWI);
        assertThat(testWell.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWell.getWellType()).isEqualTo(UPDATED_WELL_TYPE);
        assertThat(testWell.getPumpModel()).isEqualTo(UPDATED_PUMP_MODEL);
        assertThat(testWell.getManifold()).isEqualTo(UPDATED_MANIFOLD);
        assertThat(testWell.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testWell.getDeposit()).isEqualTo(UPDATED_DEPOSIT);
        assertThat(testWell.getCampaignYear()).isEqualTo(UPDATED_CAMPAIGN_YEAR);
        assertThat(testWell.getStartUpDate()).isEqualTo(UPDATED_START_UP_DATE);
        assertThat(testWell.getIsInjector()).isEqualTo(UPDATED_IS_INJECTOR);
        assertThat(testWell.getStemTorque()).isEqualTo(UPDATED_STEM_TORQUE);
        assertThat(testWell.getPumpConstant()).isEqualTo(UPDATED_PUMP_CONSTANT);
        assertThat(testWell.getStemRPM()).isEqualTo(UPDATED_STEM_RPM);
        assertThat(testWell.getVariatorFrequency()).isEqualTo(UPDATED_VARIATOR_FREQUENCY);
        assertThat(testWell.getExtractionType()).isEqualTo(UPDATED_EXTRACTION_TYPE);
        assertThat(testWell.getExtractionSubtype()).isEqualTo(UPDATED_EXTRACTION_SUBTYPE);
    }

    @Test
    @Transactional
    void patchNonExistingWell() throws Exception {
        int databaseSizeBeforeUpdate = wellRepository.findAll().size();
        well.setId(count.incrementAndGet());

        // Create the Well
        WellDTO wellDTO = wellMapper.toDto(well);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWellMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wellDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wellDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Well in the database
        List<Well> wellList = wellRepository.findAll();
        assertThat(wellList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWell() throws Exception {
        int databaseSizeBeforeUpdate = wellRepository.findAll().size();
        well.setId(count.incrementAndGet());

        // Create the Well
        WellDTO wellDTO = wellMapper.toDto(well);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWellMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wellDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Well in the database
        List<Well> wellList = wellRepository.findAll();
        assertThat(wellList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWell() throws Exception {
        int databaseSizeBeforeUpdate = wellRepository.findAll().size();
        well.setId(count.incrementAndGet());

        // Create the Well
        WellDTO wellDTO = wellMapper.toDto(well);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWellMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wellDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Well in the database
        List<Well> wellList = wellRepository.findAll();
        assertThat(wellList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWell() throws Exception {
        // Initialize the database
        wellRepository.saveAndFlush(well);

        int databaseSizeBeforeDelete = wellRepository.findAll().size();

        // Delete the well
        restWellMockMvc
            .perform(delete(ENTITY_API_URL_ID, well.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Well> wellList = wellRepository.findAll();
        assertThat(wellList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
