package connection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import communication.Message;

public class ClientThreadOutput extends Thread {

    private ObjectOutputStream                   output;
    public BlockingQueue<BlockingQueue<Message>> pendingOutputQueue; // pendingOutputQueue?
    private BlockingQueue<Message>               stdout;

    public ClientThreadOutput(ObjectOutputStream output) {
        this.output = output;
        this.pendingOutputQueue = new LinkedBlockingQueue<BlockingQueue<Message>>();
        this.stdout = new LinkedBlockingQueue<Message>();
        this.setName("ClientOutput");
    }

    public void registerQueue(BlockingQueue<Message> outputQueue) throws InterruptedException {
        if (outputQueue != null) this.pendingOutputQueue.put(outputQueue);
    }

    public void singleMessage(Message message) throws InterruptedException {
        stdout.put(message);
    }

    public void run() {
        while (true) {
            try {
                if (pendingOutputQueue.isEmpty() && !stdout.isEmpty()) {
                    output.writeObject(stdout.take());
                    output.reset();
                } else if (!pendingOutputQueue.isEmpty()) {
                    if (pendingOutputQueue.peek().isEmpty() && pendingOutputQueue.size() > 1)
                        pendingOutputQueue.remove();
                    else if (!pendingOutputQueue.peek().isEmpty()) {
                        output.writeObject(pendingOutputQueue.peek().take());
                        output.reset();
                    }
                }
                sleep(200);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }
}
