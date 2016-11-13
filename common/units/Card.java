package units;

import game.PlayerInformation;

// TODO Player holds Cards in hand, shouldnt be abstract?
// TODO should a card be targetable?
public abstract class Card implements CardInfo {

    protected int             modifyMana;

    private PlayerInformation owner;

    protected Card(PlayerInformation owner) {
        this.owner = owner;
    }

    public PlayerInformation getOwner() {
        return owner;
    }

    public void modifyManaCost(int amount) {
        modifyMana += amount;
    }

    public void resetManaCost() {
        modifyMana = 0;
    }

}
