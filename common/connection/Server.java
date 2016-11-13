package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import game.Game;
import system.MatchMaking;
import system.StdHandler;

public class Server {

    // The server socket.
    private static ServerSocket serverSocket = null;
    // The client socket.
    private static Socket       clientSocket = null;

    private static final int      maxClientsCount = 10;
    private static final Client[] clients         = new Client[maxClientsCount];

    // Game threads
    private static ArrayList<Game> games = new ArrayList<Game>();

    private static MatchMaking matchMaking = MatchMaking.getInstance();
    private static StdHandler  stdHandler  = StdHandler.getInstance();

    public static void addGame(Game game) {
        games.add(game);
        game.start();
    }

    public static void main(String args[]) {
        // The default port number.
        int portNumber = 2222;
        if (args.length < 1) {
            System.out.println(
                    "Usage: java MultiThreadChatServer <portNumber>\n" + "Now using port number=" + portNumber);
        } else {
            portNumber = Integer.valueOf(args[0]).intValue();
        }

        /*
         * Open a server socket on the portNumber (default 2222). Note that we can not choose a port less than 1023 if
         * we are not privileged users (root).
         */

        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }

        // DBStore.loadDB();

        matchMaking.start();
        stdHandler.start();

        /*
         * Create a client socket for each connection and pass it to a new client thread.
         */
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                int i = 0;
                for (i = 0; i < maxClientsCount; i++) {
                    if (clients[i] == null) {
                        clients[i] = new Client(clientSocket);
                        stdHandler.addClient(clients[i]);
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}