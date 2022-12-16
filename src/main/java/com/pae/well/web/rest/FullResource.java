package com.pae.well.web.rest;

import com.pae.well.service.dto.FullDTO;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class FullResource {

    private final Logger log = LoggerFactory.getLogger(FullResource.class);

    @GetMapping("/full")
    public ResponseEntity<FullDTO> getFull() {
        log.debug("REST request to get Full");
        FullDTO fullDTO = new FullDTO();
        fullDTO.setId(5L);
        fullDTO.setName("Full test example");
        return ResponseUtil.wrapOrNotFound(Optional.of(fullDTO));
    }
}
