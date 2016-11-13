package game;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import communication.Action;
import communication.ActionEnum;
import communication.Command;
import communication.Message;
import communication.ServerCommand;
import communication.command.CommandEndTurn;
import communication.command.CommandPlay;
import connection.Client;
import connection.ConnectionStatus;
import effects.base.PermEffect;
import effects.base.TempEffect;
import events.*;
import events.Error;
import events.game.CardDrawn;
import events.game.TurnEnded;
import events.game.TurnStarted;
import manager.EffectManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stores.DBStore;
import units.*;

public class Game extends Thread{

    private Logger logger = LogManager.getLogger(Game.class.getName());
    private static long roundTime = 90000;

    private int gameID;

    private EffectManager            effectManager;
    private Map<Integer, PlayedUnit> units;

    private PlayerInformation player1, player2;
    private int               round;

    private long    elapsedRoundTime;
    private long    roundStartTime;
    private boolean roundTechnicallyOver;

    private int               playedUnitID;
    private PlayerInformation activePlayer, passivePlayer;

    public BlockingQueue<ServerCommand> inputQueue = new LinkedBlockingQueue<>();
    private EventResult eventResults = new EventResult();

    public Game(Client player1, Client player2) throws InterruptedException {
        gameID = 0;
        playedUnitID = 1;
        this.setName("Game " + gameID);

        this.effectManager = new EffectManager();
        this.units = new HashMap<>();

        this.player1 = new PlayerInformation(player1, effectManager, 1, playedUnitID++, player1.getHeroChoice());
        units.put(1, this.player1.getUnit());
        this.player2 = new PlayerInformation(player2, effectManager, 2, playedUnitID++, player2.getHeroChoice());
        units.put(2, this.player2.getUnit());

        effectManager.init(eventResults, this.player1, this.player2);

        player1.newStatus(inputQueue, this.player1.outputQueue, ConnectionStatus.IN_GAME);
        player2.newStatus(inputQueue, this.player2.outputQueue, ConnectionStatus.IN_GAME);

        round = 0;
        elapsedRoundTime = 0;

        activePlayer = this.player1;
        passivePlayer = this.player2;

        BaseUnit testBaseUnit = new BaseUnit(0, "TestUnit", BaseCard.CardType.Creature,
                                        2, 3, 2, 0, "test Mob", 0,
                                        new ArrayList<PermEffect>(), new ArrayList<TempEffect>());
        Unit testUnit = new Unit(this.player1, testBaseUnit);

        BaseSpell testBaseSpell = new BaseSpell(1, "TestSpell", BaseCard.CardType.Spell,
                                        "Test Spell", true, null, null, new ArrayList<>());
        Spell testSpell = new Spell(this.player1, testBaseSpell);

        List<ExternalEvent> eventsPlayer1 = new ArrayList<>();
        eventsPlayer1.add(new CardDrawn(testUnit, this.player1));
        eventsPlayer1.add(new CardDrawn(testSpell, this.player1));

        List<ExternalEvent> eventsPlayer2 = new ArrayList<>();
        eventsPlayer2.add(new CardDrawn(testSpell, this.player2));
        eventsPlayer2.add(new CardDrawn(testUnit, this.player2));

        Message message1 = new Message();
        Message message2 = new Message();

        for (ExternalEvent event : eventsPlayer1){
            message1.addInteropEvent(event.makeIOEvent(activePlayer.getPlayerID(), activePlayer.getPlayerID()));
            message2.addInteropEvent(event.makeIOEvent(activePlayer.getPlayerID(), passivePlayer.getPlayerID()));
        }

        for (ExternalEvent event : eventsPlayer2){
            message2.addInteropEvent(event.makeIOEvent(passivePlayer.getPlayerID(), passivePlayer.getPlayerID()));
            message1.addInteropEvent(event.makeIOEvent(passivePlayer.getPlayerID(), activePlayer.getPlayerID()));
        }

        this.player1.outputQueue.put(message1);
        this.player2.outputQueue.put(message2);

        System.out.println("Game init");
    }

