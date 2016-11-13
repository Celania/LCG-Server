package units.info;

import game.PlayerInformation;
import units.Card;
import units.PlayedUnit;

public class SourceRestriction extends UnitRestriction {

    public SourceRestriction(int targetMask, int statusMask, int statusNotMask, int type) {
        super(targetMask, statusMask, statusNotMask, type);
    }

    public class Unit {
        public final static int SHIFT = 30;
        public final static int MASK  = 0b0000000000000000000000000000011 << SHIFT;

        public final static int CREATURE = 0b00000000000000000000000000000000 << SHIFT;
        public final static int UNIT     = 0b00000000000000000000000000000001 << SHIFT;
        public final static int SPELL    = 0b00000000000000000000000000000010 << SHIFT;
        public final static int PLAYER   = 0b00000000000000000000000000000011 << SHIFT;
    }

    public class Faction {
        public final static int SHIFT = 27;
        public final static int MASK  = 0b00000000000000000000000000000111 << SHIFT;

        public final static int SOURCE          = 0b00000000000000000000000000000000 << SHIFT;
        public final static int ENEMY           = 0b00000000000000000000000000000001 << SHIFT;
        public final static int ALLY            = 0b00000000000000000000000000000010 << SHIFT;
        public final static int ALLY_NON_SOURCE = 0b00000000000000000000000000000011 << SHIFT;
        public final static int ANY             = 0b00000000000000000000000000000100 << SHIFT;
    }

    public class Mana {
        private final static int SHIFT = 25;
        public final static int  MASK  = 0b0000000000000000000000000000011 << SHIFT;

        public final static int IGNORE  = 0b0000000000000000000000000000000 << SHIFT;
        public final static int EQUAL   = 0b0000000000000000000000000000001 << SHIFT;
        public final static int LESSER  = 0b0000000000000000000000000000010 << SHIFT;
        public final static int GREATER = 0b0000000000000000000000000000011 << SHIFT;
    }

    public class ManaParam {
        private final static int SHIFT = 22;
        public final static int  MASK  = 0b00000000000000000000000000000111 << SHIFT;
    }

    public class Attack {
        private final static int SHIFT = 20;
        public final static int  MASK  = 0b0000000000000000000000000000011 << SHIFT;

        public final static int IGNORE  = 0b0000000000000000000000000000000 << SHIFT;
        public final static int EQUAL   = 0b0000000000000000000000000000001 << SHIFT;
        public final static int LESSER  = 0b0000000000000000000000000000010 << SHIFT;
        public final static int GREATER = 0b0000000000000000000000000000011 << SHIFT;
    }

    public class AttackParam {
        private final static int SHIFT = 17;
        public final static int  MASK  = 0b00000000000000000000000000000111 << SHIFT;
    }

    public class Health {
        private final static int SHIFT = 15;
        public final static int  MASK  = 0b0000000000000000000000000000011 << SHIFT;

        public final static int IGNORE  = 0b0000000000000000000000000000000 << SHIFT;
        public final static int EQUAL   = 0b0000000000000000000000000000001 << SHIFT;
        public final static int LESSER  = 0b0000000000000000000000000000010 << SHIFT;
        public final static int GREATER = 0b0000000000000000000000000000011 << SHIFT;
    }

    public class HealthParam {
        private final static int SHIFT = 12;
        public final static int  MASK  = 0b00000000000000000000000000000111 << SHIFT;
    }

    public class MaxHealth {
        private final static int SHIFT = 10;
        public final static int  MASK  = 0b0000000000000000000000000000011 << SHIFT;

        public final static int IGNORE  = 0b0000000000000000000000000000000 << SHIFT;
        public final static int EQUAL   = 0b0000000000000000000000000000001 << SHIFT;
        public final static int LESSER  = 0b0000000000000000000000000000010 << SHIFT;
        public final static int GREATER = 0b0000000000000000000000000000011 << SHIFT;
    }

    public class MaxHealthParam {
        private final static int SHIFT = 7;
        public final static int  MASK  = 0b00000000000000000000000000000111 << SHIFT;
    }

