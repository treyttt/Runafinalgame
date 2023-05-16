package cards;

import entities.Entity;
import entities.Hero;
import entities.Monster;
import generals.Element;

/**
 *
 * This class will create objects of the different ability cards Runa can cast.
 */
public class RunaAbilityCard extends Card {

    private RunasAbilities abilityName;

    /**
     * Constructor for RunaAbilityCard, creates an ability Card, based on abilityName and cardLevel
     *
     * @param abilityName the name of the ability, stored in the enum RunasAbilities
     * @param cardLevel the cardLevel of the ability card.
     */
    public RunaAbilityCard(RunasAbilities abilityName, int cardLevel) {
        this.abilityName = abilityName;
        this.setCardLevel(cardLevel);
        findAbilityType();

        defineFocusCostsAndTypeOfAbility(abilityName.toString());
    }

    /**
     * Finds the abilityType (offensive/defensive) based on the abilityName, Focus is declared as defensive as well
     */
    public void findAbilityType() {
        if (abilityName == RunasAbilities.PARRY || abilityName == RunasAbilities.REFLECT
                || abilityName == RunasAbilities.FOCUS) {
            setAbilityType(false);
        }
        else {
            setAbilityType(true);
        }
    }

    /**
     * This ability will find out, which ability has to be cast
     *
     * @param runa Runa
     * @param monster The monster that either casts the ability or is the target of an ability
     * @param dice the number Runa did roll, IGNORE FOR MONSTERS
     */
    @Override
    public void castAbility(Hero runa, Monster monster, int dice) {

        switch (abilityName) {
            case SLASH:
                this.castSlash(monster, dice);
                break;
            case SWING:
                this.castSwing(monster, dice);
                break;
            case THRUST:
                this.castThrust(monster, dice);
                break;
            case PIERCE:
                this.castPierce(monster, dice);
                break;
            case PARRY:
                castParry(runa);
                break;
            case FOCUS:
                castFocus(runa, this.getCardLevel());
                break;
            case REFLECT:
                castReflect(runa);
                break;
            case WATER:
                castWater(runa, monster);
                break;
            case ICE:
                castIce(runa, monster);
                break;
            case FIRE:
                castFire(runa, monster);
                break;
            case LIGHTNING:
                castLightning(runa, monster);
                break;
            default:
                System.err.println("No ability found!");
        }
    }

    /**
     * This method casts Runa's "Slash" ability, deals physical damage and blocks target's 'Focus'
     *
     * @param target contains the target, which the ability should hit
     * @param dice contains the amount, which Runa did roll
     */
    public void castSlash(Monster target, int dice) {
        int damage = (4 * getCardLevel() + dice) - target.getPhyArmor();
        damage = handleNegativeDamage(damage);

        target.setHealthPoints(target.getHealthPoints() - (damage));

        // If the target used 'Focus' before, it will be countered
        if (target.isLoadsFocus()) {
            target.setLoadsFocus(false);
        }
        if (damage != 0) {
            System.out.println(target.getName() + " takes " + damage + " phy. damage");
        }
    }

    /**
     * This method casts Runa's "Swing" ability, deals physical damage and blocks target's 'Focus'
     *
     * @param target contains the target, which the ability should hit
     * @param dice contains the amount, which Runa did roll
     */
    public void castSwing(Monster target, int dice) {
        int damage = (5 * getCardLevel() + dice) - target.getPhyArmor();
        damage = handleNegativeDamage(damage);

        target.setHealthPoints(target.getHealthPoints() - (damage));

        // If the target used 'Focus' before, it will be countered
        if (target.isLoadsFocus()) {
            target.setLoadsFocus(false);
        }
        if (damage != 0) {
            System.out.println(target.getName() + " takes " + damage + " phy. damage");
        }
    }


    /**
     * This method casts Runa's "Thrust" ability, deals heavy phiscal damage
     *
     * @param target contains the target, which the ability should hit
     * @param dice contains the amount, which Runa did roll
     */
    public void castThrust(Monster target, int dice) {
        int damage = 6 * getCardLevel() + dice - target.getPhyArmor();

        if (dice >= 6) {
            damage += 4 * getCardLevel();
        }
        damage = handleNegativeDamage(damage);

        target.setHealthPoints(target.getHealthPoints() - (damage));

        if (damage != 0) {
            System.out.println(target.getName() + " takes " + damage + " phy. damage");
        }
    }

    /**
     * This method casts Runa's "Pierce" ability, deals heavy phiscal damage
     *
     * @param target contains the target, which the ability should hit
     * @param dice contains the amount, which Runa did roll
     */
    public void castPierce(Monster target, int dice) {
        int damage = (7 * getCardLevel() + dice) - target.getPhyArmor();

        if (dice >= 6) {
            damage += 5 * getCardLevel();
        }
        damage = handleNegativeDamage(damage);

        target.setHealthPoints(target.getHealthPoints() - (damage));

        if (damage != 0) {
            System.out.println(target.getName() + " takes " + damage + " phy. damage");
        }
    }

