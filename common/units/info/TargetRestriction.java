package units.info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import game.PlayerInformation;
import units.Card;
import units.PlayedUnit;

public class TargetRestriction extends UnitRestriction {

    private static Random random = new Random(System.currentTimeMillis());

    public TargetRestriction(int targetMask, int statusMask, int statusNotMask, int type) {
        super(targetMask, statusMask, statusNotMask, type);
    }

    public class Location {
        public final static int SHIFT = 28;
        public final static int MASK  = 0b00000000000000000000000000001111 << SHIFT;

        public final static int BOARD     = 0b00000000000000000000000000000001 << SHIFT;
        public final static int HAND      = 0b00000000000000000000000000000010 << SHIFT;
        public final static int DECK      = 0b00000000000000000000000000000100 << SHIFT;
        public final static int GRAVEYARD = 0b00000000000000000000000000001000 << SHIFT;
    }

    public class Unit {
        public final static int SHIFT = 25;
        public final static int MASK  = 0b00000000000000000000000000000111 << SHIFT;

        public final static int CREATURE = 0b00000000000000000000000000000000 << SHIFT;
        public final static int UNIT     = 0b00000000000000000000000000000001 << SHIFT;
        public final static int PLAYER   = 0b00000000000000000000000000000010 << SHIFT;
        public final static int SPELL    = 0b00000000000000000000000000000011 << SHIFT;
        public final static int WEAPON   = 0b00000000000000000000000000000100 << SHIFT;
        public final static int SOURCE   = 0b00000000000000000000000000000101 << SHIFT;
        public final static int UNUSED1  = 0b00000000000000000000000000000110 << SHIFT;
        public final static int UNUSED2  = 0b00000000000000000000000000000111 << SHIFT;
    }

    public class Faction {
        public final static int SHIFT = 23;
        public final static int MASK  = 0b0000000000000000000000000000011 << SHIFT;

        public final static int ANY    = 0b0000000000000000000000000000000 << SHIFT;
        public final static int RANDOM = 0b0000000000000000000000000000001 << SHIFT;
        public final static int ALLY   = 0b0000000000000000000000000000010 << SHIFT;
        public final static int ENEMY  = 0b0000000000000000000000000000011 << SHIFT;

    }

    public class Pick {
        private final static int SHIFT = 21;
        public final static int  MASK  = 0b0000000000000000000000000000011 << SHIFT;

        public final static int ALL    = 0b0000000000000000000000000000000 << SHIFT;
        public final static int RANDOM = 0b0000000000000000000000000000001 << SHIFT;
    }

    public class Inclusion {
        private final static int SHIFT = 20;
        public final static int  MASK  = 0b00000000000000000000000000000001 << SHIFT;

        public final static int ANY       = 0b00000000000000000000000000000000 << SHIFT;
        public final static int NOTSOURCE = 0b00000000000000000000000000000001 << SHIFT;
    }

    public class Mana {
        private final static int SHIFT = 18;
        public final static int  MASK  = 0b0000000000000000000000000000011 << SHIFT;

        public final static int IGNORE  = 0b0000000000000000000000000000000 << SHIFT;
        public final static int EQUAL   = 0b0000000000000000000000000000001 << SHIFT;
        public final static int LESSER  = 0b0000000000000000000000000000010 << SHIFT;
        public final static int GREATER = 0b0000000000000000000000000000011 << SHIFT;
    }

    public class ManaParam {
        private final static int SHIFT = 15;
        public final static int  MASK  = 0b00000000000000000000000000000111 << SHIFT;
    }

    public class Attack {
        private final static int SHIFT = 13;
        public final static int  MASK  = 0b0000000000000000000000000000011 << SHIFT;

        public final static int IGNORE  = 0b0000000000000000000000000000000 << SHIFT;
        public final static int EQUAL   = 0b0000000000000000000000000000001 << SHIFT;
        public final static int LESSER  = 0b0000000000000000000000000000010 << SHIFT;
        public final static int GREATER = 0b0000000000000000000000000000011 << SHIFT;
    }

