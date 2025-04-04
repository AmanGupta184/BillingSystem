package common;

import java.io.File;
import javax.swing.JOptionPane;


public class OpenPdf {

    public static void openById(String id) {
        try {
            if ((new File("C:\\Users\\amang\\OneDrive\\Documents\\NetBeansProjects\\A-MartSystem\\src\\BillPdf\\" + id + ".pdf")).exists()) {
                Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler C:\\Users\\amang\\OneDrive\\Documents\\NetBeansProjects\\A-MartSystem\\src\\BillPdf\\" + id + ".pdf");
            }else{
                JOptionPane.showMessageDialog(null,"File does not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
