package game;

import javax.swing.*;

public class game {



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("International Capstone Wrestling");
                Map map = new Map();
                map.setLayout(null);
                frame.add(map);
                map.setFocusable(true);
                map.requestFocusInWindow();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setResizable(false);



            }


        });

    }










}
