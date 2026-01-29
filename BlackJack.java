//Harrison Tinley
//Jessie Mendez Cruz
//Parminder Singh
//1.29.26
//BlackJack enhcancement: 

import java.util.Random;
import java.util.Scanner;

public class BlackJack {

    private static final String[] SUITS = { "Hearts", "Diamonds", "Clubs", "Spades" };
    private static final String[] RANKS = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King",
            "Ace" };
    private static final int[] DECK = new int[52];
    private static boolean hasAce = false;
    private static int currentCardIndex = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        initializeDeck();
        shuffleDeck();

        int playerTotal = dealInitialPlayerCards();
        int dealerTotal = dealInitialDealerCards();

        playerTotal = playerTurn(scanner, playerTotal);
        if (playerTotal > 21) {
            System.out.println("You busted! Dealer wins.");
            return;
        }

        dealerTotal = dealerTurn(dealerTotal);
        determineWinner(playerTotal, dealerTotal);

        scanner.close();
    }

    //
    private static void initializeDeck() {
        for (int i = 0; i < DECK.length; i++) {
            DECK[i] = i;
        }
    }

    //Swaps values in array in order to simulate shuffling
    // Harrison Tinley
    private static void shuffleDeck() {
        Random random = new Random();
        for (int i = 0; i < DECK.length; i++) {
            int index = random.nextInt(DECK.length);
            int temp = DECK[i];
            DECK[i] = DECK[index];
            DECK[index] = temp;
        }
        //Looks a little messy, so not printing the deck can make the 
        // game more challenging and clean. Jessie Mendez Cruz
        /*
        System.out.println("printed deck");
        for (int i = 0; i < DECK.length; i++) {
            System.out.println(DECK[i] + " ");
        }
        */ 
    }

    private static int dealInitialPlayerCards() {
        int card1 = dealCard();
        int card2 = dealCard();
        System.out.println("Your cards: " + RANKS[card1] + " of " + SUITS[DECK[currentCardIndex] % 4] + " and "
                + RANKS[card2] + " of " + SUITS[card2 / 13]);
        return cardValue(card1) + cardValue(card2);
    }

    private static int dealInitialDealerCards() {
        int card1 = dealCard();
        System.out.println("Dealer's card: " + RANKS[card1] + " of " + SUITS[DECK[currentCardIndex] % 4]);
        return cardValue(card1);
    }

    //Keeps adding new cards to player's total until they decide to "Stand"
    //Harrison Tinley
    private static int playerTurn(Scanner scanner, int playerTotal) {
        while (true) {
            System.out.println("Your total is " + playerTotal + ". Do you want to hit or stand?");
            String action = scanner.nextLine().toLowerCase();
            if (action.equals("hit")) {
                int newCard = dealCard();
                playerTotal += cardValue(newCard);
                //Not useful for player to see
                // Jessie Mendez Cruz
                //System.out.println("new card index is " + newCard);
                System.out.println("You drew a " + RANKS[newCard] + " of " + SUITS[DECK[currentCardIndex] % 4]);
                if (playerTotal > 21) {
                    //Checks if player has an Ace and saves them from losing if so
                    //Harrison Tinley
                    if (hasAce == true) {
                        playerTotal -= 10;
                    }
                    else {
                        break;
                    }
                }
            } else if (action.equals("stand")) {
                break;
            } else {
                System.out.println("Invalid action. Please type 'hit' or 'stand'.");
            }
        }
        return playerTotal;
    }

    // the dealer has to hit until 17
    // is used when the player stands
    // Parminder Singh
    private static int dealerTurn(int dealerTotal) {
        while (dealerTotal < 17) {
            int newCard = dealCard();
            dealerTotal += cardValue(newCard);
        }
        System.out.println("Dealer's total is " + dealerTotal);
        return dealerTotal;
    }

    private static void determineWinner(int playerTotal, int dealerTotal) {
        if (dealerTotal > 21 || playerTotal > dealerTotal) {
            System.out.println("You win!");
        } else if (dealerTotal == playerTotal) {
            System.out.println("It's a tie!");
        } else {
            System.out.println("Dealer wins!");
        }
    }

    //Returns a 
    private static int dealCard() {
        return DECK[currentCardIndex++] % 13;
    }

    
    //Converts array values into what the card values actually are
    // Jessie Mendez Cruz && Harrison Tinley
    private static int cardValue(int card) {
        //Rewritten in order to be more human readable
        //Harrison Tinley
        if (card < 9) {
            return card += 2;
        }
        //adds a checker to see if the card is an Ace and then adds 11 to total
        //Harrison Tinley
        else if (card == 13) {
            hasAce = true;
            //Add a notifier so that the player knows the ace is in the game
            // and how it works.
            //Jessie Mendez Cruz
            System.out.println("You have an Ace! If you get over 21, it will be converted from 11 to 1.");
            return 11;
        }
        else {
            return 10;
        }
       //return card < 9 ? card + 2 : 10;
    }

    //Removed, seems to be added for fluff
    // and isn't really useful
    // Parminder Singh

    /* 
    int linearSearch(int[] numbers, int key) {
        int i = 0;
        for (i = 0; i < numbers.length; i++) {
            if (numbers[i] == key) {
                return i;
            }
        }
        return -1; // not found
    }
    */
}
