//Name: Huang Jiaming
//NSID: jih211
//StuID: 11207964

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package school;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jiajia
 */
public class School {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run(){
                Thread t1 = new Thread(new Runnable(){
                    public void run(){
                        new GUI().setVisible(true);
                    }
                });
                Thread t2 = new Thread(new Runnable(){
                    public void run(){
                        new GUI().setVisible(true);
                    }
                });
                Thread t3 = new Thread(new Runnable(){
                    public void run(){
                        new GUI().setVisible(true);
                    }
                });
                
                t1.start();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(School.class.getName()).log(Level.SEVERE, null, ex);
                }
                t2.start();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(School.class.getName()).log(Level.SEVERE, null, ex);
                }
                t3.start();
            } 
        });
    }
    
}
