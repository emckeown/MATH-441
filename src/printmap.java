import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import java.util.Scanner;

import javax.swing.*;
import javax.imageio.ImageIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class printmap{
int[] X = new int[30];
int[] Y = new int[30];
int i = 1;
String content = "";
int xold = 0;
int yold = 0;
    private BufferedImage bImage;
    private ImageIcon image;
    private JLabel imageLabel;
    private int xClicked = 0;
    private int yClicked = 0;
    private int xDragged = 0;
    private int yDragged = 0;
    int previndex = 1;
    int nextindex = 0;
    Team p;

    private MouseAdapter mouseListener =
       new MouseAdapter() {
           private boolean paint = false;
           @Override
           public void mousePressed(MouseEvent me) {
            String fileName = "F:/5 sem/441/coo.txt";

               // This will reference one line at a time
               String line = null;

               File file = new File("coordinates.txt");

               try {

                   Scanner sc = new Scanner(file);
                        int j = 0;
                   while (j<30) {

                       String i = sc.next();
                       System.out.println(i + "\n");
                       X[j] = Integer.valueOf(i);
                       i = sc.next();
                       System.out.println(i);
                       Y[j] = Integer.valueOf(i);
                      
                       j++;}
                   sc.close();
               }
               catch (FileNotFoundException e) {
                   e.printStackTrace();
               }

            Graphics2D g2 = bImage.createGraphics();
               g2.setStroke(new BasicStroke(2));
               g2.setColor(Color.black);

               int i = 0;
               while(i<190)
               {Team next = p.getElement(p.getTeamSchedule(), i);
               if(next.getHomeIndex() == -1)
               {}
               else
               {
            	   
                       nextindex = next.getHomeIndex();

                       g2.drawLine(X[previndex],Y[previndex],
        X[nextindex],Y[nextindex]);
                       g2.setColor(Color.RED);
                       g2.setFont(new Font("default", Font.BOLD, 16));
                       g2.drawString(next.getTeamName(),X[nextindex]+5 , Y[nextindex]);
                       previndex = nextindex;
                       g2.setColor(Color.black);}
                       i++;}
                       System.out.println(X[nextindex]+" "+Y[nextindex]+" "+X[previndex]+" "+Y[previndex]);
                        g2.setColor(Color.red);
                        g2.setStroke(new BasicStroke(4));
                        g2.drawLine(X[p.getHomeIndex()],Y[p.getHomeIndex()]+3,
        X[p.getHomeIndex()], Y[p.getHomeIndex()]+3);
                        g2.drawLine(X[p.getHomeIndex()]+3,Y[p.getHomeIndex()],
        X[p.getHomeIndex()]+3, Y[p.getHomeIndex()]);
                g2.dispose();
               imageLabel.setIcon(new ImageIcon(bImage));

               me.getComponent().invalidate();
               me.getComponent().repaint();

           }};

    public printmap(Team team) {
        try {
            bImage = ImageIO.read(new File("N America Outline Map.jpg"));
            image = new ImageIcon(bImage);
            p = team;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void displayGUI() {
        JFrame frame = new JFrame("Painting on Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        imageLabel = new JLabel(image);
        imageLabel.addMouseListener(mouseListener);
        imageLabel.addMouseMotionListener(mouseListener);

        contentPane.add(imageLabel);

        frame.setContentPane(contentPane);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void print(Team team) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new printmap(team).displayGUI();
            }
        });
    }
}