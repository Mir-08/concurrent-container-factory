package PROYECTO_1EV;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;


/**
 * Representa una caja que puede contener hasta 5 envases.
 * Coordina la adición de envases por parte de los empaquetadores y la retirada por parte del transportador.
 * Utiliza wait/notify para la sincronización entre hilos.
 *
 * @author TuNombre
 * @version 1.0
 */
public class Caja {
    private int envasesEnCaja;
    private boolean cajaLlena;
    private final Object lock = new Object();

    /**
     * Constructor que inicializa una caja vacía.
     */
    public Caja() {
        this.envasesEnCaja = 0;
        this.cajaLlena = false;
    }


    /**
     * Permite a un empaquetador añadir un envase a la caja.
     * Si la caja está llena, el empaquetador espera hasta que el transportador la vacíe.
     *
     * @param idEmpaquetador el identificador único del empaquetador que añade el envase
     * @throws InterruptedException si el hilo es interrumpido durante la espera
     */
    public void añadirEnvase(int idEmpaquetador) throws InterruptedException {
        synchronized (lock) {
            while (cajaLlena) {
                lock.wait();
            }

            envasesEnCaja++;
            System.out.println("[Empaquetador-" + idEmpaquetador + "] Envase añadido a caja. Envases en caja: " + envasesEnCaja);

            if (envasesEnCaja == 5) {
                cajaLlena = true;
                lock.notifyAll(); //"La caja esta llena"
            }
        }
    }

    /**
     * Permite al transportador retirar una caja llena para enviarla al almacén.
     * Si la caja no está llena, el transportador espera hasta que tenga 5 envases.
     *
     * @throws InterruptedException si el hilo es interrumpido durante la espera
     */
    public void retirarCaja() throws InterruptedException {
        synchronized (lock) {

            while (!cajaLlena) {
                lock.wait();
            }

            System.out.println("[Transportador] Caja llena. Enviando al almacén...");
            envasesEnCaja = 0;
            cajaLlena = false;
            lock.notifyAll();
        }
    }

    /**
     * Obtiene el número actual de envases en la caja.
     *
     * @return el número de envases actualmente en la caja
     */

    public int getEnvasesEnCaja() {
        synchronized (lock) {
            return envasesEnCaja;
        }
    }
}
