package units;

import java.util.List;

import effects.base.PermEffect;
import effects.base.TempEffect;

public class BaseUnit extends BaseCard implements UnitInfo {

    private final int baseHealth;
    private final int baseAttack;
    private final int creatureType;

    private final Spell            battlecry;
    private final Spell            deathrattle;
    private final List<PermEffect> permEffects;
    private final List<TempEffect> tempEffects;
    private final int              baseStatusMask;

    // TODO implement cardType (creature/unit)
    public BaseUnit(int cardID, String name, int cardType, int baseHealth, int baseAttack, int baseManaCost,
            int statusMask, String description, int creatureType, List<PermEffect> permEffects,
            List<TempEffect> tempEffects) {
        super(cardID, name, baseManaCost, description, cardType);
        this.baseHealth = baseHealth;
        this.baseAttack = baseAttack;
        this.baseStatusMask = statusMask;
        this.permEffects = permEffects;
        this.tempEffects = tempEffects;
        this.battlecry = null;
        this.deathrattle = null;
        this.creatureType = creatureType;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getBaseStatusMask() {
        return baseStatusMask;
    }

    public List<PermEffect> getPermEffects() {
        return permEffects;
    }

    public List<TempEffect> getTempEffects() {
        return tempEffects;
    }

    public Spell getBattlecry() {
        return battlecry;
    }

    public Spell getDeathrattle() {
        return deathrattle;
    }

    public int getCreatureType() {
        return creatureType;
    }
}
