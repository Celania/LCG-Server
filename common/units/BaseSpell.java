package units;

import java.util.List;

import effects.base.SpellEffectGroup;
import game.PlayerInformation;
import units.info.SourceRestriction;
import units.info.TargetRestriction;

public class BaseSpell extends BaseCard {

    public final List<SpellEffectGroup> effects;
    public final boolean                isTargeted;
    // TODO is there a reason UnitRestriction was used in favour of TargetRestriction?
    // public final UnitRestriction targetRestriction;

    // if targetRestriction is set it is required for the spell to be able to find a target to apply the spell to
    public final TargetRestriction targetRestriction;
    // IDEA give spells SourceRestrictions - only be able to cast Spell if e.g. a certain creature type is in hand
    public final SourceRestriction sourceRestriction = null;

    public BaseSpell(int baseID, String name, int baseManaCost, String description, boolean isTargeted,
            TargetRestriction requiredRestriction, TargetRestriction targetRestriction,
            List<SpellEffectGroup> effects) {
        super(baseID, name, baseManaCost, description, BaseCard.CardType.Spell);
        this.effects = effects;
        this.isTargeted = isTargeted;
        this.targetRestriction = targetRestriction;
    }

    public boolean isValidTarget(PlayedUnit target, PlayerInformation caster) {
        return targetRestriction.isValidUnit(target, caster);
    }

    public boolean isValidTarget(PlayerInformation target, PlayerInformation caster) {
        return targetRestriction.isValidUnit(target, caster);
    }

    public boolean hasValidTarget(PlayerInformation caster, PlayerInformation enemy){
        return false;
    }
}
