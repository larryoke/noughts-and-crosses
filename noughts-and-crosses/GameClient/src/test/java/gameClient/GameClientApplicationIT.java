package gameClient;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@RunWith(SpringRunner.class)
@RestClientTest(GameClientApplication.class)
public class GameClientApplicationIT {

    @Autowired
    private CommandLineRunner commandLineRunner;

    @Test
    public void testRun() throws Exception {
	// ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
	// System.setIn(in);

	commandLineRunner.run();
    }
}
