package mongo;

import com.mongodb.event.*;

public class CustomConnectionPoolListener implements ConnectionPoolListener {
    private boolean print = false;
    @Override
    public void connectionPoolOpened(ConnectionPoolOpenedEvent connectionPoolOpenedEvent) {
        if (print)
            System.out.println("connectionPoolOpened");
    }

    @Override
    public void connectionPoolClosed(ConnectionPoolClosedEvent connectionPoolClosedEvent) {
        if (print)
        System.out.println("connectionPoolClosed");
    }

    @Override
    public void connectionCheckedOut(ConnectionCheckedOutEvent connectionCheckedOutEvent) {
        if (print)
        System.out.println("connectionCheckedOut");
    }

    @Override
    public void connectionCheckedIn(ConnectionCheckedInEvent connectionCheckedInEvent) {
        if (print)
        System.out.println("connectionCheckedIn");
    }

    @Override
    public void waitQueueEntered(ConnectionPoolWaitQueueEnteredEvent connectionPoolWaitQueueEnteredEvent) {
        if (print)
        System.out.println("waitQueueEntered");
    }

    @Override
    public void waitQueueExited(ConnectionPoolWaitQueueExitedEvent connectionPoolWaitQueueExitedEvent) {
        if (print)
        System.out.println("waitQueueExited");
    }

    @Override
    public void connectionAdded(ConnectionAddedEvent connectionAddedEvent) {
        if (print)
        System.out.println("connectionAdded");
    }

    @Override
    public void connectionRemoved(ConnectionRemovedEvent connectionRemovedEvent) {
        if (print)
        System.out.println("connectionRemoved --");
    }
}
