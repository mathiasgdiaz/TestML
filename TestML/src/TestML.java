import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author MathiasGDiaz
 */

public class TestML extends Thread {
   
    /* CONSTANTES DE LA CLASE*/    
        private static final int T = 1000000; 
        /* T = VALOR LIMITE A EVALUAR. */

        private static final int N = Runtime.getRuntime().availableProcessors(); 
        /* N = CANTIDAD DE THREADS A LANZAR. VALOR OBTENIDO SEGUN EL NUMERO DE CORES DE LA COMPUTADORA PARA LOGRAR UN MEJOR RENDIMIENTO. */

        private static final int JUMP = 200; 
        /* JUMP = DEBIDO A QUE EL OBJETIVO NO ES EJECUTAR UNA GRAN CANTIDAD DE TAREAS, SINO DIVIDIR UNA SOLA MUY PESADA, 
         * ME PARECIO ADECUADO ASIGNAR A LOS THREADS PORCIONES DE VALORES NUMERICOS ESTABLECIDOS POR EL VALOR DE JUMP. 
         * EVITANDO ASI QUE SEA UN PROCESO SECUENCIAL, AL DIVIDIR BLOQUES DE PROCESAMIENTO A CADA CORE, MEJORANDO EL RENDIMIENTO. 
         * EL VALOR DE LA CONSTANTE LO ELEGI LUEGO DE HACER VARIAS PRUEBAS CON DIFERENTES VALORES. 
         * HAY UNA RELATIVA MESETA DE VELOCIDAD DE EJECUCION A PARTIR DEL VALOR 200, */

        private final List<Integer> VALUES = new ArrayList<>();
        /* VALUES = ES EL CONJUNTO DE VALORES NUMERICOS ASIGNADOS A CADA THREAD PARA SU POSTERIOR EVALUACION SEGUN LA CONSIGNA DEL TEST. */            
        
    /* VARIABLES DE LA CLASE*/
        static List<Integer> output = Collections.synchronizedList(new ArrayList<Integer>(T));
        /* OUTPUT = ES UN LIST CON LA SALIDA FINAL DEL PROGRAMA. ES DECIR EL CONJUNTO DE NUMEROS PRIMOS CIRCULARES RESULTANTES DE LA EJECUCION.
         * SE UTILIZA EL METODO SYNCHRONIZEDLIST, YA QUE LAS CONSTRUCCIONES LIST/ARRAYLIST NO SON THREAD-SAFE. */     
     
        
    /* METODO RUN */
    /* CODIGO QUE EJECUTARA CADA UNO DE LOS THREADS */
    @Override
    public void run() {
        for (final Integer init : VALUES) {
            for (int i = init; i <= init + JUMP; i++) {
                //CREO UNA INSTANCIA DE LA CLASE TESTPRIMO QUE EVALUARA SI UN NUMERO ES PRIMO CIRCULAR
                TestPrimo tp = new TestPrimo(i);
                //PRIMERO DETERMINO SI EL NUMERO ACTUAL ES PRIMO
                if(tp.isPrime(tp.numero)){
                    //SI EL NUMERO PRIMO, ES CIRCULAR LO AGREGO AL LISTADO FINAL RESULTANTE
                    if(tp.isCircular()){output.add(tp.numero);}                    
                }
            }
        }
    }
    
    /* METODO ADD */ 
    /* GUARDA LOS VALORES ASIGNADOS A CADA THREAD EN EL LIST VALUES*/
    public void add(int number) {this.VALUES.add(number);}
 
    /* METODO ORDENADOS */
    /* DEVUELVE LOS VALORES DE OUTPUT PERO ORDENADOS */
    private static void ordenados(List<Integer> values) {
        Integer aux;
        for(int i = 0; i < values.size() - 1; i++)
        {
            for(int j = 0; j < values.size() - 1; j++)
            {
                if (values.get(j) > values.get(j+1))
                {
                    aux = values.get(j+1);
                    values.set(j+1, values.get(j));
                    values.set(j, aux);
                }
            }
        }
        
        System.out.println("Listado ordenado:" + values);
    }
 
    /* METODO MAIN */
    public static void main(String[] args) throws InterruptedException {
 
    	System.out.println("Test de programación Mercado Libre");
    	System.out.println(" ");
        System.out.println("Valor límite: " + T);
        System.out.println("Threads lanzados: " + N);
        System.out.println(" ");
        System.out.println("Iniciando.........................");
        System.out.println(" ");
 
        //CREO EL CONJUNTO DE THREADS
        TestML[] threadSet = new TestML[N];
        for (int i = 0; i < threadSet.length; i++) {
            threadSet[i] = new TestML();
        }

        //ASIGNO LOS VALORES A CADA THREAD, SEGUN LA CONSTANTE JUMP.
        int j = 0;
        for (int i = 2; i < T; i += JUMP) {
            threadSet[j++].add(i);
            if (j >= N){j = 0;}                          
        }

        //INICIALIZO TODOS LOS THREADS
        for (int i = 0; i < threadSet.length; i++) {
            threadSet[i].start();
        }

        //ESPERO A QUE FINALICEN TODOS LOS THREADS ANTES DE TERMINAR EL HILO PRINCIPAL
        for (int i = 0; i < threadSet.length; i++) {
            threadSet[i].join();
        }
 
        //MUESTRO EL RESULTADO FINAL
        System.out.println("Cantidad de Primos Circulares Menores a " + T + ": " + output.size());
        System.out.println(" ");
        //System.out.println("Listado desordenado:" + output);        
        /* EL LISTADO FINAL ESTARA DESORDENADO, DEBIDO A QUE A CADA THREAD SE LE ASIGNA UNA PORCION DE VALORES NUMERICOS
         * CON EL METODO "ORDENADOS" SE PUEDE VER LA LISTA EN ORDEN, PAGANDO UN MAYOR COSTO DE RENDIMIENTO. */                
        ordenados(output);
    }   
}
