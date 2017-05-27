package server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Pietro on 27/05/2017.
 */
public class MosseGiocatoreTest {
    @Test
    public void tiraIDadi() throws Exception {
        int i;
        for (int j=0; j<20;j++) {
            i = (int) ((Math.random() * 100) % 6) + 1;
            System.out.println(i);
        }
    }

}