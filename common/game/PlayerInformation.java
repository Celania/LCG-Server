package game;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import effects.base.AbstractEffect;
import events.Event;
import manager.EffectManager;
import stores.DBStore;
import units.BaseCard;
import units.Card;
import units.PlayedUnit;
import units.Spell;
import units.info.EffectTargetable;

import communication.Message;

import connection.Client;
import effects.base.SpellEffect;
import units.info.SpellTargetable;

public class PlayerInformation implements EffectTargetable, SpellTargetable {

    private EffectManager         effectManager;

    // add PlayedUnit component so we can let character effects affect players too
    private PlayedUnit            hero;

    private int                   armor;
    private int                   heroChoice;
    private int                   lifePoints;

    private int                   maxMana;
    private int                   mana;

    /* how to resolve overloads? */
    private int                   overload;
    private int                   locked;

    private int                   weaponID;
    private int                   playerID;
    private List<PlayedUnit>      board;
    private List<Card>            deck;
    private List<Card>            hand;
    private Client                client;
    public BlockingQueue<Message> outputQueue;

    // IDEA do we really need baseID, or can the baseID be deducted from the hero class
    public PlayerInformation(Client client, EffectManager effectManager, int ID, int unitID, int baseID) {

        this.effectManager = effectManager;

        this.playerID = ID;
        this.armor = 0;
        this.board = new LinkedList<PlayedUnit>();
        this.deck = new LinkedList<Card>();
        this.hand = new LinkedList<Card>();
        this.heroChoice = client.getHeroChoice();
        this.lifePoints = 30;
        this.maxMana = 0;
        this.mana = 0;
        this.weaponID = 0;
        this.client = client;

        // this.hero = new PlayedUnit(effectManager, this, unitID, DBStore.getUnit(baseID), BaseCard.CardType.Player);

        this.outputQueue = new LinkedBlockingQueue<Message>();

    }

    public UUID getID() {
        return client.getID();
    }

    public int getHeroChoice() {
        return heroChoice;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getMana() {
        return mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void alterMaxMana(int amount) {
        maxMana = (maxMana + amount) % 10;
    }

    public void alterMana(int amount) {
        mana = (mana + amount) % 10;
    }

    public void resetMana() {
        mana = maxMana - overload;
        locked = overload;
        overload = 0;
    }

    public List<PlayedUnit> getBoard() {
        return board;
    }

    public List<Card> getHand() {
        return hand;
    }

    public PlayedUnit getUnit() {
        return hero;
    }

    @Override
    public void applySpell(Spell spell) {
        spell.directedApply(effectManager, this);
    }

    @Override
    public void applySpellEffect(SpellEffect spellEffect, PlayerInformation caster) {
        spellEffect.apply(effectManager, caster, this);
    }

    @Override
    public boolean isValidForSpell(Spell spell) {
        return spell.isValidTarget(this);
    }

    @Override
    public void applyEffect(EffectManager effectManager, AbstractEffect abstractEffect, int param1, int param2) {
        abstractEffect.apply(effectManager, this, param1, param2);
    }

    @Override
    public void undo(EffectManager effectManager, AbstractEffect abstractEffect, int param1, int param2) {

    }
}
