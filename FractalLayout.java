import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;
import java.lang.*;

/*  FractalLayout.java
 *     Scott Stinson
 *     This is a Java Swing rewrite of a fractal program that uses the 
 *     generalized Newton's method.  (Fractal Programming and Ray Tracing
 *     with C++, p. 40, Roger T. Stevens
 */

class Fractal {
   	
   private float[] DegreeList= new float[10];
   private int numCol = 640;
   private int numRow = 350;
   private int[][] FractalTable = new int[numCol][numRow];
   int degree = 0;

   
   Fractal(int inputList[])
   {
       for (int i=0; i < 10; i++)
          DegreeList[i] = inputList[i];

       for (int i=9; i>= 0; i--)
       {
           if (DegreeList[i] != 0)
	   {
              degree = i;
	      break;
	   } 
       }

       for (int i=0; i < numCol; i++)
          for (int j=0; j< numRow; j++)
              FractalTable[i][j] = 0;
	  
   }

   void GenerateFractal()
   {
       int max_iterations = 64;
       int max_size = 4;
       double Xmax= 2.0;
       double Xmin = -2.0;
       double Ymax = 1.20;
       double Ymin = -1.20;
       double deltaX, deltaY;
       Complex z = new Complex();
       Complex old_z = new Complex();
       Complex f = new Complex();
       Complex f_prime = new Complex();
       Complex z_power = new Complex();

       deltaX = (Xmax - Xmin) / 640;
       deltaY = (Ymax - Ymin) / 350;
       old_z.assign(42,42);

       for (int col = 0; col < numCol; col++)
       {
          for (int row = 0; row < numRow; row++)
	  {
             z.assign(Xmin + col*deltaX, Ymax - row * deltaY);
	     int i = 0;
	     while (i < max_iterations)
	     {
               f.assign(0,0);
	       f_prime.assign(0,0);
	       z_power.assign(1,0);
	       for (int j = 0; j<degree; j++)
	       {
                   f.assign(f.add(z_power.multiply(DegreeList[j])));
		   f_prime.assign(f_prime.add(
			          z_power.multiply(DegreeList[j+1] * (j+1))));
		   z_power.assign(z.multiply(z_power));
	       }
	       f.assign(f.add(z_power.multiply(DegreeList[degree])));
	       z.assign(z.subtract(f.divide(f_prime)));
	       if (z.notEquals(old_z))
	       {
                 old_z.assign(z);
		 i++;
	       }
	       else
		       break;
	     }
             int color = i%16;
             FractalTable[col][row] = color;
	  }
       } 
   }

   int[][] ReadFractal()
   {
     return FractalTable;
   }

}

// Model the Pixel drawing class on the OvalPanel program
// from the Java Swing reference book.
// (The Definitive Guide to Java Swing 3rd Ed, Page 111,
// John Zukowski)
class PixelComponent extends JComponent {
   Color color;
   int screenHeight = 350;
   int screenWidth = 640;
   int Screen[][] = new int[screenWidth][screenHeight];
   Color palette[] = new Color[16];

   public PixelComponent(int inputPixels[][], Color inputPalette[])
   {
      for (int i=0; i<screenWidth; i++)
	      for (int j=0; j<screenHeight; j++)
		      Screen[i][j] = inputPixels[i][j];
      for (int i=0; i<16; i++)
	      palette[i] = inputPalette[i];

   }

   public void paintComponent(Graphics g) {
	   setOpaque(true);
           for (int i=0; i<screenWidth; i++)
		   for (int j=0; j<screenHeight; j++)
	   {
            g.setColor(palette[Screen[i][j]]);
	    g.fillRect(i,j,1,1);
	   }
   }
}

