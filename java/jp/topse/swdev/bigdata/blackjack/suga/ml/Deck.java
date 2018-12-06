package jp.topse.swdev.bigdata.blackjack.suga.ml;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import jp.topse.swdev.bigdata.blackjack.Card;

/**
 * Created by doi on 2017/09/27.
 */
public class Deck {

    public static Deck createDefault() {
        Map<Card, Integer> map = new HashMap<Card, Integer>();
        map.put(Card.TWO,   4);
        map.put(Card.THREE, 4);
        map.put(Card.FOUR,  4);
        map.put(Card.FIVE,  4);
        map.put(Card.SIX,   4);
        map.put(Card.SEVEN, 4);
        map.put(Card.EIGHT, 4);
        map.put(Card.NINE,  4);
        map.put(Card.TEN,   4);
        map.put(Card.JACK,  4);
        map.put(Card.QUEEN, 4);
        map.put(Card.KING,  4);
        map.put(Card.ACE,   4);
        return new Deck(map);
    }

    public static Deck createQLearningDeck() {
        Map<Card, Integer> map = new HashMap<Card, Integer>();
        map.put(Card.TWO,   10);
        map.put(Card.THREE, 10);
        map.put(Card.FOUR,  10);
        map.put(Card.FIVE,  10);
        map.put(Card.SIX,   10);
        map.put(Card.SEVEN, 10);
        map.put(Card.EIGHT, 10);
        map.put(Card.NINE,  10);
        map.put(Card.TEN,   10);
        map.put(Card.JACK,  10);
        map.put(Card.QUEEN, 9);
        map.put(Card.KING,  7);
        map.put(Card.ACE,   7);
        return new Deck(map);
    }
    
    public static Deck createTest3Deck() {
        Map<Card, Integer> map = new HashMap<Card, Integer>();
        map.put(Card.TWO,   10);
        map.put(Card.THREE, 1);
        map.put(Card.FOUR,  1);
        map.put(Card.FIVE,  1);
        map.put(Card.SIX,   10);
        map.put(Card.SEVEN, 1);
        map.put(Card.EIGHT, 1);
        map.put(Card.NINE,  1);
        map.put(Card.TEN,   10);
        map.put(Card.JACK,  1);
        map.put(Card.QUEEN, 1);
        map.put(Card.KING,  5);
        map.put(Card.ACE,   5);
        return new Deck(map);
    }
    
    public interface RandomGenerator {
        public int nextInt(int bound);
    }

    private static class DefaultRandomGenerator implements RandomGenerator {

        private final Random randomGenerator = new Random();

        @Override
        public int nextInt(int bound) {
            return randomGenerator.nextInt(bound);
        }
    }

    private RandomGenerator randomGenerator = new DefaultRandomGenerator();

    private Map<Card, Integer> numberOfCards;
    private Map<Card, Integer> remains;

    public Deck(Map<Card, Integer> numberOfCards) {
        this.numberOfCards = numberOfCards;
        reset();
    }

    public void reset() {
        this.remains = new HashMap<Card, Integer>();
        numberOfCards.forEach((key, value) -> {
            if (value > 0) {
                this.remains.put(key, value.intValue());
            }
        });
    }

    public Card nextCard() {
        if (remains.size() == 0) {
            throw new RuntimeException("Deck is empty");
        }
        do {
            int index = randomGenerator.nextInt(Card.numberOfTypes()) + 1;
            Card card = Card.indexOf(index);
            if (remains.containsKey(card)) {
                int count = remains.get(card);
                if (count == 1) {
                    remains.remove(card);
                } else {
                    remains.put(card, count - 1);
                }
                return card;
            }
        } while (true);
    }
}
