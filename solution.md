# Solution

For this solution we will be getting expertise from the classic Barber Problem.

Semaphore clientQueueMutex = 1;
Semaphore clients = 0;
Semaphore cashiers = N;

Cashier Thread:
run() {
    Client currentClient;
    int atTime;
    Semaphore clientSpecifcSemaphore;
    while (true) {
        down(clients);
        
        down(clientQueueMutex);
        currentClient = queue.top();
        up(clientQueueMutex);
        
        currentClient.setCashier(this);
        atTime = currentClient.getATTime();
        clientSpecifcSemaphore = currentClient.getSemaphore();

        up(cashiers);
        down(clientSpecifcSemaphore);
        doWork(atTime);
    }


}

Client Thread:
run() {
    goToQueue();
    down(clientQueueMutex);
    queue.append(this);
    up(clientQueueMutex);

    up(clients);

    while (!this.hasCashier()) down(cashiers);
    
    goToCashier();

    up(this.clientSpecifcSemaphore);
    doWork();
    goToHome()
}

