package com.pae.well.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ManagementUnitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ManagementUnit.class);
        ManagementUnit managementUnit1 = new ManagementUnit();
        managementUnit1.setId(1L);
        ManagementUnit managementUnit2 = new ManagementUnit();
        managementUnit2.setId(managementUnit1.getId());
        assertThat(managementUnit1).isEqualTo(managementUnit2);
        managementUnit2.setId(2L);
        assertThat(managementUnit1).isNotEqualTo(managementUnit2);
        managementUnit1.setId(null);
        assertThat(managementUnit1).isNotEqualTo(managementUnit2);
    }
}
