package events.interop.models;

import java.io.Serializable;

/**
 * Created by Lukas on 22.06.2016.
 */
public class InteropCard implements Serializable{

    private int playerID;
    private int position;

    public InteropCard(int playerID){
        this.playerID = playerID;
    }
}
