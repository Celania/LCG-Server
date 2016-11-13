package units;

import java.util.List;

import effects.base.PermEffect;
import effects.base.TempEffect;

public interface UnitInfo extends CardInfo{

    public int getBaseHealth();

    public int getBaseAttack();

    public int getBaseStatusMask();

    public List<PermEffect> getPermEffects();

    public List<TempEffect> getTempEffects();

}