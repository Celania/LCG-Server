package units;


public abstract class BaseCard implements CardInfo {

    private final int    baseID;

    private final int    baseManaCost;

    private final int    cardType;

    private final String name;
    private final String description;

    // use bitfield to cover creature and player under unit?
    public static class CardType {
        public static final int Creature = 0;
        public static final int Spell    = 1;
        public static final int Player   = 2;
        public static final int Unit     = 3;
    }

    protected BaseCard(int baseID, String name, int baseManaCost, String description, int cardType) {
        this.baseID = baseID;
        this.name = name;
        this.cardType = cardType;
        this.baseManaCost = baseManaCost;
        this.description = description;
    }

    public int getManaCost() {
        return baseManaCost;
    }

    public int getBaseID() {
        return baseID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCardType() {
        return cardType;
    };

}
