/*
PROGRAM NAME - Blackjack ISU 

PROGRAMMERS - Noah& Asvin

DATE - Started 1/6/2023

DESCRIPTION - 1 player blackjack game versus ai dealer, with more advanced features such as
              splitting, doubling down, and betting, you will start 100,000 chips to spend.


*/
import java.util.*;
class Main {

  static ArrayList<Card> deck = new ArrayList<Card>();//variable to store deck of cards
  static Card pile = null;
  
  public static void generateDeck() {//generate deck of cards
    String[] suits = { "♦", "♥", "♠", "♣" };
    String[] values = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
    for (String suit : suits) {
      for (String value : values) {
        deck.add(new Card(suit, value));//adds cards to deck
      }
    }//end result is a full deck of cards

    Collections.shuffle(deck);//shuffles deck
  }


  public static Card drawCard() {//method for drawing cards
    Card c = deck.get(0);//takes a card from the top of the deck
    deck.remove(0);//removes that card from the deck

    return c;//returns card
  }

  
  public static void main(String[] args) throws InterruptedException{//throw InterruptedException is here for the Thread.sleep()
    
    Scanner scan = new Scanner(System.in);
    ArrayList<Card> playerHand = new ArrayList<Card>();//player hand
    ArrayList<Card> splitHand = new ArrayList<Card>();//player hand during splits
    ArrayList<Card> dealerHand = new ArrayList<Card>();//dealer hand
    
    String RESET = "\u001b[0m";//reset
    String Cyan = "\u001b[38;5;123m";//code for cyan
    String Red = "\u001b[38;5;1m";
    String Green = "\u001b[38;5;118m";
    String Yellow = "\u001b[38;5;226m";
    
    String playAgain = "y";//main game loop
    String hitStand = "hit";//must start as hit so game runs
    
    int playerScore = 0;//player card value
    int splitScore = 0;//Player card value when splitting
    int dealerScore = 0;//dealer card value
    
    double player=1;//used for getting dealer to go
    
    double coins = 100000; //starting balance
    double bet;//amount user has bet
    double splitBet = 0;//the bet on the split hand
    
    int firstCard = 0;//the first card the player draws, used for aceHigh
    int dealerFirstCard = 0;//the first card the dealer draws, used for aceHigh
    int secondCard = 0;//the second card the player draws, used for aceHigh
    int dealerSecondCard = 0;//the second card the dealer draws, used for aceHigh


    System.out.println("Welcome to Blackjack!\n\n");
    while (playAgain.equalsIgnoreCase("y")&&coins!=0){//while they want to play again and have coins
      System.out.print("You have $" + coins + ". Please enter your bet amount: ");
      bet = Integer.parseInt(scan.nextLine());
      
      if (bet > coins||bet==0){//ensure they bet a proper amount of coins
        while (bet>coins||bet==0){
          System.out.print("You do not have enough money for that. Please enter your bet amount: ");
          bet = Integer.parseInt(scan.nextLine());
        }
      }
      
      if(bet<100){ // $100 minimum bet
        while (bet<100){
          System.out.print("Minimum bet is $100. Please enter your bet amount: ");
          bet = Integer.parseInt(scan.nextLine());
        }
      }
      System.out.println("You have bid $"+bet+"");
      coins-=bet;
      generateDeck();
      Card card = drawCard();//drawing card
      playerHand.add(card);//adding card to player hand
      firstCard = (card.getValue());//storing value in the firstCard variable
      playerScore += (card.getValue());//increase playerScore by card value
      
      card = drawCard();//drawing new card
      dealerHand.add(card);//adding to dealer hand
      dealerScore += (card.getValue());//same as playerScore, but for dealer
      dealerFirstCard = card.getValue();//same as firstCard, but for dealer
      
      
      card = drawCard();
      playerHand.add(card);//second card being dealt
      secondCard = card.getValue();
      playerScore += (card.getValue());
      if (playerScore>21){
        playerScore -=10;//if they have 2 aces, take away 10 from score
      }
      card = drawCard();
      dealerHand.add(card);//second card being given to dealer
      dealerScore += (card.getValue());
      dealerSecondCard = card.getValue();
      if (dealerScore>21){
        dealerScore -=10;//if they have 2 aces, take away 10 from score
      }
      Thread.sleep(700);//sleep for 700 ms
      System.out.print(Yellow+"\nDealer has a ");//tell user what dealer has
      System.out.print(card.getValue());
      //System.out.print(cardsuit()); ask if possible to print card suit
      System.out.print(" and a hidden card.\n"+RESET);//tell user what dealer has
      Thread.sleep(500);
      System.out.print("You have ");//tell user their hand and score
      System.out.println(playerHand.toString());
      System.out.println("Your score is "+playerScore);
      Thread.sleep(400);

      if(dealerScore==21 && playerScore==21 && playerHand.size()==2 && dealerHand.size()==2){//conditions for blackjack off deal
        coins+=bet;
        System.out.println(Cyan+"Tie! Both you and the dealer have Blackjack. You get back your $" +bet+RESET);
        player= 0;//stops from hitting again
        
      }else if (playerScore==21 && playerHand.size()==2){//if player has blackjack off the start
        coins+=bet*1.5;//blackjack payout is 1:1.5 instead of 1:2
        System.out.println(Green+"\nYou have Blackjack! You have won $" +bet*1.5+RESET);
        player= 0;
      }else if (dealerScore==21 && dealerHand.size()==2){//dealer blackjack
        System.out.println(Red+"Dealer has Blackjack! You have lost $"+ bet+RESET);
        player = 0;
        
      }else{
        while (playerScore <=21 && dealerScore <=21){//game loop while neither player won
          
          
          while(player==1){
            if (bet<=coins&&firstCard==playerScore/2.0&&playerHand.size()<=2){ // this checks if you have enough to double down or split
              System.out.print("\nWould you like to hit, stand, double down or split? ");
              hitStand = scan.nextLine();
              if(!(hitStand.equalsIgnoreCase("hit") || hitStand.equalsIgnoreCase("stand")|| hitStand.equalsIgnoreCase("double down")||hitStand.equalsIgnoreCase("split"))){//spelling
                System.out.print("Would you like to hit, stand, double down or split? ");
                hitStand = scan.nextLine();
              }
            }else if (bet<=coins&&playerHand.size()<=2){//check if double down is possible
              System.out.print("\nWould you like to hit, stand or double down? ");
              hitStand = scan.nextLine();
              if(!(hitStand.equalsIgnoreCase("hit") || hitStand.equalsIgnoreCase("stand")|| hitStand.equalsIgnoreCase("double down"))){//spelling
                System.out.print("Would you like to hit, stand or double down? ");
                hitStand = scan.nextLine();
              }
            }
             else{
              System.out.print("\nWould you like to hit or stand? ");//if they cannot double down or split
              hitStand = scan.nextLine();
              if(!(hitStand.equalsIgnoreCase("hit") || hitStand.equalsIgnoreCase("stand"))){
                System.out.print("Try entering \"hit\" or \"stand\"!\n\nWould you like to hit or stand? ");
                hitStand = scan.nextLine();
              }
            }
            if(hitStand.equalsIgnoreCase("hit")){
              card = drawCard();
              playerHand.add(card);
              playerScore += (card.getValue());
              if (playerScore>21){//check if their score is over 21
                if (secondCard ==11){//if it is and they have an ace, lower score by 10
                  playerScore-=10;
                  secondCard =0;
                }else if(firstCard ==11){
                  playerScore-=10;
                  firstCard = 0;
                }else if(card.getValue()==11){
                  playerScore-=10;
                }
              }
              System.out.print("\nYou drew a ");//telling player whay they drew
              System.out.print(card.getValue());
              System.out.print("!\nYou have ");
              System.out.println(playerHand.toString());
              System.out.println("Your score is "+playerScore);
              if (playerScore > 21){// this is to make sure if they go above 21 the delear still does his turn
                hitStand ="stand";
              }
              break;
            }else if(hitStand.equalsIgnoreCase("stand")){// this will make it so it reveals the delears cards and does his turn after
              System.out.println("You have chosen to stand!"+Yellow+" \nDealer is revealing his card!");//dealer is a nice yellow colour
              System.out.print("\nDealer has ");
              System.out.println(dealerHand.toString());
              System.out.println("Dealer score is " + dealerScore+RESET);
              player=0;
            }else if(hitStand.equalsIgnoreCase("double down")){//Doubles bet for a chance to win big
              System.out.println("");
              System.out.println("You have betted an additional $"+bet);
              coins-=bet;
              bet*=2;
              System.out.println("You have $"+coins+" remaining");//tell user how much left
              card = drawCard();
              playerHand.add(card);//giving them the extra card
              playerScore += (card.getValue());
              
              if (playerScore>21){//check if their score is over 21
                if (secondCard ==11){//if it is and they have an ace, lower score by 10
                  playerScore-=10;
                  secondCard =0;
                }else if(firstCard ==11){
                  playerScore-=10;
                  firstCard = 0;
                }else if(card.getValue()==11){
                  playerScore-=10;
                }
              }
              System.out.print("You drew a ");
              System.out.print(card.getValue());//telling what card was drawn
              System.out.print("!\nYou have ");
              System.out.println(playerHand.toString());//showing full hand
              System.out.println("Your score is "+playerScore);//telling total score
              hitStand = "stand";
              if (playerScore > 21){// this makes it so if you bust the delear goes
                System.out.println("");
                System.out.println(Yellow+"Dealer is revealing his card!");//showing dealer full hand
                System.out.print("\nDealer has ");
                System.out.println(dealerHand.toString());
                System.out.println("Dealer score is " + dealerScore+RESET);
              }
              player=0;
              break;
            }else if (hitStand.equalsIgnoreCase("split")){//Splits hand into 2 cards, with equal bet on each
              splitHand.add(playerHand.get(0));//add first card to the split hand
              splitScore+=firstCard;//set the score of the split to the first card
              System.out.println("Your bet of $"+bet+ " has been moved into $" +bet+ " for each hand");
              splitBet=bet;//setting bet value for split hand
              coins-=splitBet;//taking away the coins from total
              playerScore-=firstCard;//taking the first card away from the score
              playerHand.remove(0);//removing the first item in the playerHand
              System.out.println("");
              System.out.println("This is your first hand");//telling user which hand they are playing for right now
              card = drawCard();
              playerHand.add(card);
              playerScore+=(card.getValue());
              System.out.print("You drew a ");//auto draw first card of the hand so player has 2
              System.out.print(card.getValue());
              System.out.print("!\nYou have ");
              System.out.println(playerHand.toString());
              System.out.println("Your score is "+playerScore);
              if (playerScore<21){//if player score is less than 21
                System.out.print("Would you like to hit or stand? ");//ask if hit or stand
                hitStand = scan.nextLine();
              while (!(hitStand.equalsIgnoreCase("Hit")||hitStand.equalsIgnoreCase("Stand"))){//spelling
                System.out.print("Would you like to hit or stand? ");
                hitStand = scan.nextLine();
              }
              if (hitStand.equalsIgnoreCase("hit")&&playerScore<21){//this is if you want to hit on your first hand
                while (playerScore < 21 && hitStand.equalsIgnoreCase("hit")){
                  card = drawCard();
                  playerHand.add(card);
                  playerScore+=(card.getValue());//all the stuff for the normal hit and stand
                  if (playerScore>21){//check if their score is over 21
                    if (secondCard ==11){//if it is and they have an ace, lower score by 10
                       playerScore-=10;
                      secondCard =0;//onlychecking second card here because the first card is in the other hand
                }else if(card.getValue()==11){
                  playerScore-=10;
                }
              }
                  System.out.println("");
                  System.out.print("You drew a ");
                  System.out.print(card.getValue());
                  System.out.print("!\nYou have ");
                  System.out.println(playerHand.toString());
                  System.out.println("Your score is "+playerScore);
                  if (playerScore < 21){
                    System.out.print("Would you like to hit or stand? ");
                    hitStand = scan.nextLine();
                  while (!(hitStand.equalsIgnoreCase("Hit")||hitStand.equalsIgnoreCase("Stand"))){
                System.out.print("Would you like to hit or stand? ");
                hitStand = scan.nextLine();
              }
                  }
                }
               }
              }
              System.out.println("");
              System.out.println("This is your Second split");//hitstand for the second hand, same as above but with splitHand and splitScore
              card = drawCard();
              splitHand.add(card);//add card to splitHand instead of normal hand
              splitScore+=(card.getValue());//increase splitScore instead of playerScore
              if (splitScore>21){//check if their score is over 21
                if(firstCard ==11){
                  splitScore-=10;//onlychecking first card here because the second card is in the other hand
                  firstCard = 0;
                }else if(card.getValue()==11){
                  splitScore-=10;
                }
              }
              System.out.print("You drew a ");
              System.out.print(card.getValue());
              System.out.print("!\nYou have ");
              System.out.println(splitHand.toString());//printing the split hand and split score
              System.out.println("Your score is "+splitScore);
              if (splitScore < 21){//check if score 21
              System.out.println("");
              System.out.print("Would you like to hit or stand? ");
              hitStand = scan.nextLine();
              while (!(hitStand.equalsIgnoreCase("Hit")||hitStand.equalsIgnoreCase("Stand"))){//checking for spelling
                System.out.print("Would you like to hit or stand? ");
                hitStand = scan.nextLine();
              }
              if (hitStand.equalsIgnoreCase("hit")&&splitScore<21){
                while (splitScore < 21 && hitStand.equalsIgnoreCase("hit")){//This checks if they are able to hit
                  card = drawCard();
                  splitHand.add(card);//drawing cards for the splitHand
                  splitScore+=(card.getValue());
                  System.out.print("You drew a ");
                  System.out.print(card.getValue());
                  System.out.print("!\nYou have ");
                  System.out.println(splitHand.toString());
                  System.out.println("Your score is "+splitScore);
                  System.out.println("");
                  if (splitScore < 21){//dont run if score is over 21
                    System.out.print("Would you like to hit or stand? ");
                    hitStand = scan.nextLine();
                  while (!(hitStand.equalsIgnoreCase("Hit")||hitStand.equalsIgnoreCase("Stand"))){//check if entered wrong
                System.out.print("Would you like to hit or stand? ");
                hitStand = scan.nextLine();
                    }
                  }
                }
              }
            }
              player=0;
              hitStand = "stand";
              break;
            }
            
          }
          //dealer is drawing here
          while (hitStand.equalsIgnoreCase("stand") && (dealerScore < 17 || (dealerScore <= playerScore&&playerScore<21))){//only run if stood, dealer score is less than 17 or the dealer score is less than the player score and the player has not busted
            card = drawCard();
            dealerHand.add(card);
            dealerScore += (card.getValue());
            if (dealerScore>21){//check if dealer has an ace and lower the value
                if (dealerSecondCard ==11){
                  dealerScore-=10;
                  dealerSecondCard =0;
                }else if(dealerFirstCard ==11){
                  dealerScore-=10;
                  dealerFirstCard = 0;
                }else if(card.getValue()==11){
                  dealerScore-=10;
                }
              }
            Thread.sleep(800);//sleep to make game more user friendly
            System.out.print(Yellow+"\nDealer drew a ");
            System.out.print(card.getValue());
            System.out.println("!\nDealer's score is "+ dealerScore);//tell dealer score to user
            System.out.print("Dealer has ");
            System.out.println(dealerHand.toString());
            System.out.print(""+RESET);
            break;
          }
          if(dealerScore>=playerScore){//stops dealer from drawing once they pass player
            break;
          }
  
  
          
        }
        
        Thread.sleep(700);
        if (playerScore > 21 && dealerScore <= 21){//if player busts and dealer does not
          System.out.println(Red+"\nYou have busted. You have lost $"+ bet+RESET);
        }else if (dealerScore > 21 && playerScore <= 21){//if dealer busts and player does not
          coins+=bet*2;
          System.out.println(Green+"\nCongradulations dealer has busted!  You have won $" +bet*2+RESET);
        }else if (playerScore > dealerScore&&(playerScore<=21&&dealerScore<=21)){//if player has higher score and neither have gone over 21
          coins+=bet*2;//payout
          System.out.println(Green+"\nCongradulations you have won! You have won $" +bet*2+RESET);  
        }else if (dealerScore > playerScore&&(playerScore<=21&&dealerScore<=21)){//if dealer wins
          System.out.println(Red+"\nYou lose! You have lost $"+ bet+RESET);
        }else{ //if (playerScore == dealerScore){
          coins+=bet;
          System.out.println(Cyan+"Tie! Your bet has been returned."+RESET);//both go bust or both tie
        }
  
  
  
        if (splitScore > 21&& dealerScore <= 21){//split hand bust
          System.out.println(Red+"Your second hand has busted. You have lost $"+ splitBet+RESET);
        }else if (dealerScore > 21 &&splitScore>0&&splitScore<=21){//split hand win because dealer bust
          coins+=splitBet*2;
          System.out.println(Green+"Congradulations dealer has busted!  Your second split has won $" +splitBet*2+RESET);
        }else if (splitScore > dealerScore&&(splitScore<=21||dealerScore<=21)&&splitScore>0){//split hand win
          coins+=splitBet*2;
          System.out.println(Green+"Congradulations your second split has won! You have won $" +splitBet*2+RESET);  
        }else if (dealerScore > splitScore&&(playerScore<=21||dealerScore<=21)&&splitScore>0){//split hand lost
          System.out.println(Red+"Your second split has lost! You have lost $"+ splitBet+RESET);
        }else if (dealerScore==splitScore||(splitScore>21&&dealerScore>21)){
          coins+=splitBet;
          System.out.println(Cyan+"Your second split has tied"+RESET);//split hand tie
        }
      }

      
      
      
      playerHand.clear();//resetting arrayLists
      dealerHand.clear();
      splitHand.clear();
      playerScore = 0;//resetting scores
      dealerScore = 0;
      splitScore = 0;
      if (coins!=0){//if they have money, ask if want to play again
        System.out.print("Do You Want To Keep Playing (y/n): ");
        playAgain = scan.nextLine();
        while (!(playAgain.equalsIgnoreCase("y")||playAgain.equalsIgnoreCase("n"))){//spelling
          System.out.print("Do You Want To Keep Playing (y/n): ");
          playAgain = scan.nextLine();
        }
      }else if (coins==0){
        System.out.println(Red+"You Lose!");
        playAgain = "n";
      }
      if (playAgain.equalsIgnoreCase("n")&&coins!=0){//if they don't want to play again
        System.out.println("\nPlease play again soon!");
      }
      if (playAgain.equalsIgnoreCase("y")){//if they do want to play again
        System.out.print("\033[H\033[2J");//clear screen
        player = 1;//set player back to 1
      }
      
    }
  }
}

