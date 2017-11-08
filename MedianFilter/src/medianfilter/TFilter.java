package medianfilter;

import java.util.*;
import java.util.concurrent.RecursiveAction;

//Parrallel median filter class
public class TFilter extends RecursiveAction 
{
    //arguments
    int lo;  //lo and hi represent portion of array being dealt with
    int hi;
    int fSize; //filter size
    double [] arr; //input array (filled with values from text file)
    
    double [] ans; //result

    static final int SEQUENTIAL_CUTOFF = 5000; //dictates how many threads will be used
    
    //constructor
    TFilter(int fs, double[] ar, int l, int h, double[] an)
    {
        this.fSize = fs;
        this.arr = ar;
        this.lo = l;
        this.hi = h;
        this.ans = an;
    }
    
   //returns median from double array input
   public double median(double[] a)
    {
        Arrays.sort(a);
        double m;
        if (a.length % 2 == 0)
        {
           m = ( a[a.length/2] + a[a.length/2 - 1])/2;
        }
        else
        {
           m = a[a.length/2];
        }
        
        return m;
        
    }
   
   //inserts filtered array into result array (ans)
   public void MF()
    {
        double[] Arr = new double[hi-lo];
        int cnt = 0;
        for (int i = lo; i < hi; i++)
        {
            Arr[cnt]  = arr[i];
            cnt++;
        }
        
        int m = (Arr.length-fSize)+1;
        List<Double> med = new ArrayList<>();
        double[] win = new double[fSize];
   
        for (int i = 0; i < m; i++)
        {
            int c = i;
            for (int j = 0; j < fSize; j++)
            {
                win[j] = Arr[c];
                c++;
            }
            med.add(median(win));
            
        }
        int x = (Arr.length-m)/2;
        
        for (int i = 0; i < x; i++) //first boundry points
        {
            ans[i] = Arr[i];
        }

        int c = 0;
        for (int i = x; i < Arr.length-x; i++) //median points
        {
            ans[i] = med.get(c);
            c++;
        }
        for (int i = Arr.length-x; i < Arr.length; i++) //last boundry points
        {
            ans[i] = Arr[i];
        }
        
    }
    
    
    //Fork join attempt at median filtering
    //Sadly couldn't get it to work properly in the end 
    //It outputs different values each time its ran - race condition?)
    @Override
    protected void compute() 
    {                           
       if((hi-lo) < SEQUENTIAL_CUTOFF)
       {
           MF();  
       }
       else
       {
         TFilter left = new TFilter(fSize,arr,lo,(hi+lo)/2,ans); 
         TFilter right = new TFilter(fSize,arr,(hi+lo)/2,hi,ans);

         left.fork();
         right.compute();
         left.join();
        
       }
    }

}
    

