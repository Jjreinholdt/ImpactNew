package Game.impact.levelGen;

import java.io.FileOutputStream;
import java.util.Random;

import android.content.Context;

import Game.impact.GameActivity;

public class Generator {
	private static int WIDTH = 680;
	private static int TILE_SIZE = 60;
	
	public static String path;
    Random gen;
	
    long x = 20+System.currentTimeMillis(), y = 50+System.currentTimeMillis(), z = 35+System.currentTimeMillis(), w = 18 + System.currentTimeMillis();
    long xor128() 
    {
        long t;
     
        t = x ^ (x << 11);
        x = y; y = z; z = w;
        return w = w ^ (w >> 19) ^ t ^ (t >> 8);
    }
    
    public Generator()
    {
    	gen = new Random(xor128());
    }
    
	public void genlvl(GameActivity activity, int level, int height)
	{
		try
		{
			FileOutputStream out = activity.openFileOutput("" + level + ".lvl",  Context.MODE_PRIVATE);
			
			String line = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
	        line += "<level width=\"680\" height=\"" + height + "\">\n";
	        line += "    <entity x=\"340\" y=\"20\" type=\"platform1\"/>\n";
	        line += "    <entity x=\"340\" y=\"50\" type=\"player\"/>\n";
	        out.write(line.getBytes());
	        
	        int tiles = height/TILE_SIZE;
	        int range = 2;
	        String type = "";
	        for(int i = 10; i <= tiles; ++i)
	        {
	        	if((i%5)==0)
	        	{
	        		if(gen.nextInt(2) == 0)
	        		{
	        			type = "bird";
	    	        	line = "    <entity x=\"" + (10+gen.nextInt(WIDTH-40)) + "\" y=\"" + i*TILE_SIZE + "\" type=\"" + type + "\"/>\n";
	            		out.write(line.getBytes());
	        		}
	        		continue;
	        	}
	        				
	        	if(i > tiles/3 && i <= (tiles/3)*2)
	        		range = 3;
	        	else if(i > (tiles/3)*2)
	        		range = 5;
	        	
	        	int n = range < 6 ? gen.nextInt(range) : gen.nextInt(range)+1;
	        	
	        	switch(n)
		        {
	        	case 0:
	        		continue;
	        	case 1:
	        		type = "coin";
	        		break;
	        	case 2:
	        		type = "platform2";
	        		break;
	        	case 3:
	        		type = "platform3";
	        		break;
	        	case 4:
	        		type = "rotator";
	        		break;
	        	default:
	        		continue;
		        }
	        	
	        	line = "    <entity x=\"" + (10+gen.nextInt(WIDTH-40)) + "\" y=\"" + i*TILE_SIZE + "\" type=\"" + type + "\"/>\n";
        		out.write(line.getBytes());
	        }
	        	
	        line = "</level>";
	        out.write(line.getBytes());
	        
	        out.close();
		}catch(Exception e)
		{
			System.out.println("Failed to Write\n");
		}
	}
}