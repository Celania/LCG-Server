package events;

/**
 * Created by Lukas on 10.02.2016.
 */
public class Status {
    public final int Freeze = 0b00000000000000000000000000000001 << 0;
    public final int Taunt  = 0b00000000000000000000000000000001 << 1;
    public final int Windfury = 0b00000000000000000000000000000001 << 2;
    public final int Divine_Shield = 0b00000000000000000000000000000001 << 2;
    public final int Immune =0b00000000000000000000000000000001 << 3;
    public final int Poison = 0b00000000000000000000000000000001 << 4;
    public final int Cannot_Attack = 0b00000000000000000000000000000001 << 5;
    public final int Stealth = 0b00000000000000000000000000000001 << 6;
    public final int Spell_Power = 0b00000000000000000000000000000001 << 7;
    public final int Charge = 0b00000000000000000000000000000001 << 8;
    public final int Deathrattle = 0b00000000000000000000000000000001 << 9;
    public final int Enraged = 0b00000000000000000000000000000001 << 10;
    public final int Damaged = 0b00000000000000000000000000000001 << 11;

}
