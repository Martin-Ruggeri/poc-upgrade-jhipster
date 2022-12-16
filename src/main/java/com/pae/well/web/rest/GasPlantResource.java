package com.pae.well.web.rest;

import com.pae.well.repository.GasPlantRepository;
import com.pae.well.service.GasPlantService;
import com.pae.well.service.dto.GasPlantDTO;
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
 * REST controller for managing {@link com.pae.well.domain.GasPlant}.
 */
@RestController
@RequestMapping("/api")
public class GasPlantResource {

    private final Logger log = LoggerFactory.getLogger(GasPlantResource.class);

    private static final String ENTITY_NAME = "wellGasPlant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GasPlantService gasPlantService;

    private final GasPlantRepository gasPlantRepository;

    public GasPlantResource(GasPlantService gasPlantService, GasPlantRepository gasPlantRepository) {
        this.gasPlantService = gasPlantService;
        this.gasPlantRepository = gasPlantRepository;
    }

    /**
     * {@code POST  /gas-plants} : Create a new gasPlant.
     *
     * @param gasPlantDTO the gasPlantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gasPlantDTO, or with status {@code 400 (Bad Request)} if the gasPlant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gas-plants")
    public ResponseEntity<GasPlantDTO> createGasPlant(@Valid @RequestBody GasPlantDTO gasPlantDTO) throws URISyntaxException {
        log.debug("REST request to save GasPlant : {}", gasPlantDTO);
        if (gasPlantDTO.getId() != null) {
            throw new BadRequestAlertException("A new gasPlant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GasPlantDTO result = gasPlantService.save(gasPlantDTO);
        return ResponseEntity
            .created(new URI("/api/gas-plants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gas-plants/:id} : Updates an existing gasPlant.
     *
     * @param id the id of the gasPlantDTO to save.
     * @param gasPlantDTO the gasPlantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gasPlantDTO,
     * or with status {@code 400 (Bad Request)} if the gasPlantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gasPlantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gas-plants/{id}")
    public ResponseEntity<GasPlantDTO> updateGasPlant(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GasPlantDTO gasPlantDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GasPlant : {}, {}", id, gasPlantDTO);
        if (gasPlantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gasPlantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gasPlantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GasPlantDTO result = gasPlantService.update(gasPlantDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gasPlantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gas-plants/:id} : Partial updates given fields of an existing gasPlant, field will ignore if it is null
     *
     * @param id the id of the gasPlantDTO to save.
     * @param gasPlantDTO the gasPlantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gasPlantDTO,
     * or with status {@code 400 (Bad Request)} if the gasPlantDTO is not valid,
     * or with status {@code 404 (Not Found)} if the gasPlantDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the gasPlantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gas-plants/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GasPlantDTO> partialUpdateGasPlant(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GasPlantDTO gasPlantDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GasPlant partially : {}, {}", id, gasPlantDTO);
        if (gasPlantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gasPlantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gasPlantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GasPlantDTO> result = gasPlantService.partialUpdate(gasPlantDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gasPlantDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /gas-plants} : get all the gasPlants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gasPlants in body.
     */
    @GetMapping("/gas-plants")
    public List<GasPlantDTO> getAllGasPlants() {
        log.debug("REST request to get all GasPlants");
        return gasPlantService.findAll();
    }

    /**
     * {@code GET  /gas-plants/:id} : get the "id" gasPlant.
     *
     * @param id the id of the gasPlantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gasPlantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gas-plants/{id}")
    public ResponseEntity<GasPlantDTO> getGasPlant(@PathVariable Long id) {
        log.debug("REST request to get GasPlant : {}", id);
        Optional<GasPlantDTO> gasPlantDTO = gasPlantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gasPlantDTO);
    }

    /**
     * {@code DELETE  /gas-plants/:id} : delete the "id" gasPlant.
     *
     * @param id the id of the gasPlantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gas-plants/{id}")
    public ResponseEntity<Void> deleteGasPlant(@PathVariable Long id) {
        log.debug("REST request to delete GasPlant : {}", id);
        gasPlantService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