    /**
     * This method casts Runa's "Parry" ability. Provides physical armor until next round
     *
     * @param runa Runa (obviously the target of her own defensive ability)
     */
    public void castParry(Hero runa) {
        int armor = (7 * getCardLevel());
        runa.setPhyArmor(armor);
    }

    /**
     * This method casts Runa's "Reflect" ability.
     * Provides magical armor until next round (Reflects incoming mag. damage)
     *
     * @param runa Runa (obviously the target of her own defensive ability)
     */
    public void castReflect(Hero runa) {
        int armor = (10 * getCardLevel());
        runa.setMagArmor(armor);
    }

    /**
     * This ability casts the water ability on a target
     *
     * @param caster Entity who casts the ability
     * @param target Entity who is the target of the ability
     */
    @Override
    public void castWater(Entity caster, Entity target) {
        Monster monster = (Monster) target;

        int damage = ((2 * getCardLevel() + 4) * caster.getFocusPoints());

        // Adds extra damage, based on the monster's type
        if (monster.getType().equals(Element.LIGHTNING)) {
            damage += (2 * getCardLevel());
        }
        damage -= monster.getMagArmor();
        damage = handleNegativeDamage(damage);

        target.setHealthPoints(target.getHealthPoints() - damage);

        if (damage != 0) {
            System.out.println(target.getName() + " takes " + damage + " mag. damage");
        }

        // Reduces Runa's focus points if they are > 1
        if (caster.getFocusPoints() > 1) {
            caster.setFocusPoints(caster.getFocusPoints() - 1);
        }
    }

    /**
     * This ability casts the ice ability on a target
     *
     * @param caster Entity who casts the ability
     * @param target Entity who is the target of the ability
     */
    @Override
    public void castIce(Entity caster, Entity target) {
        Monster monster = (Monster) target;

        int damage = ((2 * getCardLevel() + 4) * caster.getFocusPoints() + 2);

        // Adds extra damage, based on the monster's type
        if (monster.getType().equals(Element.WATER)) {
            damage += (2 * getCardLevel());
        }
        damage -= monster.getMagArmor();
        damage = handleNegativeDamage(damage);

        target.setHealthPoints(target.getHealthPoints() - damage);

        if (damage != 0) {
            System.out.println(target.getName() + " takes " + damage + " mag. damage");
        }

        // Reduces Runa's focus points if they are > 1
        if (caster.getFocusPoints() > 1) {
            caster.setFocusPoints(caster.getFocusPoints() - 1);
        }
    }

    /**
     * This ability casts the fire ability on a target
     *
     * @param caster Entity who casts the ability
     * @param target Entity who is the target of the ability
     */
    @Override
    public void castFire(Entity caster, Entity target) {
        Monster monster = (Monster) target;

        int damage = ((2 * getCardLevel() + 5) * caster.getFocusPoints());

        // Adds extra damage, based on the monster's type
        if (monster.getType().equals(Element.ICE)) {
            damage += (2 * getCardLevel());
        }
        damage -= monster.getMagArmor();
        damage = handleNegativeDamage(damage);

        target.setHealthPoints(target.getHealthPoints() - damage);

        if (damage != 0) {
            System.out.println(target.getName() + " takes " + damage + " mag. damage");
        }

        // Reduces Runa's focus points if they are > 1
        if (caster.getFocusPoints() > 1) {
            caster.setFocusPoints(caster.getFocusPoints() - 1);
        }
    }

    /**
     * This ability casts the lightning ability on a target
     *
     * @param caster Entity who casts the ability
     * @param target Entity who is the target of the ability
     */
    @Override
    public void castLightning(Entity caster, Entity target) {
        Monster monster = (Monster) target;

        int damage = ((2 * getCardLevel() + 5) * caster.getFocusPoints() + 2);

        // Adds extra damage, based on the monster's type
        if (monster.getType().equals(Element.FIRE)) {
            damage += 2 * getCardLevel();
        }
        damage -= monster.getMagArmor();
        damage = handleNegativeDamage(damage);

        target.setHealthPoints(target.getHealthPoints() - damage);

        if (damage != 0) {
            System.out.println(target.getName() + " takes " + damage + " mag. damage");
        }

        // Reduces Runa's focus points if they are > 1
        if (caster.getFocusPoints() > 1) {
            caster.setFocusPoints(caster.getFocusPoints() - 1);
        }
    }

    /**
     * Getter for abilityName
     * @return the abilityName
     */
    public RunasAbilities getAbilityName() {
        return abilityName;
    }

    /**
     * Setter for abilityName
     * @param abilityName the new abilityName
     */
    public void setAbilityName(RunasAbilities abilityName) {
        this.abilityName = abilityName;
    }

    /**
     * Returns a nice looking representation of the abilityName variable
     * @return String abilityName with just first letter uppercase
     */
    public String getStringAbilityName() {
        String ability = this.abilityName.toString();
        ability = ability.charAt(0) + ability.substring(1).toLowerCase();

        return ability;
    }
}