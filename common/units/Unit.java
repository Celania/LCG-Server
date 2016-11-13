package units;

import java.util.List;

import effects.base.PermEffect;
import effects.base.TempEffect;
import game.PlayerInformation;

public class Unit extends Card implements UnitInfo {

    private BaseUnit baseUnit;
    private int maxHealth;
    private int attack;


    public Unit(PlayerInformation owner, BaseUnit baseUnit) {
        super(owner);
        this.baseUnit = baseUnit;
        this.maxHealth = baseUnit.getBaseHealth();
        this.attack = baseUnit.getBaseAttack();
    }

    public int getBaseStatusMask() {
        return baseUnit.getBaseStatusMask();
    }

    public int getBaseHealth() {
        return baseUnit.getBaseHealth();
    }

    public int getBaseAttack() { return baseUnit.getBaseAttack(); }

    public List<PermEffect> getPermEffects() {
        return baseUnit.getPermEffects();
    }

    public List<TempEffect> getTempEffects() {
        return baseUnit.getTempEffects();
    }

    @Override
    public int getManaCost() {
        return Math.max(baseUnit.getManaCost() + modifyMana, 0);
    }

    @Override
    public int getBaseID() {
        return baseUnit.getBaseID();
    }

    @Override
    public String getName() {
        return baseUnit.getName();
    }

    @Override
    public String getDescription() {
        return baseUnit.getDescription();
    }

    @Override
    public int getCardType() { return BaseCard.CardType.Creature; }

}
