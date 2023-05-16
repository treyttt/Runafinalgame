package gameinstructions;

import cards.Deck;
import entities.Hero;
import entities.Monster;

import java.util.Scanner;

/**
 *
 * This is the class, which contains the main method to start the program.
 */
public class Game {

    private static Hero runa;
    private static Deck<Monster> monsterDeck;
    private static String terminate;

    private static FightScenario fightScenario;
    private static AfterFightInteractions afterFightInteractions;
    private static int stage = 1;
    private static int level = 1;

    /**
     * Main entry point of the program, it contains the whole algorithm to start the game and end it, using the
     * command "quit". I personally decided to not split this main method into different submethods, because all in all
     * it is not THAT long and it would probably make it more uncomfortable to understand what and why it is how
     * it is
     *
     * @param args length = 0, otherwise the program won't start
     */
    public static void main(String[] args) {

        // Checks whether args equals null or not
        if (args.length > 0) {
            System.err.println("Error, no arguments allowed");
            return;
        }
        // Initializes a new Hero (Runa) and a full deck containing the level 1 monsters
        System.out.println("Welcome to Runa's Strive");
        runa = new Hero();
        monsterDeck = new Deck<>("LevelOneMonstersDeck", 1);

        // Defines which class Runa shall be
        terminate = runa.defineCharacterClass();
        if (terminate.equals("quit")) {
            return;
        }
        // Gets the seed and shuffles the two decks with them
        terminate = getSeed();
        if (terminate.equals("quit")) {
            return;
        }
        String[] seeds = terminate.split(",");
        runa.getDeck().shuffleDeck(Integer.parseInt(seeds[0]));
        monsterDeck.shuffleDeck(Integer.parseInt(seeds[1]));

        while (true) {

            fightScenario = new FightScenario(stage, level, runa, monsterDeck);
            afterFightInteractions = new AfterFightInteractions(runa, stage);

            if (fightScenario.startFight().equals("quit")) {
                return;
            }
            if (stage == 4) {
                if (afterBossFight().equals("quit")) {
                    return;
                }
            }
            else {
                if (afterFightInteractions.getReward().equals("quit")) {
                    return;
                }
                if (afterFightInteractions.healProcess().equals("quit")) {
                    return;
                }
            }
            stage++;
        }
    }

    /**
     * This method will be called after fighting stage 4. It will decide what to do now
     */
    public static String afterBossFight() {
        if (level == 1) {
            // Updates her current stage / level
            level++;
            stage = 0;
            // Updates Runa's deck
            runa.setDeck(new Deck<>("RunasDeck", 2));
            runa.setFirstTwoCards();
            monsterDeck = new Deck<>("LevelTwoMonstersDeck", 2);

            // Prints the two cards Runa gets after killing the Spider King
            for (int i = 0; i < runa.getRunaAbilityCardList().size(); i++) {
                if (runa.getRunaAbilityCardList().get(i).getCardLevel() == 2) {
                    System.out.println("Runa gets "
                            + runa.getRunaAbilityCardList().get(i).getStringAbilityName()
                            + "(" + runa.getRunaAbilityCardList().get(i).getCardLevel() + ")");
                }
            }
            if (afterFightInteractions.healProcess().equals("quit")) {
                return "quit";
            }
            // Gets the seed and shuffles the two decks with them
            terminate = getSeed();
            if (terminate.equals("quit")) {
                return "quit";
            }
            String[] seeds = terminate.split(",");
            runa.getDeck().shuffleDeck(Integer.parseInt(seeds[0]));
            monsterDeck.shuffleDeck(Integer.parseInt(seeds[1]));
        }
        else {
            System.out.println("Runa won!");
            return "quit";
        }
        return "";
    }

    /**
     * This method makes the user input two seeds, with the given conventions
     * @return "quit" if the user inputs quit, or a String that contains the two seeds, with a comma in between
     */
    public static String getSeed() {

        System.out.println("To shuffle ability cards and monsters, enter two seeds");
        Scanner sc = new Scanner(System.in);
        String input;

        while (true) {
            System.out.println("Enter seeds [1--2147483647] separated by comma:");
            input = sc.nextLine();
            // If the input matches the expectations

            input = checkSeeds(input);
            if (input.equals("quit")) {
                return input;
            }
            else if (!input.equals("")) {
                break;
            }
        }
        return input;
    }

    /**
     * This method checks the seeds and finds out if they fit in the number space
     *
     * @param input the User Made input
     * @return the input or "" if it doesn't fit
     */
    public static String checkSeeds(String input) {
        if (input.equals("quit")) {
            return input;
        }


        if (input.matches("\\d+,\\d+")) {
            String[] splitted = input.split(",");

            // If the two numbers fit the number space
            try {
                if (Long.parseLong(splitted[0]) > 0 && Long.parseLong(splitted[0]) <= Integer.MAX_VALUE) {
                    if (Long.parseLong(splitted[1]) > 0 && Long.parseLong(splitted[1]) <= Integer.MAX_VALUE) {
                        return input;
                    }
                }
            }
            catch(NumberFormatException e) {
                // Nothing
            }
        }
        return "";
    }
}
