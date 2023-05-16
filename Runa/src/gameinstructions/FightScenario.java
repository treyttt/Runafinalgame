package gameinstructions;

import cards.Deck;
import entities.Entity;
import entities.Hero;
import entities.Monster;
import entities.MonsterCreator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 *
 * This class creates Fightscenarios.
 */
public class FightScenario {

    private Hero runa;
    private int stage;
    private int level;
    private Deck<Monster> monsterDeck;
    private List<Monster> enemies;

    /**
     * This is the Constructor of a Fightscenario, it builds the environment for the next fight
     *
     * @param stage the current stage of Runa's adventure
     * @param level the current level of Runa's adventure
     * @param runa Runa itself
     * @param monsterDeck the monsterdeck, which the next two monsters will be chosen from
     */
    public FightScenario(int stage, int level, Hero runa, Deck<Monster> monsterDeck) {
        this.runa = runa;
        this.stage = stage;
        this.level = level;
        this.monsterDeck = monsterDeck;
        enemies = new ArrayList<>();
        System.out.println("Runa enters Stage " + stage + " of Level " + level);
    }

    /**
     * This method decides how many enemies or which boss Runa has to fight, based on stage and level
     */
    public void decideNewEnemies() {

        if (stage == 1) {
            enemies.add(monsterDeck.getDeck().remove(0));
        }
        else if (stage == 2 || stage == 3) {
            enemies.add(monsterDeck.getDeck().remove(0));
            enemies.add(monsterDeck.getDeck().remove(0));
        }
        else {
            if (level == 1) {
                enemies.add(new MonsterCreator().createSpiderKing());
            }
            else {
                enemies.add(new MonsterCreator().createMegaSaurus());
            }
        }
    }

    /**
     * This method contains te algorithm for the whole fight. It's basically an infinte loop until either Runa
     * or all her enemies are dead
     *
     * @return "quit", if the user did input quit, nothing relevant otherwise
     */
    public String startFight() {
        boolean isFightOver = false;
        decideNewEnemies();

        while (!isFightOver) {
            // Prints the fight information
            printFightInformation();

            // This is the method call, that leads into Runa casting her ability
            System.out.println("Select card to play");
            if (selectCardToPlay().equals("quit")) {
                return "quit";
            }

            // Checks whether the fight is over, because if the monsters are dead, they shouldn't cast their abilities
            isFightOver = isTheFightOver();
            if (isFightOver) {
                break;
            }

            // Updates the focus points of monsters, if "Focus" was cast and not blocked until last round
            for (Monster monster : enemies) {
                checkIfFocusWasCastLastRound(monster);
                reduceArmorToZero(monster);
            }

            /*
             * All monsters use their ability, until they are done / Runa dies
             * I have to use the iterator, because in the rare case that the Monster dies due 'Reflect'
             * a ConcurrentModificationException would be thrown otherwise
            */
            for (Iterator<Monster> enemy = enemies.iterator(); enemy.hasNext();) {
                Monster monster = enemy.next();

                String result = monsterUsesAbility(runa, monster, 0);

                if (result.equals("quit")) {
                    return result;
                }
                if (result.equals("MonsterDead")) {
                    enemy.remove();
                }
            }
            isFightOver = isTheFightOver();

            // Updates the focus points of Runa, if "Focus" was cast and not blocked until last round
            checkIfFocusWasCastLastRound(runa);
            reduceArmorToZero(runa);
        }
        return "";
    }

    /**
     * This method contains an algorithm to print the current fight information.
     */
    public void printFightInformation() {
        System.out.println("----------------------------------------");
        System.out.println("Runa (" + runa.getHealthPoints() + "/50 HP, " + runa.getFocusPoints() + "/"
                + runa.getDice() + " FP)");
        System.out.println("vs.");

        // Prints the enemy's information
        for (Monster enemy : enemies) {
            System.out.println(enemy.getName() + " (" + enemy.getHealthPoints() + " HP, "
                    + enemy.getFocusPoints() + " FP): attempts "
                    + enemy.getMonsterAbilityCardList().get(enemy.getAbilityPointer()).getStringAbilityName()
                    + "(" + enemy.getMonsterAbilityCardList().get(enemy.getAbilityPointer()).getCardLevel()
                    + ") next");

        }
        System.out.println("----------------------------------------");
    }

    /**
     * This method contains the instructions to make the user input, which ability Runa should cast
     *  (Even when she only got 1)
     *
     * @return "quit" if the user input's quit, nothing relevant otherwise
     */
    public String selectCardToPlay() {

        for (int i = 0; i < runa.getRunaAbilityCardList().size(); i++) {
            System.out.println((i + 1) + ") " + runa.getRunaAbilityCardList().get(i).getStringAbilityName() + "("
                    + runa.getRunaAbilityCardList().get(i).getCardLevel() + ")");
        }

        if (selectCardToPlayHelper().equals("quit")) {
            return "quit";
        }
        return "";
    }