    private static boolean isValidAttack(int compare, int attack, int param) {
        param = param >> AttackParam.SHIFT;
        switch (compare) {
            case Attack.IGNORE:
                return true;
            case Attack.GREATER:
                return attack >= param;
            case Attack.LESSER:
                return attack <= param;
            case Attack.EQUAL:
                return attack == param;
            default:
                return true; // should never happen - log
        }

    }

    private static boolean isValidMana(int compare, int mana, int param) {
        param = param >> ManaParam.SHIFT;
        switch (compare) {
            case Mana.IGNORE:
                return true;
            case Mana.GREATER:
                return mana >= param;
            case Mana.LESSER:
                return mana <= param;
            case Mana.EQUAL:
                return mana == param;
            default:
                return true; // should never happen - log
        }

    }

    private static boolean isValidHealth(int compare, int health, int param) {
        param = param >> HealthParam.SHIFT;
        switch (compare) {
            case Health.IGNORE:
                return true;
            case Health.GREATER:
                return health >= param;
            case Health.LESSER:
                return health <= param;
            case Health.EQUAL:
                return health == param;
            default:
                return true; // should never happen - log
        }

    }

    private static boolean isValidMaxHealth(int compare, int maxhealth, int param) {
        param = param >> MaxHealthParam.SHIFT;
        switch (compare) {
            case Health.IGNORE:
                return true;
            case Health.GREATER:
                return maxhealth >= param;
            case Health.LESSER:
                return maxhealth <= param;
            case Health.EQUAL:
                return maxhealth == param;
            default:
                return true; // should never happen - log
        }

    }

    public boolean isValidUnit(PlayedUnit unit, PlayerInformation caster) {

        if (type > 0 && unit.getCreatureType() != type) return false;
        if (statusMask > 0 && !unit.hasStatus(statusMask)) return false;
        if (statusNotMask > 0 && unit.hasOneOfStatus(statusNotMask)) return false;

        int Testing = targetMask & Unit.MASK;

        if (Testing == Unit.PLAYER) return false;
        if (Testing == Unit.SPELL) return false;

        Testing = targetMask & Faction.MASK;
        if (Testing == Faction.ALLY && unit.getOwner() != caster) return false;
        if (Testing == Faction.ENEMY && unit.getOwner() == caster) return false;

        Testing = targetMask & Attack.MASK;
        if (!isValidAttack(Testing, unit.getAttack(), targetMask & AttackParam.MASK)) return false;

        Testing = targetMask & Health.MASK;
        if (!isValidHealth(Testing, unit.getHealth(), targetMask & HealthParam.MASK)) return false;

        Testing = targetMask & MaxHealth.MASK;
        if (!isValidMaxHealth(Testing, unit.getMaxHealth(), targetMask & MaxHealthParam.MASK)) return false;

        Testing = targetMask & Mana.MASK;
        if (!isValidMana(Testing, unit.getManaCost(), targetMask & ManaParam.MASK)) return false;

        return true;
    }

    public boolean isValidUnit(PlayerInformation unit, PlayerInformation caster) {
        if (statusMask > 0 && !unit.getUnit().hasStatus(statusMask)) return false;
        if (statusNotMask > 0 && unit.getUnit().hasOneOfStatus(statusNotMask)) return false;

        int Testing = targetMask & Unit.MASK;

        if (Testing == Unit.CREATURE) return false;
        if (Testing == Unit.SPELL) return false;

        Testing = targetMask & Faction.MASK;
        if (Testing == Faction.ALLY && unit != caster) return false;
        if (Testing == Faction.ENEMY && unit == caster) return false;

        Testing = targetMask & Attack.MASK;
        if (!isValidAttack(Testing, unit.getUnit().getAttack(), targetMask & AttackParam.MASK)) return false;

        Testing = targetMask & Health.MASK;
        if (!isValidHealth(Testing, unit.getUnit().getHealth(), targetMask & HealthParam.MASK)) return false;

        Testing = targetMask & MaxHealth.MASK;
        if (!isValidMaxHealth(Testing, unit.getUnit().getMaxHealth(), targetMask & MaxHealthParam.MASK)) return false;

        return true;
    }

    public boolean isValidUnit(Card unit, PlayerInformation caster) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isValidSourceAlive(PlayerInformation caster, PlayerInformation enemy) {
        // TODO check whether a unit satisfying the restriction is alive
        return false;
    }

}
