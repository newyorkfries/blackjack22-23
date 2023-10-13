import java.util.HashMap;

public class Card implements Comparable<Card>{
  String suit;
  String value;
  boolean aceHigh = true;
  static HashMap<String, Integer> cardValues = new HashMap<String, Integer>();

  /*
   * Create a new card
   * 
   * @param String suit
   * 
   * @param String value
   */
  public Card(String suit, String value) {
    this.suit = suit;
    this.value = value;
    
    if (aceHigh){
      cardValues.put("A", 11);
    }else{
      cardValues.put("A", 1);
    }
    for (int i = 2; i <= 10; i++){
      cardValues.put(String.valueOf(i), i);
    }
    cardValues.put("J", 10);
    cardValues.put("Q", 10);
    cardValues.put("K", 10);
  }

  public String toString() {
    return this.value + "" + this.suit;
  }

  public boolean equals(Card c) {
    if (this.suit.equals(c.suit) && this.value.equals(c.value)) {
      return true;
    }
    return false;
  }

  // Compare values of cards
  // 1 - this > c
  // -1 this < c
  // 0 this == c
  public int compareValues(Card c) {
    return cardValues.get(this.value).compareTo(cardValues.get(c.value));
  }

    public int compareTo(Card o) {
        Card c = (Card)o;
        int rankCompare = suit.compareTo(c.suit);
        return rankCompare != 0 ? rankCompare : suit.compareTo(c.suit);
    }

  public int getValue(){
    return cardValues.get(this.value);
  }

}
