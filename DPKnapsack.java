import java.util.*;
import java.io.*;

/**
  * This program is an implementation of knapsack algorithm which maximizes the value of a knapsack.
  * We are given a file that consists of several lines of input. Name of item and its corresponding 
  * weight and value is provided in each line. The knapsack has a certain capacity. This program 
  * selects those items so that the value of the items is maximum and total weight of those items is
  * lesser than or equal to weight of the knapsack. It has methods that calculate total weight of
  * items and number of items in the optimal solution subset or knapsack.It also checks if a given 
  * item is in the knapsack and prduces detailed information about the items in knapsack in proper
  * format
  * @author Bidhan Bhandari
 */
public class DPKnapsack
{
   private Map<String, Items> myItems;
   private String[] itemNames;
   private int[] itemWeights;
   private int[] itemValues;
   private int capacity;
   
   /**
     * Constructs a knapsack with a certain capacity, reads input file, puts those values in different 
     * arrays. It also checks for existence of the input file and handles exception if file was not 
     * found
     * @param capacity Maximum weight that knapsack can have
     * @param itemFile The file to be proccessed
    */ 
   public DPKnapsack(int capacity, String itemFile)
   {
      this.capacity = capacity;
      myItems = new HashMap<>();
   
      try //Checks if file exists or not
      {
         Scanner in = new Scanner(new File(itemFile));
         while(in.hasNext())
         {
            String name = in.next();//reads next token of the input file as string
            int item_weight = in.nextInt();//reads next token of the input file as integer
            int item_value = in.nextInt();//reads next token of the input file as integer
            myItems.put(name, new Items(item_weight, item_value));//puts items in the hashmap 
            in.nextLine();
         }
         int arraySize = myItems.size();//calculates size of the hashmap to determine size of the array
         itemNames = new String[arraySize];
         itemWeights = new int[arraySize];
         itemValues = new int[arraySize];
         
         Iterator i = myItems.keySet().iterator();
         int j = 0;
         //Inserts item name, item weight, and item values in their corresponding arrays
         while(i.hasNext()&&j<arraySize)         
         {
            String key = i.next().toString();
            itemNames[j] = key;  
            itemWeights[j] = myItems.get(key).getWeight();
            itemValues[j] = myItems.get(key).getValue();
            j++;
         }
      }
      catch(FileNotFoundException e)//Catches and handles exception if file is not fouund
      {
         System.out.println("File Not Found!");
      }
   }
   
   /**
     * Computes the total weight of items that are in the optimal solution set or knapsack of given capacity
     * @return Total weight of the items in the optimal solution set
    */
   public int optimalWeight()
   {
      return optimalWeight(capacity); 
   }
   
   /**
     * Computes total number of items in the optimal solution set or knapsack of given capacity
     * @return Number of items in the optimal solution subset
    */
   public int optimalNumber()
   {
      return optimalNumber(capacity);
   }
   
   /**
     * Checks if the given item is part of optimal solution subset or knapsack of given total capacity
     * @param item The name of the item to be checked
     * @return true if item is in the optimal solution subset or false otherwise
    */
   public boolean contains(String item)
   {
      return contains(item, capacity);
   }
   
   /**
     * Produces the formatted output of the informations items in optimized soltion subset
     * @return string representation of the items in the knapsack or optimized solution subset
    */
   public String solution()
   {
      return solution(capacity);
   }
   
   /**
     * Computes the total weight of items that are in the optimal solution set or knapsack of maximum weight given as parameter
     * @param maxWeight The maximum weight capacity of knapsack
     * @return The total weight of the items in the knapsack
    */
   public int optimalWeight(int maxWeight)
   {
      int totalWeight = 0;
      for (int i = 0; i<optimize(maxWeight).size();i++){
         totalWeight+=itemWeights[optimize(maxWeight).get(i)];
      }
      return totalWeight;
   }
   
   /**
     * Computes total number of items in the optimal solution set or knapsack of maximum weight given as parameter
     * @param maxWeight The maximum weight capacity of knapsack
     * @return Number of items in the optimal solution subset
    */
   public int optimalNumber(int maxWeight)
   {
      return optimize(maxWeight).size();
   }
   
   /**
     * Checks if the given item is part of optimal solution subset or knapsack of maximum weight given as parameter
     * @param item The name of the item to be checked
     * @param maxWeight The maximum weight capacity of knapsack
     * @return true if item is in the optimal solution subset or false otherwise
    */
   public boolean contains(String item, int maxWeight)
   {
      boolean flag = false;
      for (int i = 0; i<optimize(maxWeight).size(); i++)
      {
         if (itemNames[optimize(maxWeight).get(i)].equals(item))//Proceeds if item is in the optimal solution subset
         {
            flag = true;
         }
      }
      return flag;
   }
   
   /**
     * Produces the formatted output of the informations items in optimized soltion subset
     * @param maxWeight The maximum weight capacity of knapsack
     * @return string representation of the items in the knapsack or optimized solution subset
    */
   public String solution(int maxWeight)
   {
      String mySolution = "";
      int totalValue = 0;
      for (int i = 0; i<optimize(maxWeight).size();i++)
      {
         String s = String.format("%-15s%15d%15d\n",itemNames[optimize(maxWeight).get(i)],itemWeights[optimize(maxWeight).get(i)],itemValues[optimize(maxWeight).get(i)]);
         mySolution+=s;
         totalValue+=itemValues[optimize(maxWeight).get(i)];
      }
      String dots = String.format("%s\n",".............................................");
      String header = String.format("%-15s%15s%15s\n","ITEM NAMES","ITEM WEIGHTS","ITEM VALUES");
      String footer = String.format("%-15s%15d%15d","TOTAL",optimalWeight(maxWeight),totalValue);
      return header + dots + mySolution + dots + footer;
   }
   
   /**
     * Private method that computes the position where the items in the optimised solution subset exits in its
     * corresponding array and inserts those positions in the array list
     * @param maxWeight The maximum weight capacity of knapsack
     * @return The lists containing the position of items in optimized solution subset in their corresponding 
     * array
    */
   private ArrayList<Integer> optimize(int maxWeight)
   {
      int K[][] = new int[itemValues.length+1][maxWeight+1];
      for (int i = 0; i<=itemValues.length;i++)
      {
         for (int w = 0; w<=maxWeight;w++)
         {
            if (i==0||w==0)//Checks if knapsack weight is zero or pool of items does not exists
            {
               K[i][w] = 0;
            }
            else if(itemWeights[i-1]<=w)//Proceeds if weight of current item is lesser or equal to current weight of the knapsack
            {
               K[i][w] = Math.max(itemValues[i-1]+K[i-1][w-itemWeights[i-1]],K[i-1][w]);
            }
            else
            {
               K[i][w] = K[i-1][w];
            }
         }
      }
      
      ArrayList<Integer> result = new ArrayList<>();
      int k = maxWeight;
      for (int j = itemNames.length;j>=1; j--)//goes down from the last element of the optimisation table
      {
         boolean was_added = K[j][k] != K[j-1][k];//Checks if the value in the current position of table has changed 
         if (was_added)//Proceeds if changed
         {
            result.add(j-1);
            k-=itemWeights[j-1];
         }
      }
      return result;
   }
}



