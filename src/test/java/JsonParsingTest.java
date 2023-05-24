import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class JsonParsingTest {
    private ClassLoader cl = ZipTests.class.getClassLoader();

    @Test
    void jsonCleverTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = cl.getResourceAsStream("human.json");
             InputStreamReader isr = new InputStreamReader(is)) {

            HumanTests humanTests = mapper.readValue(isr, HumanTests.class);

            Assertions.assertEquals("Dmitry", humanTests.name);
            Assertions.assertEquals(37, humanTests.age);
            Assertions.assertEquals(List.of("Snowboard", "playing the guitar"), humanTests.hobbies);
            Assertions.assertTrue(humanTests.isClever);
            Assertions.assertEquals(993939399, humanTests.passport.number);
            Assertions.assertEquals("1 JAN 2000", humanTests.passport.issueDate);
        }
    }
}