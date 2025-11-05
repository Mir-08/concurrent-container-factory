package PROYECTO_1EV;
import java.util.concurrent.TimeUnit;

// Clase main para la simulación
public class MainFabrica {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO SIMULACIÓN DE FÁBRICA DE ENVASES ===");

        // Crear recurso compartido
        CintaTransportadora cinta = new CintaTransportadora(10);
        Caja caja = new Caja();

        // Crear e iniciar hilos productores
        Thread[] productores = new Thread[3];
        for (int i = 0; i < productores.length; i++) {
            productores[i] = new Thread(new Productor(cinta, i + 1));
            productores[i].start();
        }

        // Crear e iniciar hilos empaquetadores
        Thread[] empaquetadores = new Thread[2];
        for (int i = 0; i < empaquetadores.length; i++) {
            empaquetadores[i] = new Thread(new Empaquetador(cinta, caja, i + 1));
            empaquetadores[i].start();
        }

        // Crear e iniciar hilos transportadores
        Thread transportador = new Thread(new Transportador(caja));
        transportador.start();

        // Duración de 10 segundos
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== FINALIZANDO SIMULACIÓN ===");

        // Interrumpir los hilos
        for (Thread productor : productores) {
            productor.interrupt();
        }
        for (Thread empaquetador : empaquetadores) {
            empaquetador.interrupt();
        }
        transportador.interrupt();

        // Esperar que los hilos finalicen
        try {
            for (Thread productor : productores) {
                productor.join();
            }
            for (Thread empaquetador : empaquetadores) {
                empaquetador.join();
            }
            transportador.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("=== SIMULACIÓN FINALIZADA ===");
    }
}











