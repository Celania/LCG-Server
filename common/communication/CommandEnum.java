package communication;

public enum CommandEnum {
	CARD_PLAY, // param1: cardPosition, (optional)param2: target
	ATTACK, // param1: attackPos, param2: attackedPos
	HERO_POWER, // (optional)param1: target
	CONCEDE, // no params
	ABORT,
	QUEUE, // param1: name, param2: heroChoice
	CLOSE, 
	END_TURN
}
