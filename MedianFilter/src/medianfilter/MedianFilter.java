package medianfilter;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class MedianFilter
{
    //reads from file(filename) and returns array filled with input floating point values
    static double[] read(String filename) 
    {
        double Arr [] = {};
        
        try
        {
            List<String> l = new ArrayList<>();
            BufferedReader f = new BufferedReader(new FileReader(filename));
            String s = f.readLine();
            s = f.readLine();
            while(s!=null)
            {
                l.add(s);
                s = f.readLine();
            }
        
            Arr = new double[l.size()];
            for (int i = 0; i < l.size(); i++)
            {
                String val = l.get(i).substring(l.get(i).indexOf(" ")+1);
                double v = Double.parseDouble(val);
                Arr[i] = v;
            }
            
            
    
        }
        catch(IOException e)
        {
            System.out.println(e);
        } 
        
        return Arr;
    }
    
    //writes to a file(filename) the contents of the input double array 'd'
    static void write(double[] d, String filename)
    {
        try
        {
            File file = new File(filename);
            
            if(!(file.exists()))
            {
                file.createNewFile();
            }
            
            BufferedWriter f = new BufferedWriter(new FileWriter(file));
            f.write("Median Filtered Values");
            f.newLine();
            for (int i = 0; i < d.length; i++)
            {
            f.write(i+1 + " " + d[i]);
            f.newLine();
            }
            f.close();
           
        } 
        catch (IOException e)
        {
            System.out.println(e);
        }
    }
    
    static long startTime = 0;
    
    private static void StartTime() //starts "stopwatch"
    {
        startTime = System.currentTimeMillis();
    }
    
    private static float StopTime() //stops "stopwatch" and returns time
    {
        return (System.currentTimeMillis() - startTime)/ 1000.0f;  //returns in seconds (hence /1000.0f)
    }    
  
    static final ForkJoinPool fjPool = new ForkJoinPool(); //shared fork join pool
    
    static void F(int fs,double[] nums, double[] ans)                              
    {
       fjPool.invoke(new TFilter(fs,nums,0,1000000,ans));
    }
   
    public static void main(String[] args)
    {
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter name of file to be read from:");
        String rfn = scn.nextLine();
        System.out.println("Enter name of file to write to:");
        String wfn = scn.nextLine();
        System.out.println("Enter median filter size: ( must be odd and in range [3,21] )");
        int fs = Integer.parseInt(scn.nextLine());
        
        double[] ss = read(rfn); //read() fills array with input values (sampleinputfile.txt)
        double[] Par = new double[ss.length]; // will become filtered array
        Filter f = new Filter(fs,ss,0,1000000); //create sequential filter object
        double[] seq = f.MF();
        F(fs,ss,Par);
        
        write(Par, wfn); //writes to file
        
        //Code used to test for speed found below:
       /*System.gc(); //minimize chance garbage collector will run
        
        
        
        double [] st = new double[5];
        for (int i = 0; i < 5; i++)
        {
        StartTime();
        double[] seq = f.MF();
        float time = StopTime();
        st[i] = time;
        System.out.println("Sequential Run took " + time + " seconds");
        }
        
        double avst = 0;
        for (int i = 0; i < 5; i++)
        {
            avst += st[i];
        }
        
        System.out.println("S ave: " + avst/5);
        
        double [] pt = new double[5];
        for (int i = 0; i < 5; i++)
        {
        StartTime();
        F(fs,ss,a);
        float times = StopTime();
        pt[i] = times;
        System.out.println("Parrallel Run took " + times + " seconds");
        }
        
        double avpt = 0;
        for (int i = 0; i < 5; i++)
        {
            avpt += pt[i];
        }
        System.out.println("P ave: " + avpt/5); */
        
    }
    
}
