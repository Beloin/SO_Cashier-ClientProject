package com.beloin.so_cashierclientproject.models;

import java.util.concurrent.Semaphore;

public interface Client {
    void setCashier(Cashier cashier);
    Semaphore getOwnSemaphore();
    int getAttendantSeconds();
}
