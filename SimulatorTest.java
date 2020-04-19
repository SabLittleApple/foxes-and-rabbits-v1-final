import org.junit.Test;

import static org.junit.Assert.*;

public class SimulatorTest {

    private Simulator simulator = new Simulator();

    @Test
    //record the number of foxes and rabbits over a small number of steps
    // to prepare for regression testing of the changes to follow.
    public void simulate() {

        Object[][] fieldMatrix = simulator.getField().getFieldPositions();
        int fieldAnimalsCounter = 0;
        simulator.simulate(3);
        for (int y = 0; y < simulator.getField().getWidth(); y++) {
            for (int x = 0; x < simulator.getField().getDepth(); x++) {
                if (fieldMatrix[x][y] instanceof Rabbit || fieldMatrix[x][y] instanceof Fox) {
                    fieldAnimalsCounter++;
                }
            }
        }
        System.out.println(fieldAnimalsCounter);
        assertEquals(fieldAnimalsCounter, 1248);
    }

}