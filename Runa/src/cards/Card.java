package cards;

import entities.Entity;
import entities.Hero;
import entities.Monster;
import generals.Element;

/**
 *
 *
 * Every card type (Cards.MonsterAbilityCard / Cards.RunaAbilityCard) extends from card, because they all implement
 * these methods and has got these same attributes
 */
public abstract class Card {

    private int focusCosts;
    private int cardLevel;
    private Element type;
    // dmgType true = physical, dmgType false = magical
    private boolean dmgType;
    // abilityType true = offensive, abilityType false = defensive
    private boolean abilityType;

    /**
     * This method assigns element type and focus costs of the given ability
     *
     * @param ability the ability, which will be defined
     */
    public void defineFocusCostsAndTypeOfAbility(String ability) {

        // This if-else cascade assigns the abilities type
        if (ability.equals("WATER")) {
            type = Element.WATER;
        }
        else if (ability.equals("ICE")) {
            type = Element.ICE;
        }
        else if (ability.equals("FIRE")) {
            type = Element.FIRE;
        }
        else if (ability.equals("LIGHTNING")) {
            type = Element.LIGHTNING;
        }
        else {
            type = Element.NONE;
        }

        // Finds out how much focusPoints the ability costs
        if (type.toString().equals("NONE")) {
            focusCosts = 0;
        }
        else if (this.getClass().getSimpleName().equals("RunaAbilityCard")) {
            focusCosts = 1;
        }
        else {
            focusCosts = cardLevel;
        }

        // Defines the dmgType, based on the focus costs
        if (focusCosts != 0) {
            dmgType = false;
        }
        else {
            dmgType = true;
        }
    }

    /**
     * This method handles the occurance of negative damage (happens when the targets armor is higher than the
     * offensive ability damage, cast by the caster
     *
     * @param damage the damage, which might be negative
     * @return 0, if the damage is negative, the regular damage otherwise
     */
    public int handleNegativeDamage(int damage) {

        int temp;
        if (damage < 0) {
            temp = 0;
        }
        else {
            temp = damage;
        }
        return temp;
    }

    /**
     * This ability will find out, which ability has to be cast
     *
     * @param runa Runa
     * @param monster The monster that either casts the ability or is the target of an ability
     * @param dice the number Runa did roll, IGNORE FOR MONSTERS
     */
    public abstract void castAbility(Hero runa, Monster monster, int dice);

    /**
     * This method casts the focus ability, both Runa and Monsters can use
     * increases the focus points of the given entity by one
     *
     * @param target the target of the spell
     * @param cardLevel needed to update the variable, that stores how much focus will be added
     */
    public void castFocus(Entity target, int cardLevel) {
        target.setLoadsFocus(true);
        target.setHowMuchFocusWillBeAdded(cardLevel);
    }

    /**
     * This ability casts the water ability on a target
     *
     * @param caster Entity who casts the ability
     * @param target Entity who is the target of the ability
     */
    public abstract void castWater(Entity caster, Entity target);

    /**
     * This ability casts the ice ability on a target
     *
     * @param caster Entity who casts the ability
     * @param target Entity who is the target of the ability
     */
    public abstract void castIce(Entity caster, Entity target);

    /**
     * This ability casts the fire ability on a target
     *
     * @param caster Entity who casts the ability
     * @param target Entity who is the target of the ability
     */
    public abstract void castFire(Entity caster, Entity target);

    /**
     * This ability casts the lightning ability on a target
     *
     * @param caster Entity who casts the ability
     * @param target Entity who is the target of the ability
     */
    public abstract void castLightning(Entity caster, Entity target);

    /**
     * Getter from focusPoints
     * @return current focus Points
     */
    public int getFocusCosts() {
        return focusCosts;
    }

    /**
     * Setter from focusPoints
     * @param focusCosts the new amount of focusPoints
     */
    public void setFocusCosts(int focusCosts) {
        this.focusCosts = focusCosts;
    }

    /**
     * Getter for cardLevel
     * @return the current cardLevel
     */
    public int getCardLevel() {
        return cardLevel;
    }

    /**
     * Setter for cardLevel
     * @param cardLevel contains the new cardLevel
     */
    public void setCardLevel(int cardLevel) {
        this.cardLevel = cardLevel;
    }

    /**
     * Getter for the element type
     * @return the Element
     */
    public Element getType() {
        return type;
    }

    /**
     * Setter for the element type
     * @param type the new Element type
     */
    public void setType(Element type) {
        this.type = type;
    }

    /**
     * Getter for dmgType
     * @return true if physical, false if magical
     */
    public boolean isDmgType() {
        return dmgType;
    }

    /**
     * Setter for dmgType
     * @param dmgType true if physical, false if magical
     */
    public void setDmgType(boolean dmgType) {
        this.dmgType = dmgType;
    }

    /**
     * Getter for the abilityType variable
     * @return true, if the card is offensive, false otherwise
     */
    public boolean isAbilityType() {
        return abilityType;
    }

    /**
     * Setter for the abilityType variable
     * @param abilityType true if the card is offensive, false otherwise
     */
    public void setAbilityType(boolean abilityType) {
        this.abilityType = abilityType;
    }
}