    /**
     * Thid method just splits the selectCardToPlay() method into two
     *
     * @return the input
     */
    public String selectCardToPlayHelper() {

        Scanner sc = new Scanner(System.in);
        String input;

        while (true) {
            System.out.println("Enter number [1--" + runa.getRunaAbilityCardList().size() + "]:");

            input = sc.nextLine();
            input = cutPrefix(input);

            if (input.equals("quit")) {
                return input;
            }
            else {
                int abilityNumber = 0;
                try {
                    abilityNumber = Integer.parseInt(input);
                }
                catch (NumberFormatException e) {
                    //Nothing
                }
                if (abilityNumber > 0 && abilityNumber <= runa.getRunaAbilityCardList().size()) {
                    if (selectTarget(abilityNumber - 1).equals("quit")) {
                        return "quit";
                    }
                    break;
                }
            }
        }
        return "";
    }

    /**
     * This method makes the user select a target, if there are more than one
     *
     * @param abilityIndex The index of the chosen ability, previously picked in 'selectCardToPlay()'
     * @return "quit" if the user inputs quit, nothing relevant otherwise
     */
    public String selectTarget(int abilityIndex) {

        if (enemies.size() > 1 && runa.getRunaAbilityCardList().get(abilityIndex).isAbilityType()) {
            System.out.println("Select Runa's target.");

            for (int i = 0; i < enemies.size(); i++) {
                System.out.println((i + 1) + ") " + enemies.get(i).getName());
            }

            if (selectTargetHelper(abilityIndex).equals("quit")) {
                return "quit";
            }
        }
        else {
            if (castRunasAbility(abilityIndex, 0).equals("quit")) {
                return "quit";
            }
        }
        return "";
    }

    /**
     * This method just exist to split the selectTarget() method
     *
     * @param abilityIndex from selectTarget
     * @return "quit" if the user inputs quit, nothing relevant otherwise
     */
    public String selectTargetHelper(int abilityIndex) {

        Scanner sc = new Scanner(System.in);
        String input;

        while (true) {
            System.out.println("Enter number [1--2]:");

            input = sc.nextLine();
            input = cutPrefix(input);

            if (input.equals("quit")) {
                return input;
            }
            else {
                if (input.equals("1")) {
                    if (castRunasAbility(abilityIndex, 0).equals("quit")) {
                        return "quit";
                    }
                    break;
                } else if (input.equals("2")) {
                    if (castRunasAbility(abilityIndex, 1).equals("quit")) {
                        return "quit";
                    }
                    break;
                }
            }
        }
        return "";
    }

    /**
     * This method casts Runa's previously chosen ability on the previously chosen target
     *
     * @param abilityIndex The index of the chosen ability, previously picked in 'selectCardToPlay()'
     * @param monsterIndex The index of the chosen monster, previously picked in 'selectTarget()'
     * @return "quit" if the user inputs quit, nothing relevant otherwise
     */
    public String castRunasAbility(int abilityIndex, int monsterIndex) {

        System.out.println("Runa uses " + runa.getRunaAbilityCardList().get(abilityIndex).getStringAbilityName() + "("
                + runa.getRunaAbilityCardList().get(abilityIndex).getCardLevel() + ")");

        // If the cast ability is offensive
        if (runa.getRunaAbilityCardList().get(abilityIndex).isAbilityType()) {

            // If the cast ability is physical, the dice has to be rolled
            if (runa.getRunaAbilityCardList().get(abilityIndex).isDmgType()) {
                if (castRunasAbilityHelper(abilityIndex,monsterIndex).equals("quit")) {
                    return "quit";
                }
            }
            else {
                runa.getRunaAbilityCardList().get(abilityIndex).castAbility(runa, enemies.get(monsterIndex), 0);
            }
            // If the target from the offensive ability dies
            if (enemies.get(monsterIndex).getHealthPoints() == 0) {
                System.out.println(enemies.remove(monsterIndex).getName() + " dies");
            }
        }
        else {
            runa.getRunaAbilityCardList().get(abilityIndex).castAbility(runa, enemies.get(monsterIndex), 0);
        }
        return "";
    }

