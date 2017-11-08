package medianfilter;

import java.util.*;

//Sequential median filter class
public class Filter
{
    //agruments
    int lo; //lo and hi represent portion of array being dealt with
    int hi;
    int fSize; //filter size
    double [] arr; //input array (filled with values from text file)
   
    //constructor
    Filter(int fs, double[] a, int l, int h)
    {
        this.fSize = fs;
        this.arr = a;
        this.lo = l;
        this.hi = h;
    }
    
    //returns median from array of double values
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
    
    //returns filtered array
    public double[] MF()
    {
        double[] Arr = new double[hi-lo];
        double[] ans = new double[Arr.length];
        
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
        
        return ans;
    }
    
    
}
