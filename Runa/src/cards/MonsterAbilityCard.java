package cards;

import entities.Entity;
import entities.Hero;
import entities.Monster;

/**
 *
 * This class will create objects of the different monster ability cards.
 */
public class MonsterAbilityCard extends Card {

    private MonstersAbilities abilityName;

    /**
     * Construtor, initializes abilityName
     *
     * @param abilityName the new abilityName
     * @param cardLevel the level of the card
     */
    public MonsterAbilityCard(MonstersAbilities abilityName, int cardLevel) {
        this.abilityName = abilityName;
        this.setCardLevel(cardLevel);
        findAbilityType();

        defineFocusCostsAndTypeOfAbility(abilityName.toString());
    }

    /**
     * Finds the abilityType (offensive/defensive) based on the abilityName, Focus is declared as defensive as well
     */
    public void findAbilityType() {
        if (abilityName == MonstersAbilities.BLOCK || abilityName == MonstersAbilities.DEFLECT
                || abilityName == MonstersAbilities.FOCUS) {
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
            case SCRATCH:
                this.castScratch(runa);
                break;
            case CLAW:
                this.castClaw(runa);
                break;
            case SMASH:
                this.castSmash(runa);
                break;
            case BITE:
                this.castBite(runa);
                break;
            case DEFLECT:
                this.castDeflect(monster);
                break;
            case BLOCK:
                this.castBlock(monster);
                break;
            case FOCUS:
                this.castFocus(monster, this.getCardLevel());
                break;
            case WATER:
                this.castWater(monster, runa);
                break;
            case ICE:
                this.castIce(monster, runa);
                break;
            case FIRE:
                this.castFire(monster, runa);
                break;
            case LIGHTNING:
                this.castLightning(monster, runa);
                break;
            default:
                System.err.println("No such ability found");
        }
    }

    /**
     * This method casts the monster's "Scratch" ability. Deals physical damage and cancels Runa's focus
     *
     * @param runa Runa
     */
    public void castScratch(Hero runa) {
        int damage = (5 * getCardLevel()) - runa.getPhyArmor();
        damage = handleNegativeDamage(damage);

        // Cancels Runa's 'Focus' ability
        if (runa.isLoadsFocus()) {
            runa.setLoadsFocus(false);
        }
        updateRunaTakesDamage(runa, damage);
    }

    /**
     * This method casts the monster's "Claw" ability. Deals physical damage and cancels Runa's focus
     *
     * @param runa Runa
     */
    public void castClaw(Hero runa) {
        int damage = (6 * getCardLevel()) - runa.getPhyArmor();
        damage = handleNegativeDamage(damage);

        // Cancels Runa's 'Focus' ability
        if (runa.isLoadsFocus()) {
            runa.setLoadsFocus(false);
        }
        updateRunaTakesDamage(runa, damage);
    }

    /**
     * This method casts the monster's "Smash" ability. Deals heave physical damage
     *
     * @param runa Runa
     */
    public void castSmash(Hero runa) {
        int damage = (8 * getCardLevel()) - runa.getPhyArmor();
        damage = handleNegativeDamage(damage);
        updateRunaTakesDamage(runa, damage);
    }

    /**
     * This method casts the monster's "Bite" ability. Deals heave physical damage
     *
     * @param runa Runa
     */
    public void castBite(Hero runa) {
        int damage = (10 * getCardLevel()) - runa.getPhyArmor();
        damage = handleNegativeDamage(damage);
        updateRunaTakesDamage(runa, damage);

    }

    /**
     * This method casts the monster's "Deflect" ability. Provides them magical armor until their next turn
     *
     * @param monster The monster that casts the spell (gets the armor)
     */
    public void castDeflect(Monster monster) {
        monster.setMagArmor(11 * getCardLevel() + 2);
    }

    /**
     * This method casts the monster's "Block" ability. Provides them physicak armor until their next turn
     *
     * @param monster The monster that casts the spell (gets the armor)
     */
    public void castBlock(Monster monster) {
        monster.setPhyArmor(7 * getCardLevel());
    }

    /**
     * This ability casts the water ability on a target
     *
     * @param caster Entity who casts the ability
     * @param target Entity who is the target of the ability
     */
    @Override
    public void castWater(Entity caster, Entity target) {
        int damage = ((8 * getCardLevel()) + 2);

        castMagicalAbility(caster, target, damage);
    }

    /**
     * This ability casts the ice ability on a target
     *
     * @param caster Entity who casts the ability
     * @param target Entity who is the target of the ability
     */
    @Override
    public void castIce(Entity caster, Entity target) {
        int damage = ((10 * getCardLevel()) + 2);

        castMagicalAbility(caster, target, damage);
    }

    /**
     * This ability casts the fire ability on a target
     *
     * @param caster Entity who casts the ability
     * @param target Entity who is the target of the ability
     */
    @Override
    public void castFire(Entity caster, Entity target) {
        int damage = (12 * getCardLevel()) + 2;

        castMagicalAbility(caster, target, damage);
    }

    /**
     * This ability casts the lightning ability on a target
     *
     * @param caster Entity who casts the ability
     * @param target Entity who is the target of the ability
     */
    @Override
    public void castLightning(Entity caster, Entity target) {
        int damage = ((14 * getCardLevel()) + 2);

        castMagicalAbility(caster, target, damage);
    }

    /**
     * This method represents the algorithm of casting any offensive magical ability. Just the damage of the 4
     * different spells will not be the same, this is why these methods still have to exist.
     *
     * @param caster the caster of the spell
     * @param target the target of the spell
     * @param damage the damage of the ability
     */
    public void castMagicalAbility(Entity caster, Entity target, int damage) {
        int handledDamage = handleNegativeDamage(damage - target.getMagArmor());

        // If Runa used "Deflect" before
        int reflectedDamage = 0;
        int magArmorTemp = target.getMagArmor();
        for (int i = 0; i < damage; i++) {
            if (magArmorTemp != 0) {
                magArmorTemp--;
                reflectedDamage++;
            }
        }
        caster.setHealthPoints(caster.getHealthPoints() - reflectedDamage);

        target.setHealthPoints(target.getHealthPoints() - handledDamage);

        if (handledDamage != 0) {
            System.out.println("Runa takes " + handledDamage + " mag. damage");
        }

        if (target.getHealthPoints() != 0) {

            // If Runa used "Deflect" before
            if (reflectedDamage > 0) {
                System.out.println(caster.getName() + " takes " + reflectedDamage + " mag. damage");
            }
            caster.setFocusPoints(caster.getFocusPoints() - getCardLevel());
        }
    }

    /**
     * This method updates Runas health points and prints the damage, if not 0. Used in offensive physical abilities
     *
     * @param runa Runa
     * @param damage the incoming damage
     */
    public void updateRunaTakesDamage(Hero runa, int damage) {
        runa.setHealthPoints(runa.getHealthPoints() - damage);

        if (damage != 0) {
            System.out.println("Runa takes " + damage + " phy. damage");
        }
    }

    /**
     * Getter for abilityName
     * @return the abilityName
     */
    public MonstersAbilities getAbilityName() {
        return abilityName;
    }

    /**
     * Setter for abilityName
     * @param abilityName the new abilityName
     */
    public void setAbilityName(MonstersAbilities abilityName) {
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
