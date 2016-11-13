package units.info;

import game.PlayerInformation;
import units.Card;
import units.PlayedUnit;

public abstract class UnitRestriction {

    public final int targetMask;
    public final int statusMask;
    public final int statusNotMask;
    public final int type;

    public UnitRestriction(int targetMask, int statusMask, int statusNotMask, int type) {
        this.targetMask = targetMask;
        this.statusMask = statusMask;
        this.statusNotMask = statusNotMask;
        this.type = type;
    }

    public abstract boolean isValidUnit(PlayedUnit unit, PlayerInformation caster);

    public abstract boolean isValidUnit(PlayerInformation unit, PlayerInformation caster);

    public abstract boolean isValidUnit(Card unit, PlayerInformation caster);
}