public class FractalLayout {
	private static final Insets insets = new Insets(0,0,0,0);
	public static void main(final String args[]) {
        Runnable runner = new Runnable() {
        public void run() {
        
        int screenWidth = 640;
	int screenHeight = 350;

	// Stevens uses the EGA Palette for the C++ version of the program.
	// Since this is a rewrite for a Swing GUI application, we have
	// to create 16 new colors.
	Color color[] = new Color[16];
        // Use a simple BW scheme until I look up the actual 16 colors
	// for the EGA palette.
	for (int i=0; i<16; i++)
          color[i] = new Color(i*16, i*16, i*16);
	
	// These are not the actual colors but index numbers for the
	// palette.
//	int FractalBitmap[][] = new int[screenWidth][screenHeight];
//	for (int i=0; i<screenWidth; i++)
//		for (int j=0; j<screenHeight; j++)
//			FractalBitmap[i][j] = 0;
		
	final JFrame frame = new JFrame("GridBagLayout");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLayout(new GridBagLayout());
	JButton button;
	JLabel TitleLable = new JLabel("Welcome to the Fractal Layout. " +
			"Enter in each constant for the 10 degree equation " +
			"then hit the generate button.");
	
	JLabel DegreeLabel[];
	DegreeLabel = new JLabel[10];
	JTextField DegreeTextField[];
	DegreeTextField = new JTextField[10];
	JButton GenerateButton = new JButton("Generate");


	addComponent(frame, TitleLable, 0, 0, 1, 1,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        for (int i = 0; i<10; i++)
	{               	   
	   DegreeLabel[i] = new JLabel("Degree " + i);
	   DegreeTextField[i] = new JTextField();
	  
	   DegreeLabel[i].setLabelFor(DegreeTextField[i]);
	   addComponent(frame, DegreeLabel[i], 0, i+1, 1, 1,
			   GridBagConstraints.CENTER, GridBagConstraints.BOTH);
	   addComponent(frame, DegreeTextField[i], 1, i+1, 1, 1,
			   GridBagConstraints.CENTER, GridBagConstraints.BOTH);
	    //Set default value to 0.
	    DegreeTextField[i].setText("0");
	
	}
	addComponent(frame, GenerateButton, 0, 12, 1, 1,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH);


	// This will be the input for the Fractal Constructor.
	// The individual values will come from the 10 text fields.
        int[] FractalDegrees = new int[10];
        for (int i=0; i < 10; i++)
            FractalDegrees[i] = 0;

        // Define an ActionListener for the Generate Button.
	// This will call the constructor for Fractal and create a pop up
	// window containing the bitmap of the fractal.
	ActionListener generateListener = new ActionListener()
	{
           
	    	public void actionPerformed(ActionEvent actionEvent) {
	       	System.out.println("Generate was selected.");

                for (int i = 0; i < 10; i++)
		{
                    String outputText = DegreeTextField[i].getText();
		    System.out.println(outputText);
		    System.out.println("After parseInt:");
		    Integer temp = 0;
		    FractalDegrees[i] = temp.parseInt(outputText);
		    System.out.println(FractalDegrees[i]);
		}
	    Fractal instanceFractal = new Fractal(FractalDegrees);
	    //instanceFractal.GenerateFractal();
	    int[][] FractalBitmap = new int[screenWidth][screenHeight];
	       for (int i=0; i<screenWidth; i++)
		       for (int j=0; j<screenHeight; j++)
			       FractalBitmap[i][j] = 0;
	     instanceFractal.GenerateFractal();
	     FractalBitmap = instanceFractal.ReadFractal();
		
	    //JWindow FractalWindow = new JWindow(frame, gc);	
	    JFrame FractalWindow = new JFrame("Fractal Window");
	    FractalWindow.setSize(640,350);
	  
	    //Change a few FractalBitmap pixels to make sure that we are
	    // showing every individual pixel as opposed to one big
	    // rectangle of 1 color.
	    //FractalBitmap[10][10] = 10;
	    //FractalBitmap[20][20] = 15;
	    
	    
	    PixelComponent Pixels = new PixelComponent(FractalBitmap ,color);
	    FractalWindow.add(Pixels);
	    FractalWindow.setVisible(true);
	  }
	};

	//Attach the listener to the Generate Button
	GenerateButton.addActionListener(generateListener);

	frame.setSize(1000, 500);
	frame.setVisible(true);
	}
	};
	EventQueue.invokeLater(runner);
	}

private static void addComponent(Container container, Component component,
	int gridx, int gridy, int gridwidth, int gridheight,
	int anchor, int fill) {
   GridBagConstraints gbc = new GridBagConstraints(gridx, gridy,
        gridwidth, gridheight, 1.0, 1.0, anchor, fill, insets,
	0, 0);
   container.add(component, gbc);
}
}
