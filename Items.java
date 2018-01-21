/** This class is a helper class that helps to create the hashmap to calculate the sixe of the array
  * of items we are analyzing
 */
public class Items{
//Attributes
private int weight;//weight of the value
private int value;//value of the item

/**
  * Constructs items with certain weight and value
  * @param weight Weight of the items
  * @param value value of the items
 */
public Items(int weight, int value){
this.weight = weight;
this.value = value;
}

/**
  * Provides weight of the items
  * @return Weight of the items 
 */
public int getWeight(){
return weight;
}

/**
  * Provides value of the items
  * @return value of the items
 */
public int getValue(){
return value;
}
}