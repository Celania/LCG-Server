package system;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import communication.Message;
import communication.ServerCommand;

import connection.Client;
import connection.ConnectionStatus;
import events.SystemEvent;
import events.interop.base.InteropEvent;

abstract class CommandAcceptor extends Thread {

    // protected List<Client> clients = new LinkedList<Client>();

    protected Map<UUID, Client>         clients         = new HashMap<>();

    private BlockingQueue<Client>       clientsToAdd    = new LinkedBlockingQueue<>();
    private BlockingQueue<Client>       clientsToRemove = new LinkedBlockingQueue<>();

    public BlockingQueue<ServerCommand> inputQueue      = new LinkedBlockingQueue<>();

    // set in the extending classes constructor
    protected ConnectionStatus          status;
    protected ConnectionStatus          removeStatus;

    public CommandAcceptor() {
    }

    public void addClient(Client client) {
        try {
            clientsToAdd.put(client);
            client.newStatus(inputQueue, null, status);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Message message = new Message();
        List<SystemEvent> events = onAddClient();
        if (!events.isEmpty()) {
            List<InteropEvent> ioEvents = events.stream().map( e -> e.makeIOEvent()).collect(Collectors.toList());
            client.singleMessage(new Message(ioEvents));
        }
    }

    public void run() {
        while (true) {
            while (!clientsToAdd.isEmpty())
                clients.put(clientsToAdd.peek().getID(), clientsToAdd.poll());

            while (!clientsToRemove.isEmpty())
                clients.remove(clientsToRemove.poll().getID());

            if (!inputQueue.isEmpty()) try {
                // TODO Log input
                commandDispatch(inputQueue.take());
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            onRun();

            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeClient(Client client) {
        // client.newStatus(null, null, removeStatus); input/output points to system???
        Message message = new Message();
        List<SystemEvent> events = onRemoveClient(client.getID());
        try {
            clientsToRemove.put(client);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!events.isEmpty()) {
            List<InteropEvent> ioEvents = events.stream().map( e -> e.makeIOEvent()).collect(Collectors.toList());
            message.addInteropEvent(ioEvents);
            client.singleMessage(message);
        }
    }

    // extending class is supposed to overwrite this
    protected abstract void commandDispatch(ServerCommand command);

    protected List<SystemEvent> onAddClient() {
        List<SystemEvent> actions = new LinkedList<>();

        return actions;
    }

    protected List<SystemEvent> onRemoveClient(UUID clientID) {
        List<SystemEvent> actions = new LinkedList<>();

        return actions;
    }

    protected void onRun() {

    }
}
