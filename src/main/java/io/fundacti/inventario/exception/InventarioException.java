package io.fundacti.inventario.exception;


public class InventarioException extends RuntimeException {

    public InventarioException(String e) {
        super("Error:" + e);
    }

}