    public void run() {
        System.out.println("Game running");
        roundStartTime = System.currentTimeMillis();
        while (true) {
            Message messageActive = new Message();
            Message messagePassive = new Message();
            PlayerInformation activePlayer;
            PlayerInformation passivePlayer;
            try {
                if (!roundTechnicallyOver && elapsedRoundTime > 5 * 1000) {
                    roundTechnicallyOver = true;
                    inputQueue.add(new ServerCommand(new CommandEndTurn(this.activePlayer.getPlayerID()), this.activePlayer.getID()));
                }

                if (!inputQueue.isEmpty()) {
                    if (inputQueue.peek().getClientID() == player1.getID()) {
                        activePlayer = player1;
                        passivePlayer = player2;
                    } else {
                        activePlayer = player2;
                        passivePlayer = player1;
                    }

                    commandDispatch(inputQueue.take().getCommand(), activePlayer);

                    for (ExternalEvent event : eventResults.getEvents()){
                        messageActive.addInteropEvent(event.makeIOEvent(activePlayer.getPlayerID(), activePlayer.getPlayerID()));
                        messagePassive.addInteropEvent(event.makeIOEvent(activePlayer.getPlayerID(), passivePlayer.getPlayerID()));
                    }

                    activePlayer.outputQueue.put(messageActive);
                    passivePlayer.outputQueue.put(messagePassive);
                }
                elapsedRoundTime = System.currentTimeMillis() - roundStartTime;
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void removePlayedUnit(int id) {

    }

    public int getNewPlayedUnitID() {
        return ++playedUnitID;
    }

    public EffectManager getEffectManager() {
        return this.effectManager;
    }

    private void commandDispatch(Command command, PlayerInformation player) throws InterruptedException {
        if (command instanceof CommandPlay){
            CommandPlay cardPlay = (CommandPlay) command;
            CardPlay(cardPlay.handPosition, cardPlay.boardPosition, cardPlay.boardPlayerID);
        }else if (command instanceof CommandEndTurn){
            CommandEndTurn commandEndTurn = (CommandEndTurn) command;
            EndTurn();
        }
    }

    private void EndTurn() {

        effectManager.fireEvent(new TurnEnded(activePlayer));
        // actions.addAll(effectManager.fireEvent(ActionEnum.TURN_END, activePlayer, Util.unused,
        // IsTrigger.untriggered));
        // Event notify END_TURN, activePlayer

        PlayerInformation temp = activePlayer;
        activePlayer = passivePlayer;
        passivePlayer = temp;

        round++;

        effectManager.fireEvent(new TurnStarted(activePlayer));

        activePlayer.alterMaxMana(1);
        activePlayer.resetMana();

        BaseUnit testBaseUnit = new BaseUnit(0, "TestUnit", BaseCard.CardType.Creature,
                2, 3, 2, 0, "test Mob", 0,
                new ArrayList<PermEffect>(), new ArrayList<TempEffect>());
        Unit testUnit = new Unit(this.player1, testBaseUnit);

        effectManager.fireEvent(new CardDrawn(testUnit, activePlayer));

        elapsedRoundTime = 0;
        roundStartTime = System.currentTimeMillis();
        roundTechnicallyOver = false;

    }

    private void CardPlay(int handPos, int boardPos, int boardPlayerID) throws InterruptedException {

        Card card = activePlayer.getHand().get(handPos);

        if (card.getManaCost() < activePlayer.getMana())
            effectManager.fireEvent(new Error(ErrorType.NotEnoughMama));
        else {
            switch (card.getCardType()) {

                case BaseCard.CardType.Creature:
                    // TODO Is it necessary to delay adding the creature to the board?
                    // how to handle battleCry
                    PlayedUnit unit = new PlayedUnit(effectManager, activePlayer, playedUnitID++,
                            DBStore.getUnit(card.getBaseID()));
                    activePlayer.getBoard().add(boardPos, unit);
                    units.put(unit.getID(), unit);
                    //TODO fire CreaturePlayed
                    //effectManager.fireEvent();
                    break;
                case BaseCard.CardType.Spell:
                    Spell spell = (Spell) card;

                    // TODO passive player always enemy?
                    PlayerInformation enemy;

                    if (spell.getOwner() == player1)
                        enemy = player2;
                    else enemy = player1;

                    switch (playedUnitID) {
                        case 0:
                            // undirected apply
                            spell.cast(effectManager, null, enemy);
                            break;
                        case 1:
                            spell.cast(effectManager, player1, enemy);
                            break;
                        case 2:
                            // results.addAll(spell.cast(effectManager, player1, enemy));
                            spell.cast(effectManager, player2, enemy);
                            break;
                        default:
                            spell.cast(effectManager, units.get(playedUnitID), enemy);
                            // TODO if the unit does not exist anymore, we need to throw an error
                            // results.addAll(spellManager.castSpell(spell, units.get(playedUnitID)));
                            break;
                    }
                    break;
            }
            // TODO handle deaths
        }
    }

    // Attacks can only ever come from the activePlayer, so we don't need ownership
    private List<Action> Attack(int attackerPos, int attackedPos) throws InterruptedException {
        List<Action> results = new LinkedList<>();

        results.add(new Action(ActionEnum.CREATURE_DAMAGE_TAKEN, 0, 3));

        return results;
    }
}
