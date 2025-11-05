package PROYECTO_1EV;

import java.util.concurrent.Semaphore;

/**
 * Representa una cinta transportadora que actúa como buffer compartido entre productores y empaquetadores.
 * Coordina el flujo de envases mediante semáforos para controlar la capacidad máxima y evitar condiciones de carrera.
 *
 * @author TuNombre
 * @version 1.0
 */

public class CintaTransportadora {
    private final int capacidad;
    private int envasesActuales;
    private final Semaphore semaforoProductores;
    private final Semaphore semaforoEmpaquetadores;
    private final Object lock = new Object();

    /**
     * Constructor que inicializa la cinta transportadora con una capacidad específica.
     *
     * @param capacidad el número máximo de envases que puede contener la cinta
     */

    public CintaTransportadora(int capacidad) {
        this.capacidad = capacidad;
        this.envasesActuales = 0;
        this.semaforoProductores = new Semaphore(capacidad);
        this.semaforoEmpaquetadores = new Semaphore(0);
    }

    /**
     * Permite a un productor añadir un envase a la cinta transportadora.
     * Si la cinta está llena, el productor espera hasta que haya espacio disponible.
     *
     * @param idProductor el identificador único del productor que añade el envase
     * @throws InterruptedException si el hilo es interrumpido durante la espera
     */
    public void añadirEnvase(int idProductor) throws InterruptedException {
        semaforoProductores.acquire();  // Esperar hasta que haya hueco de la capacidad

        synchronized (lock) {
            envasesActuales++;
            System.out.println("[Productor-" + idProductor + "] Ha fabricado un envase. Total en cinta: " + envasesActuales);

            //Avisa a empaquetadores que hay nuevo envase
            semaforoEmpaquetadores.release();
        }
    }

    /**
     * Permite a un empaquetador retirar un envase de la cinta transportadora.
     * Si la cinta está vacía, el empaquetador espera hasta que haya envases disponibles.
     *
     * @param idEmpaquetador el identificador único del empaquetador que retira el envase
     * @throws InterruptedException si el hilo es interrumpido durante la espera
     */

    public void retirarEnvase(int idEmpaquetador) throws InterruptedException {
        semaforoEmpaquetadores.acquire();

        synchronized (lock) {
            envasesActuales--;
            System.out.println("[Empaquetador-" + idEmpaquetador + "] Ha retirado un envase. Envases en cinta: " + envasesActuales);

                // Avisa a productores que hay nuevo espacio/hueco
            semaforoProductores.release();
        }
    }

    /**
     * Obtiene el número actual de envases en la cinta transportadora.
     *
     * @return el número de envases actualmente en la cinta
     */
    public int getEnvasesActuales() {
        synchronized (lock) {
            return envasesActuales;
        }
    }
}









