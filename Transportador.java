package PROYECTO_1EV;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Representa un transportador que retira cajas llenas y las lleva al almacén.
 * Espera hasta que una caja esté completamente llena (5 envases) para transportarla.
 * Cada transportador funciona como un hilo independiente que coordina con los empaquetadores.
 *
 * @author Miracle Owen
 * @version 1.0
 */
public class Transportador implements Runnable {
    private final Caja caja;


    /**
     * Constructor que inicializa un transportador con la caja compartida.
     *
     * @param caja la caja que debe ser monitoreada y transportada cuando esté llena
     */
    public Transportador(Caja caja) {
        this.caja = caja;
    }

    /**
     * Método principal de ejecución del transportador.
     * Espera a que las cajas se llenen, las retira y simula el transporte al almacén
     * de forma continua hasta que el hilo es interrumpido.
     * Incluye un tiempo fijo para simular el proceso de transporte.
     */

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                caja.retirarCaja();

                // Tiempo transportación
                Thread.sleep(1000);
                System.out.println("[Transportador] Caja entregada en almacén.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("[Transportador] Finalizado.");
    }
}
