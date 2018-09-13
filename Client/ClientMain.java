package Client;


import javax.swing.*;

public class ClientMain {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//            System.out.println(info.getName());
            if ("CDE/Motif".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        int i = 0;
        while(true) {
            i++;
            System.out.println(i);
            new Client("localhost", 60000);
        }
    }
}