    public class AttackParam {
        private final static int SHIFT = 10;
        public final static int  MASK  = 0b00000000000000000000000000000111 << SHIFT;
    }

    public class Health {
        private final static int SHIFT = 8;
        public final static int  MASK  = 0b0000000000000000000000000000011 << SHIFT;

        public final static int IGNORE  = 0b0000000000000000000000000000000 << SHIFT;
        public final static int EQUAL   = 0b0000000000000000000000000000001 << SHIFT;
        public final static int LESSER  = 0b0000000000000000000000000000010 << SHIFT;
        public final static int GREATER = 0b0000000000000000000000000000011 << SHIFT;
    }

    public class HealthParam {
        private final static int SHIFT = 5;
        public final static int  MASK  = 0b00000000000000000000000000000111 << SHIFT;
    }

    public class MaxHealth {
        private final static int SHIFT = 3;
        public final static int  MASK  = 0b0000000000000000000000000000011 << SHIFT;

        public final static int IGNORE  = 0b0000000000000000000000000000000 << SHIFT;
        public final static int EQUAL   = 0b0000000000000000000000000000001 << SHIFT;
        public final static int LESSER  = 0b0000000000000000000000000000010 << SHIFT;
        public final static int GREATER = 0b0000000000000000000000000000011 << SHIFT;
    }

    public class MaxHealthParam {
        private final static int SHIFT = 0;
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

    public boolean isValidUnit(Card unit, PlayerInformation caster) {
        return true;
    }

    public boolean isValidUnit(PlayerInformation unit, PlayerInformation caster) {
        int Testing = targetMask & Unit.MASK;
        if (Testing == Unit.SPELL) return false;
        if (Testing == Unit.CREATURE) return false;
        if (Testing == Unit.SOURCE) return false;

        Testing = targetMask & Faction.MASK;
        if (Testing == Faction.ALLY && unit != caster) return false;
        if (Testing == Faction.ENEMY && unit == caster) return false;

        Testing = targetMask & Attack.MASK;
        if (!isValidAttack(Testing, unit.getUnit().getAttack(), targetMask & AttackParam.MASK)) return false;

        Testing = targetMask & Health.MASK;
        if (!isValidHealth(Testing, unit.getUnit().getHealth(), targetMask & HealthParam.MASK)) return false;

        // DEBUG only
        Testing = targetMask & Mana.MASK;
        if (Testing != Mana.IGNORE) System.out.println("Mana Mask may not be set for Player targets");

        // DEBUG only
        Testing = targetMask & MaxHealth.MASK;
        if (Testing != MaxHealth.IGNORE) System.out.println("MaxHealth Mask may not be set for Player targets");

        if (!unit.getUnit().hasStatus(statusMask)) return false;
        if (!unit.getUnit().isType(type)) return false;
        if (unit.getUnit().hasOneOfStatus(statusNotMask)) return false;

        return true;
    }

    public boolean isValidUnit(PlayedUnit unit, PlayerInformation caster) {
        int Testing = targetMask & Location.BOARD;
        if (Testing == 0) return false;

        Testing = targetMask & Unit.MASK;
        if (Testing == Unit.SPELL) return false;
        if (Testing == Unit.PLAYER) return false;
        if (Testing == Unit.WEAPON) return false;

        Testing = targetMask & Faction.MASK;
        if (Testing == Faction.ALLY && unit.getOwner() != caster) return false;
        if (Testing == Faction.ENEMY && unit.getOwner() == caster) return false;

        // Inclusion, Pick and PickParam tests are not needed to verify a given target;

        if (!unit.hasStatus(statusMask)) return false;
        if (!unit.isType(type)) return false;
        if (unit.hasOneOfStatus(statusNotMask)) return false;

        if (!isValidMana(targetMask & Mana.MASK, unit.getManaCost(), targetMask & ManaParam.MASK)) return false;
        if (!isValidAttack(targetMask & Attack.MASK, unit.getAttack(), targetMask & AttackParam.MASK)) return false;
        if (!isValidHealth(targetMask & Health.MASK, unit.getHealth(), targetMask & HealthParam.MASK)) return false;
        if (!isValidMaxHealth(targetMask & MaxHealth.MASK, unit.getMaxHealth(), targetMask & HealthParam.MASK))
            return false;

        return true;
    }

