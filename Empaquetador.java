package PROYECTO_1EV;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Representa un empaquetador que retira envases de la cinta transportadora
 * y los coloca en cajas. Cada empaquetador funciona como un hilo independiente
 * que coordina con productores y transportadores.
 *
 * @author Miracle Owen
 * @version 1.0
 */

public class Empaquetador implements Runnable {
    private final CintaTransportadora cinta;
    private final Caja caja;
    private final int id;

    /**
     * Constructor que inicializa un empaquetador con sus recursos compartidos.
     *
     * @param cinta la cinta transportadora de donde retirar envases
     * @param caja la caja donde colocar los envases
     * @param id el identificador único del empaquetador
     */
    public Empaquetador(CintaTransportadora cinta, Caja caja, int id) {
        this.cinta = cinta;
        this.caja = caja;
        this.id = id;
    }


    /**
     * Método principal de ejecución del empaquetador.
     * Retira envases de la cinta transportadora y los añade a la caja
     * de forma continua hasta que el hilo es interrumpido.
     * Incluye tiempos aleatorios para simular el proceso de empaquetado.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // Simular tiempo de transportación
                Thread.sleep((long) (Math.random() * 300 + 150));

                cinta.retirarEnvase(id);
                caja.añadirEnvase(id);
            }
        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
        System.out.println("[Empaquetador-" + id + "] Finalizado.");
    }
}
