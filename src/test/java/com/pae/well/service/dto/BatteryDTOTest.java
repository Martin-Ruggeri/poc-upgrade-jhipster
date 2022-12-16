package com.pae.well.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BatteryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BatteryDTO.class);
        BatteryDTO batteryDTO1 = new BatteryDTO();
        batteryDTO1.setId(1L);
        BatteryDTO batteryDTO2 = new BatteryDTO();
        assertThat(batteryDTO1).isNotEqualTo(batteryDTO2);
        batteryDTO2.setId(batteryDTO1.getId());
        assertThat(batteryDTO1).isEqualTo(batteryDTO2);
        batteryDTO2.setId(2L);
        assertThat(batteryDTO1).isNotEqualTo(batteryDTO2);
        batteryDTO1.setId(null);
        assertThat(batteryDTO1).isNotEqualTo(batteryDTO2);
    }
}
