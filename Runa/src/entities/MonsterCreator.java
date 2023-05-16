package entities;

import cards.MonsterAbilityCard;
import cards.MonstersAbilities;
import generals.Element;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class contains all methods to create decks of monsters or boss monsters
 */
public class MonsterCreator {

    /**
     * This method creates a list containing all the monsters from level 1, exclusive the boss!
     * @return list full of level 1 monsters
     */
    public List<Monster> createLevelOneMonsterList() {
        List<Monster> list = new ArrayList<>();

        // Adds all the level 1 monsters to the list
        list.add(createFrog());
        list.add(createGhost());
        list.add(createGorgon());
        list.add(createSkeleton());
        list.add(createSpider());
        list.add(createGoblin());
        list.add(createRat());
        list.add(createMushroomlin());

        return list;
    }

    /**
     * This method creates a list containing all the monsters from level 2, exclusive the boss!
     * @return list full of level 2 monsters
     */
    public List<Monster> createLevelTwoMonsterList() {
        List<Monster> list = new ArrayList<>();

        // Adds all the level 2 monsters to the list
        list.add(createSnake());
        list.add(createDarkElf());
        list.add(createShadowBlade());
        list.add(createHornet());
        list.add(createTarantula());
        list.add(createBear());
        list.add(createMushroomlon());
        list.add(createWildBoar());

        return list;
    }

