package cards;

import entities.MonsterCreator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @param <T> will be either 'MonsterAbilityCard' or 'RunaAbilityCard'
 *
 * This class will create objects of full card decks for both card types
 */
public class Deck<T> {

    private String deckType;
    private List<T> deck;
    private int cardLevel;

    /**
     * Constructor of Deck, initializes a Deck instantly based on decktype and cardLevel
     *
     * @param deckType the type of the deck (Runa's abilities / Level 1 Monsters / Level 2 Monsters)
     * @param cardLevel Level of the cards to put in Runa's deck
     */
    public Deck(String deckType, int cardLevel) {
        deck = new ArrayList<>();
        this.deckType = deckType;
        this.cardLevel = cardLevel;

        initializeDeck();
    }

    /**
     * This method fills the Deck with the Runa's ability cards / Monster cards, based on the type of the deck
     */
    public void initializeDeck() {

        MonsterCreator creator = new MonsterCreator();

        if (deckType.equals("LevelOneMonstersDeck")) {
            deck = (List<T>) creator.createLevelOneMonsterList();
        }
        else if (deckType.equals("LevelTwoMonstersDeck")) {
            deck = (List<T>) creator.createLevelTwoMonsterList();
        }
        else if (deckType.equals("RunasDeck")) {
            for (RunasAbilities ability : RunasAbilities.values()) {
                RunaAbilityCard card = new RunaAbilityCard(ability, cardLevel);
                deck.add((T) card);
            }
        }
    }

    /**
     * This method shuffles the deck
     * @param seed a seed, which defines how a deck will be shuffled
     */
    public void shuffleDeck(int seed) {
        Collections.shuffle(deck, new Random(seed));
    }

    /**
     * Getter of the List, containing the cards
     * @return the card deck
     */
    public List<T> getDeck() {
        return deck;
    }

    /**
     * Setter of the List, containing the cards
     * @param deck the card deck
     */
    public void setDeck(List<T> deck) {
        this.deck = deck;
    }

    /**
     * Getter of the decktype, defining which type of deck this is
     * @return the decktype
     */
    public String getDeckType() {
        return deckType;
    }

    /**
     * Setter of the decktype, defining which type of deck this is
     * @param deckType the decktype
     */
    public void setDeckType(String deckType) {
        this.deckType = deckType;
    }
}
