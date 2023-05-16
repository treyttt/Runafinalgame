package entities;

/**
 * This class defines attributes every entity (Runa or monsters) own. Reduces duplicate code
 */
public abstract class Entity {

    private String name;
    private int healthPoints;
    private int focusPoints;
    private int phyArmor;
    private int magArmor;
    // loadsFocus is true, if the Entity used 'Focus', false otherwise
    private boolean loadsFocus;
    // howMuchFocusWillbeAdded basically stores the cardLevel of the Entity that used Focus
    private int howMuchFocusWillBeAdded;

    /**
     * Constructor for any entity
     * @param name contains the entity's name
     * @param healthPoints contains the entity's health points
     * @param focusPoints contains the entity's focus points
     * @param phyArmor contains the entity's physical armor
     * @param magArmor contains the entity's magical armor
     */
    public Entity(String name, int healthPoints, int focusPoints, int phyArmor, int magArmor) {
        this.name = name;
        this.healthPoints = healthPoints;
        this.focusPoints = focusPoints;
        this.phyArmor = phyArmor;
        this.magArmor = magArmor;
        this.loadsFocus = false;
    }

    /**
     * Getter for entity's name
     * @return entity's name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for entitiy's name
     * @param name entity's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the entity's health points
     * @return the entity's health points
     */
    public int getHealthPoints() {
        return healthPoints;
    }

    /**
     * Setter for the entity's health points
     * @param healthPoints contains the new amount of entities health points
     */
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;

        if (this.getHealthPoints() > 50 && !this.getName().equals("Mega Saurus")) {
            this.healthPoints = 50;
        }
        else if (this.getHealthPoints() < 0) {
            this.healthPoints = 0;
        }
    }

    /**
     * Getter for the entity's focus points
     * @return the entity's focus points
     */
    public int getFocusPoints() {
        return focusPoints;
    }

    /**
     * Setter for the entity's focus points
     * @param focusPoints contains the new amount of entity's focus points
     */
    public void setFocusPoints(int focusPoints) {
        this.focusPoints = focusPoints;
    }

    /**
     * Getter for phyArmor
     * @return the amount of physical armor
     */
    public int getPhyArmor() {
        return phyArmor;
    }

    /**
     * Setter for phyArmor
     * @param phyArmor the new amout of physical armor
     */
    public void setPhyArmor(int phyArmor) {
        this.phyArmor = phyArmor;
    }

    /**
     * Getter for magArmor
     * @return the amount of magical armor
     */
    public int getMagArmor() {
        return magArmor;
    }

    /**
     * Setter for magArmor
     * @param magArmor the new amout of magical armor
     */
    public void setMagArmor(int magArmor) {
        this.magArmor = magArmor;
    }

    /**
     * Getter for loadsFocus
     * @return true, if the entity used "Focus" at least one round ago, false otherwise
     */
    public boolean isLoadsFocus() {
        return loadsFocus;
    }

    /**
     * Setter for loadsFocus
     * @param loadsFocus true if entity used "Focus", false otherwise
     */
    public void setLoadsFocus(boolean loadsFocus) {
        this.loadsFocus = loadsFocus;
    }

    /**
     * Getter for howMuchFocusWillBeAdded
     * @return the amount of focus that will be added, if the ablity 'Focus' isn't blocked
     */
    public int getHowMuchFocusWillBeAdded() {
        return howMuchFocusWillBeAdded;
    }

    /**
     * Setter for howMuchFocusWillBeAdded
     * @param howMuchFocusWillBeAdded the amount of focus that will be added, if the ablity 'Focus' isn't blocked
     */
    public void setHowMuchFocusWillBeAdded(int howMuchFocusWillBeAdded) {
        this.howMuchFocusWillBeAdded = howMuchFocusWillBeAdded;
    }
}
