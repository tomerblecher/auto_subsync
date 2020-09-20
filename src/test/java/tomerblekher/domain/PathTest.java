package tomerblekher.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tomerblekher.web.rest.TestUtil;

public class PathTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Path.class);
        Path path1 = new Path();
        path1.setId(1L);
        Path path2 = new Path();
        path2.setId(path1.getId());
        assertThat(path1).isEqualTo(path2);
        path2.setId(2L);
        assertThat(path1).isNotEqualTo(path2);
        path1.setId(null);
        assertThat(path1).isNotEqualTo(path2);
    }
}
