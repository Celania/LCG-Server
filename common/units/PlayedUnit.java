package units;

import effects.base.AbstractEffect;
import effects.base.SpellEffect;
import events.game.CreatureDamageTaken;
import game.PlayerInformation;
import manager.EffectManager;
import manager.EventSource;
import units.info.EffectTargetable;
import units.info.SpellTargetable;

public class PlayedUnit implements EffectTargetable, EventSource, SpellTargetable {

    private EffectManager     effectManager;
    private BaseUnit          baseUnit;     // final? what happens if we want to change a creature
    // private List<Integer> activeEffectsID; // ToDo

    private int               type;
    private int               ID;
    private int               statusMask;
    private int               maxHealth;
    private int               health;
    private int               attack;
    private boolean           alive;        // mark creature as dead and have game remove dead creatures?
    private PlayerInformation owner;

    public PlayedUnit(EffectManager effectManager, PlayerInformation owner, int playedID, BaseUnit baseUnit) {
        this.baseUnit = baseUnit;
        this.effectManager = effectManager;
        ID = playedID;
        health = baseUnit.getBaseHealth();
        maxHealth = baseUnit.getBaseHealth();
        attack = baseUnit.getBaseAttack();
        statusMask = baseUnit.getBaseStatusMask();
        alive = true;

        this.owner = owner;

        effectManager.addPermEffects(baseUnit.getPermEffects().iterator(), this);
        effectManager.addTempEffects(baseUnit.getTempEffects().iterator(), this);
    }

    public PlayedUnit(EffectManager effectManager, PlayerInformation owner, int playedID, BaseUnit baseUnit,
            int cardType) {
        this.baseUnit = baseUnit;
        this.effectManager = effectManager;
        ID = playedID;
        health = baseUnit.getBaseHealth();
        maxHealth = baseUnit.getBaseHealth();
        attack = baseUnit.getBaseAttack();
        statusMask = baseUnit.getBaseStatusMask();
        alive = true;

        this.owner = owner;

        effectManager.addPermEffects(baseUnit.getPermEffects().iterator(), this);
        effectManager.addTempEffects(baseUnit.getTempEffects().iterator(), this);
    }

    public void addEffect(int trigger, int effect, int param1, int param2, int duration) {
    }

    public void addStatus(int status) {
        this.statusMask |= status;
        // EventManager event
    }

    public boolean hasStatus(int status) {
        return ((this.statusMask & status) == status);
    }

    public void removeStatus(int status) {
        this.statusMask &= ~status;
    }

    public void alterHealth(Object source, int amount) {
        if (amount < 0)
            getDamaged(source, amount);
        else getHealed(source, amount);
    }

    public void alterAttack(int amount) {
        // fire Event

        attack += amount;
        if (attack < 0) attack = 0;
    }

    public void alterMaxHealth(int amount) {
        maxHealth += amount;
        if (amount > 0)
            health += amount;
        else if (health > maxHealth) health = maxHealth;
    }

    private void getDamaged(Object source, int amount) {
        //TODO if the playedunit has divine shield it doesnt take dmg at all
        health -= amount;
        effectManager.fireEvent(new CreatureDamageTaken(this, source, amount));
        if (health < 0) die();
    }

    private void getHealed(Object source, int amount) {
        health += amount % maxHealth;
    }

    public void die() {

        // remove unit from board
        alive = false;

        // Event(DEATH, this)
    }

    public int getCreatureType() {
        return baseUnit.getCreatureType();
    }

    public int getID() {
        return ID;
    }

    public int getAttack() {
        return this.attack;
    }

    public boolean isAlive() {
        return alive;
    }

    public PlayerInformation getOwner() {
        return owner;
    }

    public boolean isType(int targetTypeMask) {
        return (targetTypeMask == type);
    }

    public boolean hasOneOfStatus(int status) {
        return ((statusMask & status) > 0);
    }

    public int getCardType() {
        return baseUnit.getCardType();
    }

    public int getManaCost() {
        return baseUnit.getManaCost();
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    // EffectTargetable
    public void applyEffect(EffectManager effectManager, AbstractEffect abstractEffect, int param1, int param2) {

    }

    public void undo(EffectManager effectManager, AbstractEffect abstractEffect, int param1, int param2) {

    }

    public void applySpell(Spell spell) {
    }

    public void applySpellEffect(SpellEffect spellEffect, PlayerInformation caster) {
    }

    public boolean isValidForSpell(Spell spell) {
        return false;
    }
}
