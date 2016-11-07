package test;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Cliff
 * Date: 5/2/2014
 * Time: 1:32 PM
 */
public class VolatileTest {

    private static Logger LOGGER = Logger.getLogger( VolatileTest.class.getName() );

    private static volatile int MY_INT = 0;

    public static void main(String[] args) {
        LOGGER.addHandler( new ConsoleHandler() );
        new ChangeListener().start();
        new ChangeMaker().start();
    }

    static class ChangeListener extends Thread {
        @Override
        public void run() {
            int local_value = MY_INT;
            while ( local_value < 5){
                if( local_value!= MY_INT){
                    //LOGGER.info("Got Change for MY_INT : " + MY_INT );
                    System.out.println( "Got Change for MY_INT : " + MY_INT );
                    local_value= MY_INT;
                }
            }
        }
    }

    static class ChangeMaker extends Thread{
        @Override
        public void run() {

            int local_value = MY_INT;
            while (MY_INT <5){
                //LOGGER.info( "Incrementing MY_INT to " + (local_value + 1) );
                System.out.println( "Incrementing MY_INT to " + (local_value + 1) );
                MY_INT = ++local_value;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }
}
