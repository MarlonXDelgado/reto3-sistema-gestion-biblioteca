package com.dev.mxd.model;

// Enum que representa los posibles estados de un préstamo
public enum LoanState {
    // El préstamo está activo, el libro sigue en manos del usuario
    STARTED,
    // El préstamo ya finalizó, el libro fue devuelto
    FINISHED;
}