    /**
     * This method creates a Spider King entity
     * @return Monster Spider King
     */
    public Monster createSpiderKing() {
        Monster spiderKing = new Monster("Spider King", 50, true, Element.LIGHTNING, 1);
        // Adds the abilities to the Spider King
        spiderKing.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.BITE, 1));
        spiderKing.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.BLOCK, 1));
        spiderKing.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.FOCUS, 1));
        spiderKing.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.LIGHTNING, 1));
        return spiderKing;
    }

    /**
     * This method creates a Frog entity
     * @return Monster Frog
     */
    public Monster createFrog() {
        Monster frog = new Monster("Frog", 16, false, Element.WATER, 1);
        // Adds the abilities to the Frog
        frog.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.FOCUS, 1));
        frog.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.WATER, 1));
        return frog;
    }

    /**
     * This method creates a Ghost entity
     * @return Monster Ghost
     */
    public Monster createGhost() {
        Monster ghost = new Monster("Ghost", 15, false, Element.ICE, 1);
        // Adds the abilities to the Ghost
        ghost.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.FOCUS, 1));
        ghost.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.ICE, 1));
        return ghost;
    }

    /**
     * This method creates a Gorgon entity
     * @return Monster Gorgon
     */
    public Monster createGorgon() {
        Monster gorgon = new Monster("Gorgon", 13, false, Element.FIRE, 1);
        // Adds the abilities to the Gorgon
        gorgon.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.FOCUS, 1));
        gorgon.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.FIRE, 1));
        return gorgon;
    }

    /**
     * This method creates a Skeleton entity
     * @return Monster Skeleton
     */
    public Monster createSkeleton() {
        Monster skeleton = new Monster("Skeleton", 14, false, Element.LIGHTNING, 1);
        // Adds the abilities to the Skeleton
        skeleton.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.FOCUS, 1));
        skeleton.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.LIGHTNING, 1));
        return skeleton;
    }

    /**
     * This method creates a Spider entity
     * @return Monster Spider
     */
    public Monster createSpider() {
        Monster spider = new Monster("Spider", 15, false, Element.NONE, 1);
        // Adds the abilities to the Spider
        spider.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.BITE, 1));
        spider.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.BLOCK, 1));
        return spider;
    }

    /**
     * This method creates a Goblin entity
     * @return Monster Goblin
     */
    public Monster createGoblin() {
        Monster goblin = new Monster("Goblin", 12, false, Element.NONE, 1);
        // Adds the abilities to the Goblin
        goblin.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.SMASH, 1));
        goblin.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.DEFLECT, 1));
        return goblin;
    }

    /**
     * This method creates a Rat entity
     * @return Monster Rat
     */
    public Monster createRat() {
        Monster rat = new Monster("Rat", 14, false, Element.NONE, 1);
        // Adds the abilities to the Rat
        rat.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.BLOCK, 1));
        rat.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.CLAW, 1));
        return rat;
    }

    /**
     * This method creates a Mushroomlin entity
     * @return Monster Mushroomlin
     */
    public Monster createMushroomlin() {
        Monster mushroomlin = new Monster("Mushroomlin", 20, false, Element.NONE, 1);
        // Adds the abilities to the Mushroomlin
        mushroomlin.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.DEFLECT, 1));
        mushroomlin.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.SCRATCH, 1));
        return mushroomlin;
    }

    /**
     * This method creates a Mega Saurus entity
     * @return Monster Mega Saurus
     */
    public Monster createMegaSaurus() {
        Monster megaSaurus = new Monster("Mega Saurus", 100, true, Element.NONE, 2);
        // Adds the abilities to the Mega Saurus
        megaSaurus.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.BITE, 2));
        megaSaurus.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.BLOCK, 2));
        megaSaurus.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.FOCUS, 2));
        megaSaurus.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.FIRE, 1));
        megaSaurus.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.LIGHTNING, 1));
        return megaSaurus;
    }

    /**
     * This method creates a Snake entity
     * @return Monster Snake
     */
    public Monster createSnake() {
        Monster snake = new Monster("Snake", 31, false, Element.ICE, 2);
        // Adds the abilities to the Snake
        snake.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.BITE, 2));
        snake.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.FOCUS, 2));
        snake.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.ICE, 2));
        return snake;
    }

    /**
     * This method creates a Dark Elf entity
     * @return Monster Dark Elf
     */
    public Monster createDarkElf() {
        Monster darkElf = new Monster("Dark Elf", 34, false, Element.NONE, 2);
        // Adds the abilities to the Dark Elf
        darkElf.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.FOCUS, 2));
        darkElf.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.WATER, 1));
        darkElf.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.LIGHTNING, 1));
        return darkElf;
    }

    /**
     * This method creates a Shadow Blade entity
     * @return Monster Shadow Blade
     */
    public Monster createShadowBlade() {
        Monster shadowBlade = new Monster("Shadow Blade", 27, false, Element.LIGHTNING, 2);
        // Adds the abilities to the Shadow Blade
        shadowBlade.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.SCRATCH, 2));
        shadowBlade.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.FOCUS, 2));
        shadowBlade.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.LIGHTNING, 2));
        return shadowBlade;
    }

    /**
     * This method creates a Hornet entity
     * @return Monster Hornet
     */
    public Monster createHornet() {
        Monster hornet = new Monster("Hornet", 32, false, Element.FIRE, 2);
        // Adds the abilities to the Hornet
        hornet.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.SCRATCH, 2));
        hornet.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.FOCUS, 2));
        hornet.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.FIRE, 1));
        hornet.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.FIRE, 2));
        return hornet;
    }

    /**
     * This method creates a Tarantula entity
     * @return Monster Tarantula
     */
    public Monster createTarantula() {
        Monster tarantula = new Monster("Tarantula", 33, false, Element.NONE, 2);
        // Adds the abilities to the Tarantula
        tarantula.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.BITE, 2));
        tarantula.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.BLOCK, 2));
        tarantula.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.SCRATCH, 2));
        return tarantula;
    }

    /**
     * This method creates a Bear entity
     * @return Monster Bear
     */
    public Monster createBear() {
        Monster bear = new Monster("Bear", 40, false, Element.NONE, 2);
        // Adds the abilities to the Bear
        bear.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.CLAW, 2));
        bear.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.DEFLECT, 2));
        bear.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.BLOCK, 2));
        return bear;
    }

    /**
     * This method creates a Mushroomlon entity
     * @return Monster Mushroomlon
     */
    public Monster createMushroomlon() {
        Monster mushroomlon = new Monster("Mushroomlon", 50, false, Element.NONE, 2);
        // Adds the abilities to the Mushroomlon
        mushroomlon.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.DEFLECT, 2));
        mushroomlon.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.SCRATCH, 2));
        mushroomlon.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.BLOCK, 2));
        return mushroomlon;
    }

    /**
     * This method creates a Wild Boar entity
     * @return Monster Wild Boar
     */
    public Monster createWildBoar() {
        Monster wildBoar = new Monster("Wild Boar", 27, false, Element.NONE, 2);
        // Adds the abilities to the Wild Boar
        wildBoar.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.SCRATCH, 2));
        wildBoar.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.DEFLECT, 2));
        wildBoar.getMonsterAbilityCardList().add(new MonsterAbilityCard(MonstersAbilities.SCRATCH, 2));
        return wildBoar;
    }
}
