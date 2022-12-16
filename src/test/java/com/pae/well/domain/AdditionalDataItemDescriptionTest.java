package com.pae.well.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdditionalDataItemDescriptionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdditionalDataItemDescription.class);
        AdditionalDataItemDescription additionalDataItemDescription1 = new AdditionalDataItemDescription();
        additionalDataItemDescription1.setId(1L);
        AdditionalDataItemDescription additionalDataItemDescription2 = new AdditionalDataItemDescription();
        additionalDataItemDescription2.setId(additionalDataItemDescription1.getId());
        assertThat(additionalDataItemDescription1).isEqualTo(additionalDataItemDescription2);
        additionalDataItemDescription2.setId(2L);
        assertThat(additionalDataItemDescription1).isNotEqualTo(additionalDataItemDescription2);
        additionalDataItemDescription1.setId(null);
        assertThat(additionalDataItemDescription1).isNotEqualTo(additionalDataItemDescription2);
    }
}
