package poker;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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
		assertTrue(new Card(CardSuite.HEARTS, CardValue.TWO)
				.compareTo(new Card(CardSuite.HEARTS, CardValue.TWO)) == 0);
	}

	@Test
	public void compareHigherHand() {
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
		Hand hand1 = createHandFrom(CardValue.TWO, CardValue.THREE,
				CardValue.SIX, CardValue.FOUR, CardValue.FIVE);
		Hand hand2 = createHandFrom(CardValue.TWO, CardValue.THREE,
				CardValue.TWO, CardValue.FOUR, CardValue.FIVE);
		assertTrue(hand1.compareTo(hand2) > 0);
	}

	@Test
	public void pair() {
		Hand hand = createHandFrom(CardValue.THREE, CardValue.THREE,
				CardValue.SIX, CardValue.FOUR, CardValue.FIVE);
		assertTrue(hand.hasPair());
	}

	@Test
	public void twoPairs() {
		Hand hand = createHandFrom(CardValue.THREE, CardValue.THREE,
				CardValue.SIX, CardValue.SIX, CardValue.FIVE);
		assertTrue(hand.hasTwoPairs());
		Hand hand2 = createHandFrom(CardValue.FOUR, CardValue.THREE,
				CardValue.THREE, CardValue.FIVE, CardValue.THREE);
		assertFalse(hand2.hasTwoPairs());
	}

	@Test
	public void threeOfAKind() {
		Hand hand = createHandFrom(CardValue.THREE, CardValue.THREE,
				CardValue.THREE, CardValue.SIX, CardValue.FIVE);
		assertTrue(hand.hasThreeOfAKind());
	}

	@Test
	public void fourOfAKind() {
		Hand hand = createHandFrom(CardValue.THREE, CardValue.THREE,
				CardValue.THREE, CardValue.SIX, CardValue.THREE);
		assertTrue(hand.hasFourOfAKind());
	}

	@Test
	public void fullHouse() {
		Hand hand = createHandFrom(CardValue.THREE, CardValue.THREE,
				CardValue.SIX, CardValue.SIX, CardValue.THREE);
		assertTrue(hand.hasFullHouse());
		Hand hand2 = createHandFrom(CardValue.THREE, CardValue.THREE,
				CardValue.FIVE, CardValue.SIX, CardValue.THREE);
		assertFalse(hand2.hasFullHouse());
	}

	@Test
	public void straight() {
		Hand lowestStraightWithoutAce = createHandFrom(CardValue.TWO,
				CardValue.THREE, CardValue.FOUR, CardValue.FIVE, CardValue.SIX);
		assertTrue(lowestStraightWithoutAce.isStraight());
		Hand gay = createHandFrom(CardValue.THREE, CardValue.FOUR,
				CardValue.FIVE, CardValue.SIX, CardValue.EIGHT);
		assertFalse(gay.isStraight());
		Hand lowestStraight = createHandFrom(CardValue.ACE, CardValue.TWO,
				CardValue.THREE, CardValue.FOUR, CardValue.FIVE);
		assertTrue(lowestStraight.isStraight());
		Hand highestStraight = createHandFrom(CardValue.ACE, CardValue.KING,
				CardValue.QUEEN, CardValue.JACK, CardValue.TEN);
		assertTrue(highestStraight.isStraight());
	}

	@Test
	public void flush() {
		Hand hand = createHandFrom(CardValue.THREE, CardValue.THREE,
				CardValue.SIX, CardValue.SIX, CardValue.THREE);
		assertTrue(hand.isFlush());

		Hand hand2 = createHandFrom(CardValue.THREE, CardValue.THREE,
				CardValue.SIX, CardValue.SIX);
		hand2.getCards().add(new Card(CardSuite.SPADES, CardValue.ACE));
		assertFalse(hand2.isFlush());
	}

	@Test
	public void straightFlush() {
		Hand straightFlush = createHandFrom(CardValue.TWO, CardValue.THREE,
				CardValue.FOUR, CardValue.FIVE, CardValue.SIX);
		assertTrue(straightFlush.isStraightFlush());

		Hand flush = createHandFrom(CardValue.TWO, CardValue.THREE,
				CardValue.FOUR, CardValue.FIVE, CardValue.KING);
		assertFalse(flush.isStraightFlush());

		Hand straight = createHandFrom(CardValue.THREE, CardValue.FOUR,
				CardValue.FIVE, CardValue.SIX);
		straight.getCards().add(new Card(CardSuite.SPADES, CardValue.SEVEN));
		assertFalse(straight.isStraightFlush());
	}

	@Test
	public void royalStraightFlush() {
		Hand royalStraightFlush = createHandFrom(CardValue.ACE, CardValue.KING,
				CardValue.QUEEN, CardValue.JACK, CardValue.TEN);
		assertTrue(royalStraightFlush.isRoyalStraightFlush());

		Hand straightFlush = createHandFrom(CardValue.TWO, CardValue.THREE,
				CardValue.FOUR, CardValue.FIVE, CardValue.SIX);
		assertFalse(straightFlush.isRoyalStraightFlush());
	}
	
	@Test
	public void deadMansHand() throws Exception {
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card(CardSuite.CLUBS, CardValue.EIGHT));
		cards.add(new Card(CardSuite.SPADES, CardValue.EIGHT));
		cards.add(new Card(CardSuite.CLUBS, CardValue.ACE));
		cards.add(new Card(CardSuite.SPADES, CardValue.ACE));
		cards.add(new Card(CardSuite.DIAMONDS, CardValue.QUEEN));
		Hand deadMansHand = new Hand(cards);
		assertTrue(deadMansHand.isDeadMansHand());
		
		Hand royalStraightFlush = createHandFrom(CardValue.ACE, CardValue.KING,
				CardValue.QUEEN, CardValue.JACK, CardValue.TEN);
		assertFalse(royalStraightFlush.isDeadMansHand());
	}

	private static Hand createHandFrom(CardValue... values) {
		List<Card> cards = new ArrayList<Card>();
		for (CardValue value : values) {
			cards.add(cardFrom(value));
		}
		fillHand(cards, values[0]);
		return new Hand(cards);
	}

	private static void fillHand(List<Card> cards, CardValue value) {
		while (cards.size() < 5) {
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