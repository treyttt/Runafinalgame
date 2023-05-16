package entities;

import cards.Deck;
import cards.RunaAbilityCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents the Hero 'Runa'
 *
 */
public class Hero extends Entity {

    private String characterClass;
    private int dice;

    // Ability Cards
    private Deck<RunaAbilityCard> deck;
    private List<RunaAbilityCard> runaAbilityCardList;

    /**
     * Constructor for entities.Hero, name is always Runa, dice starts with 4 sides
     */
    public Hero() {
        super("Runa", 50, 1, 0, 0);
        dice = 4;

        runaAbilityCardList = new ArrayList<>();
        deck = new Deck<RunaAbilityCard>("RunasDeck", 1);
    }

    /**
     * This method lets the user choose the class of Runa
     * @return String quit, Warrior, Mage or Paladin
     */
    public String defineCharacterClass() {

        System.out.println("Select Runa's character class");
        System.out.println("1) Warrior\n2) Mage\n3) Paladin");
        System.out.println("Enter number [1--3]:");

        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (true) {
            input = scanner.nextLine();

            if (input.length() != 0) {
                if (input.charAt(0) == '+') {
                    input = input.substring(1);
                }
            }
            if (input.equals("quit")) {
                return "quit";
            }
            else {
                if (input.equals("1")) {
                    characterClass = "Warrior";
                    setFirstTwoCards();
                    return "Warrior";
                }
                else if (input.equals("2")) {
                    characterClass = "Mage";
                    setFirstTwoCards();
                    return "Mage";
                }
                else if (input.equals("3")) {
                    characterClass = "Paladin";
                    setFirstTwoCards();
                    return "Paladin";
                }
                else {
                    System.out.println("Enter number [1--3]:");
                }
            }
        }
    }

    /**
     * This method gives Runa her first two cards, based on her class, wil be called again after Runa did
     * kill the Spider King, to give her the first two cards again, just level 2
     */
    public void setFirstTwoCards() {

        /*
         * Iterates through the whole deck and gives Runa the first two cards, based on her class,
         * The control variable 'i' has to be reduced, if a card from deck is removed, because it wouldn't
         * check all cards existing if I didn't reduce it (Check how deleting an item from list works for details)
         */
        int pointer = 0;
        for (int i = 0; i < deck.getDeck().size(); i++) {

            // If Runa is a warrior
            if (characterClass.equals("Warrior")) {
                if (deck.getDeck().get(pointer).getAbilityName().toString().equals("THRUST")) {
                    runaAbilityCardList.add(deck.getDeck().remove(pointer));
                    pointer--;
                }
                else if (deck.getDeck().get(pointer).getAbilityName().toString().equals("PARRY")) {
                    runaAbilityCardList.add(deck.getDeck().remove(pointer));
                    pointer--;
                }
            }
            // If Runa is a mage
            else if (characterClass.equals("Mage")) {
                if (deck.getDeck().get(pointer).getAbilityName().toString().equals("WATER")) {
                    runaAbilityCardList.add(deck.getDeck().remove(pointer));
                    pointer--;
                }
                else if (deck.getDeck().get(pointer).getAbilityName().toString().equals("FOCUS")) {
                    runaAbilityCardList.add(deck.getDeck().remove(pointer));
                    pointer--;
                }
            }
            // If Runa is a paladin
            else if (characterClass.equals("Paladin")) {
                if (deck.getDeck().get(pointer).getAbilityName().toString().equals("SLASH")) {
                    runaAbilityCardList.add(deck.getDeck().remove(pointer));
                    pointer--;
                }
                else if (deck.getDeck().get(pointer).getAbilityName().toString().equals("REFLECT")) {
                    runaAbilityCardList.add(deck.getDeck().remove(pointer));
                    pointer--;
                }
            }
            pointer++;
        }
    }

    /**
     * This method prints the next 'amount' ability cards of the deck, starts with index 0 - 'amount - 1'
     *
     * @param amount the amount of cards that should be printed
     */
    public void printNextDeckCards(int amount) {
        for (int i = 0; i < amount; i++) {
            System.out.println((i + 1) + ") " + this.getDeck().getDeck().get(i).getStringAbilityName()
                    + "(" + this.getDeck().getDeck().get(i).getCardLevel() + ")");
        }
    }

    /**
     * This method prints the next 'amount' ability cards of Runa's ability card hand.
     *
     * @param amount the amount of cards that should be printed
     */
    public void printNextAbilityCards(int amount) {
        for (int i = 0; i < amount; i++) {
            System.out.println((i + 1) + ") " + this.getRunaAbilityCardList().get(i).getStringAbilityName()
                    + "(" + this.getRunaAbilityCardList().get(i).getCardLevel() + ")");
        }
    }

    /**
     * Getter for characterClass
     * @return the characterClass
     */
    public String getCharacterClass() {
        return characterClass;
    }

    /**
     * Setter for characterClass
     * @param characterClass contains new CharacteClass
     */
    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    /**
     * Getter for the dice
     * @return number of dice's sides
     */
    public int getDice() {
        return dice;
    }

    /**
     * Setter for dice
     * @param dice number of dice's sides
     */
    public void setDice(int dice) {
        this.dice = dice;
    }

    /**
     * Getter for Runa's WHOLE abilitydeck, contains all cards available at the beginning
     * @return the deck
     */
    public Deck<RunaAbilityCard> getDeck() {
        return deck;
    }

    /**
     * Setter for Runa's deck
     * @param deck Runa's deck
     */
    public void setDeck(Deck<RunaAbilityCard> deck) {
        this.deck = deck;
    }

    /**
     * Getter for Runa's abilities, that she can cast
     * @return the abilities she can cast
     */
    public List<RunaAbilityCard> getRunaAbilityCardList() {
        return runaAbilityCardList;
    }

    /**
     * Setter for Runa's abilities
     * @param runaAbilityCardList the abilities she can cast
     */
    public void setRunaAbilityCardList(List<RunaAbilityCard> runaAbilityCardList) {
        this.runaAbilityCardList = runaAbilityCardList;
    }
}

