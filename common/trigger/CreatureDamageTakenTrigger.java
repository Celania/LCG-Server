package trigger;

import communication.ActionEnum;
import events.game.CreatureDamageTaken;
import events.Event;
import game.PlayerInformation;
import units.PlayedUnit;
import units.Spell;
import units.info.SourceRestriction;
import units.info.TargetRestriction;

/**
 * Created by Lukas on 23.02.2016.
 */
public class CreatureDamageTakenTrigger extends Trigger{

    int damage;
    Comparator cmp;

    SourceRestriction sourceRestriction;
    TargetRestriction targetRestriction;


    public enum Comparator{
        Ignore, Equal, Lesser, Greater
    }

    public CreatureDamageTakenTrigger(int damage, Comparator cmp, SourceRestriction sourceRestriction, TargetRestriction targetRestriction)
    {
        super(ActionEnum.CREATURE_DAMAGE_TAKEN);
        this.damage = damage;
        this.cmp = cmp;
        this.sourceRestriction = sourceRestriction;
        this.targetRestriction = targetRestriction;
    }

    @Override
    public boolean doesTrigger(Event event, PlayerInformation effectSourceOwner) {
        if (!(event instanceof CreatureDamageTaken)) {
            return false;
        }
        CreatureDamageTaken cdt =(CreatureDamageTaken)event;

        switch (cmp){
            case Equal: if (damage != cdt.getDamage()) return false;
            case Greater: if (damage >= cdt.getDamage()) return false;
            case Lesser: if (damage < cdt.getDamage()) return false;
            case Ignore: break;
        }

        Object source = cdt.getSource();

        if (source instanceof PlayedUnit) {
            if (!sourceRestriction.isValidUnit((PlayedUnit) source, effectSourceOwner)) return false;
        }else if (source instanceof PlayerInformation) {
            if (!sourceRestriction.isValidUnit((PlayerInformation) source, effectSourceOwner)) return false;
        }else if (source instanceof Spell) {
            if (!sourceRestriction.isValidUnit((Spell) source, effectSourceOwner)) return false;
        }

        if (!targetRestriction.isValidUnit(cdt.getTarget(), effectSourceOwner)) return false;


        return true;
    }
}
