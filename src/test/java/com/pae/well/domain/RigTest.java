package com.pae.well.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RigTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rig.class);
        Rig rig1 = new Rig();
        rig1.setId(1L);
        Rig rig2 = new Rig();
        rig2.setId(rig1.getId());
        assertThat(rig1).isEqualTo(rig2);
        rig2.setId(2L);
        assertThat(rig1).isNotEqualTo(rig2);
        rig1.setId(null);
        assertThat(rig1).isNotEqualTo(rig2);
    }
}
