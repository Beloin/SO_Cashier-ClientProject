package com.beloin.so_cashierclientproject.models;

import java.util.concurrent.Semaphore;

public interface Client extends PositionedModel {
    void setCashier(Cashier cashier);
    Semaphore getOwnSemaphore();
    int getAttendantSeconds();

    Integer getClientId();
}
