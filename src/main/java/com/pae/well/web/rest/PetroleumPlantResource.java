package com.pae.well.web.rest;

import com.pae.well.repository.PetroleumPlantRepository;
import com.pae.well.service.PetroleumPlantService;
import com.pae.well.service.dto.PetroleumPlantDTO;
import com.pae.well.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.pae.well.domain.PetroleumPlant}.
 */
@RestController
@RequestMapping("/api")
public class PetroleumPlantResource {

    private final Logger log = LoggerFactory.getLogger(PetroleumPlantResource.class);

    private static final String ENTITY_NAME = "wellPetroleumPlant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PetroleumPlantService petroleumPlantService;

    private final PetroleumPlantRepository petroleumPlantRepository;

    public PetroleumPlantResource(PetroleumPlantService petroleumPlantService, PetroleumPlantRepository petroleumPlantRepository) {
        this.petroleumPlantService = petroleumPlantService;
        this.petroleumPlantRepository = petroleumPlantRepository;
    }

    /**
     * {@code POST  /petroleum-plants} : Create a new petroleumPlant.
     *
     * @param petroleumPlantDTO the petroleumPlantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new petroleumPlantDTO, or with status {@code 400 (Bad Request)} if the petroleumPlant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/petroleum-plants")
    public ResponseEntity<PetroleumPlantDTO> createPetroleumPlant(@Valid @RequestBody PetroleumPlantDTO petroleumPlantDTO)
        throws URISyntaxException {
        log.debug("REST request to save PetroleumPlant : {}", petroleumPlantDTO);
        if (petroleumPlantDTO.getId() != null) {
            throw new BadRequestAlertException("A new petroleumPlant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PetroleumPlantDTO result = petroleumPlantService.save(petroleumPlantDTO);
        return ResponseEntity
            .created(new URI("/api/petroleum-plants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /petroleum-plants/:id} : Updates an existing petroleumPlant.
     *
     * @param id the id of the petroleumPlantDTO to save.
     * @param petroleumPlantDTO the petroleumPlantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated petroleumPlantDTO,
     * or with status {@code 400 (Bad Request)} if the petroleumPlantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the petroleumPlantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/petroleum-plants/{id}")
    public ResponseEntity<PetroleumPlantDTO> updatePetroleumPlant(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PetroleumPlantDTO petroleumPlantDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PetroleumPlant : {}, {}", id, petroleumPlantDTO);
        if (petroleumPlantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, petroleumPlantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!petroleumPlantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PetroleumPlantDTO result = petroleumPlantService.update(petroleumPlantDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, petroleumPlantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /petroleum-plants/:id} : Partial updates given fields of an existing petroleumPlant, field will ignore if it is null
     *
     * @param id the id of the petroleumPlantDTO to save.
     * @param petroleumPlantDTO the petroleumPlantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated petroleumPlantDTO,
     * or with status {@code 400 (Bad Request)} if the petroleumPlantDTO is not valid,
     * or with status {@code 404 (Not Found)} if the petroleumPlantDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the petroleumPlantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/petroleum-plants/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PetroleumPlantDTO> partialUpdatePetroleumPlant(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PetroleumPlantDTO petroleumPlantDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PetroleumPlant partially : {}, {}", id, petroleumPlantDTO);
        if (petroleumPlantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, petroleumPlantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!petroleumPlantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PetroleumPlantDTO> result = petroleumPlantService.partialUpdate(petroleumPlantDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, petroleumPlantDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /petroleum-plants} : get all the petroleumPlants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of petroleumPlants in body.
     */
    @GetMapping("/petroleum-plants")
    public List<PetroleumPlantDTO> getAllPetroleumPlants() {
        log.debug("REST request to get all PetroleumPlants");
        return petroleumPlantService.findAll();
    }

    /**
     * {@code GET  /petroleum-plants/:id} : get the "id" petroleumPlant.
     *
     * @param id the id of the petroleumPlantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the petroleumPlantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/petroleum-plants/{id}")
    public ResponseEntity<PetroleumPlantDTO> getPetroleumPlant(@PathVariable Long id) {
        log.debug("REST request to get PetroleumPlant : {}", id);
        Optional<PetroleumPlantDTO> petroleumPlantDTO = petroleumPlantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(petroleumPlantDTO);
    }

    /**
     * {@code DELETE  /petroleum-plants/:id} : delete the "id" petroleumPlant.
     *
     * @param id the id of the petroleumPlantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/petroleum-plants/{id}")
    public ResponseEntity<Void> deletePetroleumPlant(@PathVariable Long id) {
        log.debug("REST request to delete PetroleumPlant : {}", id);
        petroleumPlantService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
