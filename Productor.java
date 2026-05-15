package PROYECTO_1EV;

/**
 * Representa un productor que fabrica envases y los coloca en la cinta transportadora.
 * Cada productor funciona como un hilo independiente que trabaja de forma concurrente
 * con otros productores y coordina con los empaquetadores.
 *
 * @author Miracle Owen
 * @version 1.0
 */
public class Productor implements Runnable {
    private final CintaTransportadora cinta;
    private final int id;

    /**
     * Constructor que inicializa un productor con la cinta transportadora compartida.
     *
     * @param cinta la cinta transportadora donde depositar los envases
     * @param id el identificador único del productor
     */

    public Productor(CintaTransportadora cinta, int id) {
        this.cinta = cinta;
        this.id = id;
    }

    /**
     * Método principal de ejecución del productor.
     * Fabrica envases continuamente y los coloca en la cinta transportadora
     * hasta que el hilo es interrumpido. Incluye tiempos aleatorios para
     * simular el proceso de fabricación.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {

                Thread.sleep((long) (Math.random() * 500 + 100));

                cinta.añadirEnvase(id);
            }
        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
        System.out.println("[Productor-" + id + "] Finalizado.");
    }
}
