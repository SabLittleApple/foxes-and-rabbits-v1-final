import java.util.List;
import java.util.Random;


public abstract class Animal {
    // Whether the fox is alive or not.
    private boolean alive;
    // The fox's position.
    private Location location;
    // The field occupied.
    private Field field;
    protected int age;


    public Animal(Field field, Location location) {
        alive = true;
        this.field = field;
        setLocation(location);
        age = 0;

    }

    protected void act(List<Animal> newAnimals){

    }

    /**
     * Check whether the fox is alive or not.
     *
     * @return True if the fox is still alive.
     */
    protected boolean isAlive() {
        return alive;
    }

    /**
     * Return the fox's location.
     *
     * @return The fox's location.
     */
    protected Location getLocation() {
        return location;
    }

    /**
     * Place the fox at the new location in the given field.
     *
     * @param newLocation The fox's new location.
     */
    protected void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Indicate that the fox is no longer alive.
     * It is removed from the field.
     */
    protected void setDead() {
        alive = false;
        if (location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    protected Field getField() {
        return field;
    }

    protected boolean canBreed() {
        return age >= getBreedingAge();
    }

    abstract protected int getBreedingAge();

    /**
     * Increase the age. This could result in the fox's death.
     */
    protected void incrementAge() {
        age++;
        if (age > getMaxAge()) {
            setDead();
        }
    }

    abstract protected int getMaxAge();

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     *
     * @return The number of births (may be zero).
     */

    protected int breed() {
        int births = 0;
        if (canBreed() && getRand().nextDouble() <= getBreedingProbability()) {
            births = getRand().nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    abstract protected double getBreedingProbability();

    abstract protected int getMaxLitterSize();

    abstract protected Random getRand();
}
