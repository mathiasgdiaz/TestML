/**
 *
 * @author MathiasGDiaz
 */


public class TestPrimo {
    
    public Integer numero;
    public TestPrimo (Integer numero){
        this.numero = numero;
    }
    
    /* METODO ISCIRCULAR*/
    /* EVALUA SI EL NUMERO CORRESPONDIENTE A LA INSTANCIA CREADA DESDE EL THREAD ES UN PRIMO CIRCULAR */    
    public boolean isCircular(){
        String num = numero+"";
        //CONVIERTO EL NUMERO PRIMO A STRING PARA PODER TRABAJAR CON LOS CARACTERES. 
        //e.g. "971".
        
        boolean flag = false;        

        for (int j = 0; j < num.length(); j++) {            
            String last = num.charAt(num.length() - 1)+"";
            //OBTENGO EL ULTIMO CARACTER DEL NUMERO PRIMO. PARA EL EJEMPLO SERIA: LAST = "1".
            
            String rotate = last + num;            
            //COLOCO EL ULTIMO VALOR DEL NUMERO EN LA PRIMERA POSICION. ROTATE = "1971"
            
            String truncate = rotate.substring(0, rotate.length() - 1);
            //QUITO EL CARACTER ROTADO DE LA ULTIMA POSICION. TRUNCATE = "197".
            //OBTENGO ASI UNA ROTACION DEL NUMERO PRIMO ORIGINAL.

            //EVALUO AHORA SI ESTA ROTACION TAMBIEN ES UN NUMERO PRIMO.            
            if(!isPrime(Integer.parseInt(truncate))){            
               flag = false;
               break;
            }
            else{
                flag = true;
                num = truncate;//COLOCO LA NUEVA ROTACION EN EL VALOR INICIAL PARA SEGUIR EL PROCESO
            }
        }
        return flag;
    }
    
    /* METODO ISPRIME*/
    //RECIBE COMO PARAMETRO UN NUMERO ENTERO Y RETORNA UN VALOR BOOLEANO DE ACUERDO A SI EL NUMERO ES PRIMO O NO
    public boolean isPrime(Integer y){
        for(int x = 2; x < y; x++){
            if(y % x == 0){return false;}
        }
        return true;
    }
}
