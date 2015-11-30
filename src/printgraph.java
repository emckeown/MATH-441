

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


	public class printgraph{

	int i = 1;


	    private BufferedImage bImage;
	    private ImageIcon image;
	    private JLabel imageLabel;





	    public printgraph(double[] distance) {
	        try {
	            bImage = ImageIO.read(new File("graph.jpg"));
	            image = new ImageIcon(bImage);

	        } catch(Exception e) {
	            e.printStackTrace();
	        }
	    }

	    private void displayGUI(int iter, double[] distance) {
	        JFrame frame = new JFrame("Painting on Image");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        JPanel contentPane = new JPanel();
	        imageLabel = new JLabel(image);
	        Graphics2D g2 = bImage.createGraphics();
	        g2.setStroke(new BasicStroke(2));
	        g2.setColor(Color.black);

	        int k = 0;
	      int x = 95;

	while(k<iter-2){
	        g2.drawLine(x,300
	+(int)(((distance[k]-distance[0])/(distance[iter-1]-distance[0]))*300),x+1,300+
	(int)(((distance[k+1]-distance[0])/(distance[iter-1]-distance[0]))*300)
	);
	x = x+1;

	k++;}
	         g2.dispose();
	        imageLabel.setIcon(new ImageIcon(bImage));

	        contentPane.add(imageLabel);

	        frame.setContentPane(contentPane);
	        frame.pack();
	        frame.setLocationByPlatform(true);
	        frame.setVisible(true);
	    }

	    public static void print(double[] distance, int iter) {
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {

	                new printgraph(distance).displayGUI(iter, distance);
	            }
	        });
	    }
}
