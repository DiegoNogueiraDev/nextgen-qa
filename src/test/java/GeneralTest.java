import com.nextgenqa.model.Flow;
import com.nextgenqa.service.YamlLoaderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GeneralTest {

    @Test
    public void testLoadFlows() {
        YamlLoaderService yamlLoaderService = new YamlLoaderService();
        List<Flow> flows = yamlLoaderService.loadFlows("flows.yaml");
        Assertions.assertNotNull(flows);
        Assertions.assertFalse(flows.isEmpty());
        Assertions.assertEquals("contact_form", flows.get(0).getName());
    }

}
