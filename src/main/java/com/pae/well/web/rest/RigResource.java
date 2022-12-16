package com.pae.well.web.rest;

import com.pae.well.repository.RigRepository;
import com.pae.well.service.RigService;
import com.pae.well.service.dto.RigDTO;
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
 * REST controller for managing {@link com.pae.well.domain.Rig}.
 */
@RestController
@RequestMapping("/api")
public class RigResource {

    private final Logger log = LoggerFactory.getLogger(RigResource.class);

    private static final String ENTITY_NAME = "wellRig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RigService rigService;

    private final RigRepository rigRepository;

    public RigResource(RigService rigService, RigRepository rigRepository) {
        this.rigService = rigService;
        this.rigRepository = rigRepository;
    }

    /**
     * {@code POST  /rigs} : Create a new rig.
     *
     * @param rigDTO the rigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rigDTO, or with status {@code 400 (Bad Request)} if the rig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rigs")
    public ResponseEntity<RigDTO> createRig(@Valid @RequestBody RigDTO rigDTO) throws URISyntaxException {
        log.debug("REST request to save Rig : {}", rigDTO);
        if (rigDTO.getId() != null) {
            throw new BadRequestAlertException("A new rig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RigDTO result = rigService.save(rigDTO);
        return ResponseEntity
            .created(new URI("/api/rigs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rigs/:id} : Updates an existing rig.
     *
     * @param id the id of the rigDTO to save.
     * @param rigDTO the rigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rigDTO,
     * or with status {@code 400 (Bad Request)} if the rigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rigs/{id}")
    public ResponseEntity<RigDTO> updateRig(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody RigDTO rigDTO)
        throws URISyntaxException {
        log.debug("REST request to update Rig : {}, {}", id, rigDTO);
        if (rigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rigDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RigDTO result = rigService.update(rigDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rigDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rigs/:id} : Partial updates given fields of an existing rig, field will ignore if it is null
     *
     * @param id the id of the rigDTO to save.
     * @param rigDTO the rigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rigDTO,
     * or with status {@code 400 (Bad Request)} if the rigDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rigDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rigs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RigDTO> partialUpdateRig(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RigDTO rigDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rig partially : {}, {}", id, rigDTO);
        if (rigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rigDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RigDTO> result = rigService.partialUpdate(rigDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rigDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rigs} : get all the rigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rigs in body.
     */
    @GetMapping("/rigs")
    public List<RigDTO> getAllRigs() {
        log.debug("REST request to get all Rigs");
        return rigService.findAll();
    }

    /**
     * {@code GET  /rigs/:id} : get the "id" rig.
     *
     * @param id the id of the rigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rigs/{id}")
    public ResponseEntity<RigDTO> getRig(@PathVariable Long id) {
        log.debug("REST request to get Rig : {}", id);
        Optional<RigDTO> rigDTO = rigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rigDTO);
    }

    /**
     * {@code DELETE  /rigs/:id} : delete the "id" rig.
     *
     * @param id the id of the rigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rigs/{id}")
    public ResponseEntity<Void> deleteRig(@PathVariable Long id) {
        log.debug("REST request to delete Rig : {}", id);
        rigService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