    /**
     * This method just splits castRunasAbility() into two methods
     *
     * @param abilityIndex The index of the chosen ability, previously picked in 'selectCardToPlay()'
     * @param monsterIndex The index of the chosen monster, previously picked in 'selectTarget()'
     * @return "quit" if the user inputs quit, nothing relevant otherwise
     */
    public String castRunasAbilityHelper(int abilityIndex, int monsterIndex) {

        Scanner sc = new Scanner(System.in);
        String input;

        while (true) {
            System.out.println("Enter dice roll [1--" + runa.getDice() + "]:");


            input = sc.nextLine();
            input = cutPrefix(input);

            if (input.equals("quit")) {
                return input;
            }
            else {
                int dice = 0;
                try {
                    // Tries to parse the input into int and cast her ability with it
                    dice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    //Nothing
                }
                if (dice > 0 && dice <= runa.getDice()) {
                    runa.getRunaAbilityCardList().get(abilityIndex)
                            .castAbility(runa, enemies.get(monsterIndex), dice);
                    break;
                }
            }
        }
        return "";
    }

    /**
     * This method checks whether the fight is over, it only checks the monsters status, because Runa's life status
     * will be checked at the end of 'monsterUsesAbility()'
     *
     * @return true if both monsters are dead, false otherwise
     */
    public boolean isTheFightOver() {
        return enemies.size() == 0;
    }

    /**
     * This method contains the instructions for a monster to cast his ability, also checks
     * whether Runa or the monster died in this process
     *
     * @param runa Runa
     * @param monster the monster that casts the ability
     * @param dice IGNORE THIS ONE
     * @return "quit" if Runa dies, nothing relevant otherwise
     */
    public String monsterUsesAbility(Hero runa, Monster monster, int dice) {
        // If the next ability is a magical one, it will be checked, if enough focus points are available
        // if this is not the case, the abilityPointer will be updated, until one ability is found!
        if (!monster.getMonsterAbilityCardList().get(monster.getAbilityPointer()).isDmgType()) {
            while (monster.getMonsterAbilityCardList().get(monster.getAbilityPointer()).getFocusCosts()
                    > monster.getFocusPoints()) {
                monster.updateAbilityPointer();
            }
        }

        System.out.println(monster.getName() + " uses "
                + monster.getMonsterAbilityCardList().get(monster.getAbilityPointer()).getStringAbilityName()
                + "(" + monster.getMonsterAbilityCardList().get(monster.getAbilityPointer()).getCardLevel() + ")");

        monster.getMonsterAbilityCardList().get(monster.getAbilityPointer()).castAbility(runa, monster, dice);

        // If Runa or the monster, that cast the spell dies (is possible due to Runa's reflect ability)
        if (runa.getHealthPoints() == 0) {
            System.out.println("Runa dies");
            return "quit";
        }
        else if (monster.getHealthPoints() == 0) {
            System.out.println(monster.getName() + " dies");
            return "MonsterDead";
        }
        monster.updateAbilityPointer();
        return "";
    }

    /**
     * This method checks whether "Focus" was used last round and increases the FP of the given entity by 1
     * if it wasn't cancelled already
     *
     * @param target the Entity that eventually used Focus before
     */
    public void checkIfFocusWasCastLastRound(Entity target) {

        if (target.isLoadsFocus()) {

            // If the target of 'Focus' is Runa
            if (target.equals(runa) && runa.getFocusPoints() < runa.getDice()) {
                if (runa.getHowMuchFocusWillBeAdded() + runa.getFocusPoints() > runa.getDice()) {
                    runa.setHowMuchFocusWillBeAdded(runa.getHowMuchFocusWillBeAdded() - 1);
                }
                target.setFocusPoints(target.getFocusPoints() + target.getHowMuchFocusWillBeAdded());
                System.out.println(target.getName() + " gains " + target.getHowMuchFocusWillBeAdded() + " focus");
                target.setLoadsFocus(false);
            }
            // If the target of 'Focus' is not Runa
            else if (!target.equals(runa)) {
                target.setFocusPoints(target.getFocusPoints() + target.getHowMuchFocusWillBeAdded());
                System.out.println(target.getName() + " gains " + target.getHowMuchFocusWillBeAdded() + " focus");
                target.setLoadsFocus(false);
            }
        }
        target.setHowMuchFocusWillBeAdded(0);
    }

    /**
     * This method just sets the armor of the given Entity to 0
     *
     * @param target Entity that may have armor left
     */
    public void reduceArmorToZero(Entity target) {
        target.setPhyArmor(0);
        target.setMagArmor(0);
    }

    /**
     * This method cuts the positive prefix from the given input
     *
     * @param input the given input, where a positive prefix might exist
     */
    public String cutPrefix(String input) {
        String temp = input;

        if (input.length() != 0) {
            if (input.charAt(0) == '+') {
                temp = input.substring(1);
            }
        }
        return temp;
    }
}
