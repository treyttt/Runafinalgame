package entities;

import cards.MonsterAbilityCard;
import generals.Element;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class will create objects of monsters.
 */
public class Monster extends Entity {

    private List<MonsterAbilityCard> monsterAbilityCardList;
    private boolean boss;
    private Element type;
    private int level;
    private int abilityPointer;

    /**
     * Constructor of Monster, defines the Monsters stats
     *
     * @param name contains the monster's name
     * @param healthPoints contains the monster's health points
     * @param boss contains information, whether the monster is a boss or not
     * @param type contains information about the monster's type
     * @param level contains the information about the monster's level
     */
    public Monster(String name, int healthPoints, boolean boss, Element type, int level) {
        super(name, healthPoints, 0, 0, 0);
        monsterAbilityCardList = new ArrayList<>();
        this.boss = boss;
        this.type = type;
        this.level = level;
        abilityPointer = 0;
    }

    /**
     * This method updates the abilityPointer, so it will never point on an index that doesn't exist.
     * Please do not use the Setter, it is uncomfortable and might throw exceptions
     */
    public void updateAbilityPointer() {
        abilityPointer++;

        if (abilityPointer == monsterAbilityCardList.size()) {
            abilityPointer = 0;
        }
    }

    /**
     * Getter for the monster's abilities
     * @return a list containing the monster's ability cards
     */
    public List<MonsterAbilityCard> getMonsterAbilityCardList() {
        return monsterAbilityCardList;
    }

    /**
     * setter for the monster's abilities
     * @param monsterAbilityCardList a list, containing the monster's abilities
     */
    public void setMonsterAbilityCardList(List<MonsterAbilityCard> monsterAbilityCardList) {
        this.monsterAbilityCardList = monsterAbilityCardList;
    }

    /**
     * Getter for the boolean 'boss' variable
     * @return true if the monster is a boss, false otherwise
     */
    public boolean isBoss() {
        return boss;
    }

    /**
     * Setter for the boolean 'boss' variable
     * @param boss true if the monster is a boss, false otherwise
     */
    public void setBoss(boolean boss) {
        this.boss = boss;
    }

    /**
     * Getter for the monster's element type
     * @return either WATER, ICE, FIRE, LIGHTNING or NONE
     */
    public Element getType() {
        return type;
    }

    /**
     * Setter for the monster's element type
     * @param type either WATER, ICE, FIRE, LIGHTNING or NONE
     */
    public void setType(Element type) {
        this.type = type;
    }

    /**
     * Getter for the monster's level
     * @return 1 if it is on level 1, 2 if it is on level 2
     */
    public int getLevel() {
        return level;
    }

    /**
     * Setter for the monster's level
     * @param level 1 if it is on level 1, 2 if it is on level 2
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Getter for the abilityPointer
     * @return the current index
     */
    public int getAbilityPointer() {
        return abilityPointer;
    }

    /**
     * Setter for the abiltyPointer, Use "updateAbilityPointer()" instead!
     * @param abilityPointer the new abilityPointer
     */
    public void setAbilityPointer(int abilityPointer) {
        this.abilityPointer = abilityPointer;
    }
}
