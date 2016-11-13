package units;

import java.util.List;

import effects.base.SpellEffectGroup;

public interface SpellInfo {

    public List<SpellEffectGroup> getEffects();

    public boolean isTargeted();

}
