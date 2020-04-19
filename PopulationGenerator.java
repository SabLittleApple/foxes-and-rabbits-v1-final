import java.awt.*;
import java.util.Random;

public class PopulationGenerator {
    private SimulatorView view;
    private Simulator simulator;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;


    public PopulationGenerator(Simulator simulator) {
        this.simulator = simulator;
        view = simulator.getView();
        view.setColor(Rabbit .class,Color.ORANGE);
        view.setColor(Fox .class,Color.BLUE);
    }

    /**
     * Randomly populate the field with foxes and rabbits.
     */
    public void populate() {
        Random rand = Randomizer.getRandom();
        simulator.getField().clear();
        for (int row = 0; row < simulator.getField().getDepth(); row++) {
            for (int col = 0; col < simulator.getField().getWidth(); col++) {
                if (rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, simulator.getField(), location);
                    simulator.getAnimals().add(fox);
                } else if (rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, simulator.getField(), location);
                    simulator.getAnimals().add(rabbit);
                }
                // else leave the location empty.
            }
        }
    }
}
