package events.game;

import communication.ActionEnum;
import events.ExternalEvent;
import events.game.interop.CreatureDamageTaken.CreatureDamageTakenInterop;
import events.interop.base.InteropEvent;
import units.PlayedUnit;


/**
 * Created by Lukas on 04.02.2016.
 */
public class CreatureDamageTaken extends ExternalEvent {

    private PlayedUnit target;


    private Object source;
    private int damage;
    public CreatureDamageTaken(PlayedUnit target, Object source, int damage){
        super(ActionEnum.CREATURE_DAMAGE_TAKEN);
        this.target = target;
        this.source = source;
        this.damage = damage;
    }

    public int getDamage(){ return damage; }

    @Override
    public InteropEvent makeIOEvent(int activePlayer, int playerPerspective) {
        return new CreatureDamageTakenInterop(target.getOwner().getPlayerID(), damage, 0);
    }

    public PlayedUnit getTarget(){ return target; }
    public Object getSource(){ return source; }
}
