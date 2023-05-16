package gameinstructions;

import entities.Hero;

import java.util.Scanner;

/**
 *
 * This class contains all the interactions Runa can make after she won a fight. This includes getting new cards,
 * upgrading her dice and heal herself
 */
public class AfterFightInteractions {

    private Hero runa;
    private int stage;

    /**
     * This is the Constructor for AfterFightIntercations, needs Runa and her current stage, to define
     * how many cards she will be rewarded with
     *
     * @param runa Runa
     * @param stage her current stage
     */
    public AfterFightInteractions(Hero runa, int stage) {
        this.runa = runa;
        this.stage = stage;
    }

    /**
     * This method must be cast to define which kind of reward Runa gets, based on the player's input
     *
     * @return "quit" if the user inputs quit, nothing relevant otherwise
     */
    public String getReward() {
        int choice = 0;


        if (runa.getDice() < 12) {
            System.out.println("Choose Runa's reward");
            System.out.println("1) new ability cards");
            System.out.println("2) next player dice");

            Scanner sc = new Scanner(System.in);
            String input;

            do {
                System.out.println("Enter number [1--2]:");

                input = sc.nextLine();
                input = cutPrefix(input);
                if (input.equals("quit")) {
                    return input;
                }

                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    // Nothing
                }

            } while (choice != 1 && choice != 2);
        }
        else {
            choice = 1;
        }
        if (choice == 1) {
            if (pickLoot().equals("quit")) {
                return "quit";
            }
        }
        else {
            upgradeDice();
        }
        return "";
    }

    /**
     * This method will be called if the player decides to get new cards after a fight. It is split into many
     * methods, because some of them could be used at the healing process as well.
     *
     * @return "quit" if the user inputs quit, nothing relevant otherwise
     */
    public String pickLoot() {

        int count = printOptions();

        String cardChoices = pickOneOrMoreCardsAndCheckTheInput(count, false);

        if (cardChoices.equals("quit")) {
            return cardChoices;
        }

        int[] pickedCards = formatStringIntoIntArray(cardChoices);

        giveRunaPickedRewards(pickedCards);
        return "";
    }

    /**
     * This method prints Runa's options to pick new cards from the deck, based on her current stage and previous
     * choices (if she took 3 times cards, the third time will only show 3 cards instead of 4)
     *
     * @return the amount of cards, the player has to choose from
     */
    public int printOptions() {
        int count = 2;
        if (stage == 1) {
            System.out.println("Pick 1 card(s) as loot");
        }
        else {
            System.out.println("Pick 2 card(s) as loot");
        }

        if (stage > 1) {
            count++;
            if (runa.getDeck().getDeck().size() > 3) {
                count++;
            }
        }
        runa.printNextDeckCards(count);
        return count;
    }

    /**
     * This method gives Runa her new cards, based on the decision the player made. Not picked cards will be put out
     * of the game!
     *
     * @param picked the choices the player made
     */
    public void giveRunaPickedRewards(int[] picked) {

        System.out.println("Runa gets " + runa.getDeck().getDeck().get(picked[0] - 1).getStringAbilityName()
                + "(" + runa.getDeck().getDeck().get(picked[0] - 1).getCardLevel() + ")");
        runa.getRunaAbilityCardList().add(runa.getDeck().getDeck().remove(picked[0] - 1));

        if (picked.length == 2) {

            if (picked[0] < picked[1]) {
                System.out.println("Runa gets " + runa.getDeck().getDeck().get(picked[1] - 2).getStringAbilityName()
                    + "(" + runa.getDeck().getDeck().get(picked[1] - 2).getCardLevel() + ")");

                runa.getRunaAbilityCardList().add(runa.getDeck().getDeck().remove(picked[1] - 2));
            }
            else {
                System.out.println("Runa gets " + runa.getDeck().getDeck().get(picked[1] - 1).getStringAbilityName()
                        + "(" + runa.getDeck().getDeck().get(picked[1] - 1).getCardLevel() + ")");
                runa.getRunaAbilityCardList().add(runa.getDeck().getDeck().remove(picked[1] - 1));
            }
        }

        // Removes one left over card for sure, a second if it is needed and possible
        runa.getDeck().getDeck().remove(0);

        if (stage > 1 && runa.getDeck().getDeck().size() > 0) {
            runa.getDeck().getDeck().remove(0);
        }
    }

    /**
     * This method upgrades the dice, if that is possible, is already checked in 'getReward()'
     */
    public void upgradeDice() {
        runa.setDice(runa.getDice() + 2);
        System.out.println("Runa upgrades her die to a d" + runa.getDice());
    }

    /**
     * This method contains the instructions to heal Runa. If she has 50 HP, they won't be executed. She can skip this
     * process by just entering a space + enter. Healing costs ability cards. They will be removed correctly
     * if the player chooses to heal
     *
     * @return "quit" if the user inputs quit, nothing relevant otherwise
     */
    public String healProcess() {

        if (runa.getRunaAbilityCardList().size() < 2) {
            return "";
        }
        if (runa.getHealthPoints() != 50) {
            System.out.println("Runa (" + runa.getHealthPoints()
                    + "/50 HP) can discard ability cards for healing (or none)");

            runa.printNextAbilityCards(runa.getRunaAbilityCardList().size());

            String cardChoices = pickOneOrMoreCardsAndCheckTheInput(runa.getRunaAbilityCardList().size(), true);

            if (cardChoices.equals("quit") || cardChoices.equals("")) {
                return cardChoices;
            }

            int[] pickedCards = formatStringIntoIntArray(cardChoices);

            // Sorting algorithm, because we are using an Array instead on a list
            for (int i = 0; i < pickedCards.length; i++) {
                for (int j = i + 1; j < pickedCards.length; j++) {
                    if (pickedCards[j] > pickedCards[i]) {
                        int temp = pickedCards[i];
                        pickedCards[i] = pickedCards[j];
                        pickedCards[j] = temp;
                    }
                }
            }
            // After sorting in descending order, the indexes will be removed from Runa's ability card list
            for (int pickedCard : pickedCards) {
                runa.getRunaAbilityCardList().remove(pickedCard - 1);
            }

            int h1 = runa.getHealthPoints();
            runa.setHealthPoints(runa.getHealthPoints() + (10 * pickedCards.length));
            int h2 = runa.getHealthPoints();
            System.out.println("Runa gains " + (h2 - h1) + " health");
        }
        return "";
    }

    /**
     * This method contains an algorithm to pick cards and check the input if it fits the conventions. For example:
     * You cannot pick 3 cards, if you are only allowed to choose two, or you can't pick card '7' if there are only
     * 3 cards to pick from. The healing process has some extra rules, that is why a variable 'heal' is needed.
     * It helps to difference the two processes where this algorithm will be needed,
     *
     * @param topEnd the last card, if you can choose from 4 cards, this variable will be 4
     * @param heal true, if healProcess() called this method, false otherwise
     * @return "quit" if the user inputs quit, nothing relevant otherwise
     */
    public String pickOneOrMoreCardsAndCheckTheInput(int topEnd, boolean heal) {

        Scanner sc = new Scanner(System.in);
        String input;
        // Have to initialize it, because it's actually initialized inside a try block
        int[] pickedCards = new int[1];

        while (true) {
            if (topEnd <= 2) {
                System.out.println("Enter number [1--" + topEnd + "]:");
            }
            else {
                System.out.println("Enter numbers [1--" + topEnd + "] separated by comma:");
            }

            input = sc.nextLine();
            input = cutPrefix(input);
            if (input.equals("quit")) {
                return input;
            }

            // Special case, you can cancel the healing with entering just a space
            if (input.equals("") && heal) {
                return input;
            }

            try {
                pickedCards = formatStringIntoIntArray(input);
            }
            catch (NumberFormatException e) {
                // Nothing
            }
            // If all everything fits the conventions, it breaks the loop
            if (checkAllConventions(pickedCards, topEnd, heal)) {
                break;
            }
        }
        return input;
    }

    /**
     * This method contains all rules that the users choices have to follow
     *
     * @param pickedCards the indexes of the picked cards
     * @param topEnd the highest pickable card
     * @param heal true if healProces() called this method, false otherwise
     * @return true, if everything is alright, false otherwise
     */
    public boolean checkAllConventions(int[] pickedCards, int topEnd, boolean heal) {

        // Checks all integers, if they fit into the conventions
        boolean allNumbersCorrect = true;

        // Checks if the fit in the number space 1 - 2/3/4
        for (int pickedCard : pickedCards) {
            if (pickedCard < 1 || pickedCard > topEnd) {
                allNumbersCorrect = false;
                break;
            }
        }
        // If you typed in more than two numbers, it is incorrect
        if (pickedCards.length > 2 && !heal) {
            allNumbersCorrect = false;
        }
        // If you typed two times the same number
        for (int i = 0; i < pickedCards.length; i++) {
            for (int j = i + 1; j < pickedCards.length; j++) {
                if (pickedCards[i] == pickedCards[j]) {
                    allNumbersCorrect = false;
                    break;
                }
            }
        }
        // If you are instructed to choose two cards, but just take one
        if (stage != 1 && pickedCards.length == 1 && !heal) {
            allNumbersCorrect = false;
        }
        // If you want to heal and want to pick all cards you have
        if (heal && pickedCards.length == runa.getRunaAbilityCardList().size()) {
            allNumbersCorrect = false;
        }
        return allNumbersCorrect;
    }

    /**
     * This method formats the given input into an int[], this will be needed several times
     *
     * @param input the input, the player made, often when he was prompted to choose from different cards
     * @return the int[] from the input
     * @throws NumberFormatException if the input does not only contain ints and commas
     */
    public int[] formatStringIntoIntArray(String input) throws NumberFormatException {

        if (input.equals("")) {
            throw new NumberFormatException();
        }
        if (input.endsWith(",")) {
            throw new NumberFormatException();
        }

        String[] arr = input.split(",");
        int[] pickedCards = new int[arr.length];

        // Tries to convert all String inputs into integers
        for (int i = 0; i < arr.length; i++) {
            pickedCards[i] = Integer.parseInt(arr[i]);
        }
        return pickedCards;
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
