package com.pae.well.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ManagementUnitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ManagementUnitDTO.class);
        ManagementUnitDTO managementUnitDTO1 = new ManagementUnitDTO();
        managementUnitDTO1.setId(1L);
        ManagementUnitDTO managementUnitDTO2 = new ManagementUnitDTO();
        assertThat(managementUnitDTO1).isNotEqualTo(managementUnitDTO2);
        managementUnitDTO2.setId(managementUnitDTO1.getId());
        assertThat(managementUnitDTO1).isEqualTo(managementUnitDTO2);
        managementUnitDTO2.setId(2L);
        assertThat(managementUnitDTO1).isNotEqualTo(managementUnitDTO2);
        managementUnitDTO1.setId(null);
        assertThat(managementUnitDTO1).isNotEqualTo(managementUnitDTO2);
    }
}
