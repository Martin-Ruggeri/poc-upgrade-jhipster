package com.pae.well.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdditionalDataItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdditionalDataItem.class);
        AdditionalDataItem additionalDataItem1 = new AdditionalDataItem();
        additionalDataItem1.setId(1L);
        AdditionalDataItem additionalDataItem2 = new AdditionalDataItem();
        additionalDataItem2.setId(additionalDataItem1.getId());
        assertThat(additionalDataItem1).isEqualTo(additionalDataItem2);
        additionalDataItem2.setId(2L);
        assertThat(additionalDataItem1).isNotEqualTo(additionalDataItem2);
        additionalDataItem1.setId(null);
        assertThat(additionalDataItem1).isNotEqualTo(additionalDataItem2);
    }
}
