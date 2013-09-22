package poker;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.Suite;

import poker.CardValue;

public class CardTest {

	@Test
	public void cardIsHigher() {
		assertTrue(cardFrom(CardValue.ACE).compareTo(
				new Card(CardSuite.HEARTS, CardValue.TWO)) > 0);
	}

	@Test
	public void cardIsLower() {
		assertTrue(new Card(CardSuite.HEARTS, CardValue.TWO)
				.compareTo(cardFrom(CardValue.ACE)) < 0);
	}

	@Test
	public void cardsAreEqual() {
		assertTrue(new Card(CardSuite.HEARTS, CardValue.TWO).compareTo(new Card(
				CardSuite.HEARTS, CardValue.TWO)) == 0);
	}

	@Test
	public void compareHigherHand() throws Exception {
		Hand hand1 = createHandFrom(CardValue.ACE);
		Hand hand2 = createHandFrom(CardValue.KING);
		assertTrue(hand1.compareTo(hand2) > 0);
	}

	@Test
	public void compareLowerHand() {
		Hand hand1 = createHandFrom(CardValue.KING);
		Hand hand2 = createHandFrom(CardValue.ACE);
		assertTrue(hand1.compareTo(hand2) < 0);
	}
	
	@Test
	public void compareEqualHand() {
		Hand hand1 = createHandFrom(CardValue.KING);
		Hand hand2 = createHandFrom(CardValue.KING);
		assertTrue(hand1.compareTo(hand2) == 0);
	}
	
	@Test
	public void compareHigherHandTwoCards() {
		Hand hand1 = createHandFrom(CardValue.TWO, CardValue.JACK);
		Hand hand2 = createHandFrom(CardValue.THREE, CardValue.NINE);
		assertTrue(hand1.compareTo(hand2) > 0);
	}
	
	@Test
	public void compareHigherHandFiveCards() {
		Hand hand1 = createHandFrom(CardValue.TWO, CardValue.THREE, CardValue.SIX, CardValue.FOUR, CardValue.FIVE);
		Hand hand2 = createHandFrom(CardValue.TWO, CardValue.THREE, CardValue.TWO, CardValue.FOUR, CardValue.FIVE);
		assertTrue(hand1.compareTo(hand2) > 0);
	}
	
	@Test
	public void getPairFromHand() {
		Hand hand = createHandFrom(CardValue.THREE, CardValue.THREE, CardValue.SIX, CardValue.FOUR, CardValue.FIVE);
		assertTrue(hand.hasPair(hand));
	}
	
	@Test
	public void getTwoPairsFromHand() {
		Hand hand = createHandFrom(CardValue.THREE, CardValue.THREE, CardValue.SIX, CardValue.SIX, CardValue.FIVE);
		assertTrue(hand.hasTwoPairs(hand));
	}
	
	@Test
	public void getTripletsFromHand() {
		Hand hand = createHandFrom(CardValue.THREE, CardValue.THREE, CardValue.THREE, CardValue.SIX, CardValue.FIVE);
		assertTrue(hand.hasTriplets(hand));
	}
	
	
	private static Hand createHandFrom(CardValue... values) {
		List<Card> cards = new ArrayList<Card>();
		for(CardValue value : values) {
			cards.add(cardFrom(value));
		}
		fillHand(cards, values[0]);
		return new Hand(cards);
	}

	private static void fillHand(List<Card> cards, CardValue value) {
		while(cards.size() < 5) {
			cards.add(cardFrom(value));
		}
	}

	private Hand createHandFrom(CardValue value) {
		List<Card> cards = new ArrayList<Card>();
		fillHand(cards, value);
		return new Hand(cards);
	}

	private static Card cardFrom(CardValue value) {
		return new Card(CardSuite.HEARTS, value);
	}
}