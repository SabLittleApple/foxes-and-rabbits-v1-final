import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public class Fox extends Animal {
    // Characteristics shared by all foxes (class variables).

    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a fox can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.08;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 9;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // The fox's food level, which is increased by eating rabbits.
    private int foodLevel;

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public Fox(boolean randomAge, Field field, Location location) {
        super(field, location);
//        age = 0;
        // setLocation(location);
        if (randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
        } else {
            // leave age at 0
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
        }
    }

    /**
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     *
     * @param newFoxes A list to return newly born foxes.
     */
    protected void act(List<Animal> newFoxes) {
        incrementAge();
        incrementHunger();
        if (isAlive()) {
            giveBirth(newFoxes);
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if (newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                // Overcrowding.
                setDead();
            }
        }
    }


    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    protected void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     * <p>
     * <p>
     * Currently, a fox will eat at most one rabbit at each step. Modify the find-
     * Food method so that rabbits in all adjacent locations are eaten at a single step. Assess the im-
     * pact of this change on the results of the simulation. Note that the findFood method currently
     * returns the location of the single rabbit that is eaten, so you will need to return the location of
     * one of the eaten rabbits in your version. However, don’t forget to return null if there are no
     * rabbits to eat
     */
    protected Location findFood() {
        List<Location> adjacent = getField().adjacentLocations(getLocation());
        Location lastLocation = null;
        for (Location where : adjacent) {
            Object animal = getField().getObjectAt(where);
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    lastLocation = where;
                }
            }
        }
        return lastLocation;
    }

    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     *
     * @param newFoxes A list to return newly born foxes.
     */
    protected void giveBirth(List<Animal> newFoxes) {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = getField().getFreeAdjacentLocations(getLocation());
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Fox young = new Fox(false, field, loc);
            newFoxes.add(young);
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     *
     * @return The number of births (may be zero).
     */
    protected int breed() {
        int births = 0;
        if (canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    protected int getBreedingAge() {
        return BREEDING_AGE;

    }

    protected int getMaxAge() {
        return MAX_AGE;
    }

    protected double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    protected int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    protected Random getRand() {
        return rand;
    }
}