    public List<PlayedUnit> getUnitTargets(PlayerInformation caster, PlayerInformation enemy) {
        List<PlayedUnit> result = new ArrayList<>();
        List<PlayedUnit> tmp = new ArrayList<>();

        // TODO Unit.Source
        // TODO Inclusion
        switch (targetMask & Location.MASK) {
            case Location.BOARD:
                switch (targetMask & Unit.MASK) {
                    case Unit.CREATURE:
                        switch (targetMask & Faction.MASK) {
                            case Faction.ALLY:
                                tmp.addAll(caster.getBoard());
                                break;
                            case Faction.ENEMY:
                                tmp.addAll(enemy.getBoard());
                                break;
                            case Faction.ANY:
                                tmp.addAll(caster.getBoard());
                                tmp.addAll(enemy.getBoard());
                                break;
                            case Faction.RANDOM:
                                int faction = random.nextInt(2);
                                if (faction == 0)
                                    tmp.addAll(caster.getBoard());
                                else tmp.addAll(enemy.getBoard());
                                break;
                        }
                        break;
                    case Unit.UNIT:
                        switch (targetMask & Faction.MASK) {
                            case Faction.ALLY:
                                tmp.addAll(caster.getBoard());
                                tmp.add(caster.getUnit());
                                break;
                            case Faction.ENEMY:
                                tmp.addAll(enemy.getBoard());
                                tmp.add(enemy.getUnit());
                                break;
                            case Faction.ANY:
                                tmp.addAll(caster.getBoard());
                                tmp.add(caster.getUnit());
                                tmp.addAll(enemy.getBoard());
                                tmp.add(enemy.getUnit());
                                break;
                            case Faction.RANDOM:
                                int faction = random.nextInt(2);
                                if (faction == 0) {
                                    tmp.addAll(caster.getBoard());
                                    tmp.add(caster.getUnit());
                                } else {
                                    tmp.addAll(enemy.getBoard());
                                    tmp.add(enemy.getUnit());
                                }
                                break;
                        }
                        break;
                    default:
                        System.out.println("Not implemented yet!");
                        break;
                }
        }

        Iterator<PlayedUnit> it = tmp.iterator();
        while (it.hasNext()) {
            PlayedUnit unit = it.next();
            if (!isValidMana(targetMask & Mana.MASK, unit.getManaCost(), targetMask & ManaParam.MASK)) it.remove();
            if (!isValidAttack(targetMask & Attack.MASK, unit.getAttack(), targetMask & AttackParam.MASK)) it.remove();
            if (!isValidHealth(targetMask & Health.MASK, unit.getHealth(), targetMask & HealthParam.MASK)) it.remove();
            if (!isValidMaxHealth(targetMask & MaxHealth.MASK, unit.getMaxHealth(), targetMask & MaxHealthParam.MASK))
                it.remove();
            // how to handle inclusion? spells should always not target themselves (e.g counterspell)
        }

        int pickAmount = (targetMask & Pick.MASK) >> Pick.SHIFT;
        if (pickAmount > Pick.ALL && pickAmount < tmp.size()) {
            Collections.shuffle(tmp);
            // TODO is the target requirement implicit here? are we certain we have enough valid picks?
            for (int i = 0; i < pickAmount; i++)
                result.add(tmp.get(i));
        } else result = tmp;

        return result;

    }

    public List<PlayerInformation> getPlayerTargets(PlayerInformation caster, PlayerInformation enemy) {
        List<PlayerInformation> result = new ArrayList<PlayerInformation>();

        return result;
    }

    public List<Card> getCardTargets(PlayerInformation caster, PlayerInformation enemy) {
        List<Card> result = new ArrayList<Card>();

        return result;
    }

    public List<EffectTargetable> getTargets(PlayerInformation caster, PlayerInformation enemy){
        List<EffectTargetable> result = new ArrayList<>();

        return result;
    }

}
