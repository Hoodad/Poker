package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hand implements Comparable<Hand> {
	private List<Card> cards;

	public Hand(List<Card> cards) {
		this.cards = cards;
	}

	public List<Card> getCards() {
		return cards;
	}

	@Override
	public int compareTo(Hand other) {
		Card otherHighestCard = other.getHighestCard();
		return getHighestCard().compareTo(otherHighestCard);
	}

	private Card getHighestCard() {
		Card highestCard = cards.get(0);
		for (Card card : cards) {
			if (card.compareTo(highestCard) > 0) {
				highestCard = card;
			}
		}
		return highestCard;
	}

	public boolean hasPair() {
		return ofAKind(2, cards);
	}

	public boolean hasTwoPairs() {
		ArrayList<Card> aPair = hasOfAKind(cards, 2);
		if (aPair.size() == 2) {
			return ofAKind(2, getRemainder(aPair));
		}
		return false;
	}

	public boolean hasThreeOfAKind() {
		return ofAKind(3, cards);
	}

	public boolean hasFourOfAKind() {
		return ofAKind(4, cards);
	}

	public boolean hasFullHouse() {
		ArrayList<Card> tripplets = hasOfAKind(cards, 3);
		if (tripplets.size() == 3) {
			ArrayList<Card> hand = getRemainder(tripplets);
			return ofAKind(2, hand);
		}
		return false;
	}

	private boolean ofAKind(int nrOf, List<Card> hand) {
		return hasOfAKind(hand, nrOf).size() == nrOf;
	}

	private ArrayList<Card> getRemainder(ArrayList<Card> tripplets) {
		ArrayList<Card> hand = new ArrayList<Card>(cards);
		hand.removeAll(tripplets);
		return hand;
	}

	private ArrayList<Card> hasOfAKind(List<Card> cards, int nrOf) {
		for (Card card : cards) {
			ArrayList<Card> copy = new ArrayList<Card>(cards);
			ArrayList<Card> uniqueCard = new ArrayList<Card>();
			uniqueCard.add(card);
			copy.retainAll(uniqueCard);
			if (copy.size() == nrOf)
				return copy;
		}
		return new ArrayList<Card>();
	}

	public boolean isStraight() {
		//TODO: Fix ugly code!
		List<Card> cards = new ArrayList<Card>(this.cards);
		Collections.sort(cards);
		boolean aligned = true;
		int lowestValue = cards.get(0).getCardValue().getValue()-1;
		for(Card card: cards){
			int currentValue = card.getCardValue().getValue();
			if( currentValue - lowestValue != 1){
				aligned = false;
			}
			lowestValue = currentValue;
		}
		if(!aligned){
			if(cards.get(0).getCardValue() == CardValue.TWO && cards.get(4).getCardValue() == CardValue.ACE){
				return true;
			}
			return false;
		}
		/*
		Card removedAce = null;
		if (getHighestCard().getCardValue() == CardValue.ACE) {
			removedAce = cards.remove(cards.size() - 1);
		}
		int lowest = cards.get(0).getCardValue().getValue();
		for (int i = 1; i < cards.size(); i++) {
			int currentValue = cards.get(i).getCardValue().getValue();
			if (currentValue - lowest != 1) {
				return false;
			}
			lowest = currentValue;
		}
		if (removedAce != null) {
			return cards.get(0).getCardValue() == CardValue.TWO
					|| cards.get(3).getCardValue() == CardValue.KING;
		}
		*/
		return true;
	}

	public boolean isFlush() {
		CardSuite suite = cards.get(0).getSuite();
		for (Card card : cards) {
			if (suite != card.getSuite()) {
				return false;
			}
		}
		return true;
	}

	public boolean isStraightFlush() {
		return isFlush() && isStraight();
	}

	public boolean isRoyalStraightFlush() {
		return isStraightFlush() && toSet(cards).contains(CardValue.KING);
	}

	public static Set<CardValue> toSet(List<Card> cards) {
		Set<CardValue> set = new HashSet<CardValue>();
		for (Card card : cards) {
			set.add(card.getCardValue());
		}
		return set;
	}

	public boolean isDeadMansHand() {
		return cards.contains( new Card(CardSuite.CLUBS,  CardValue.EIGHT)) &&
				cards.contains(new Card(CardSuite.SPADES, CardValue.EIGHT)) &&
				cards.contains(new Card(CardSuite.SPADES, CardValue.ACE)) &&
				cards.contains(new Card(CardSuite.CLUBS,  CardValue.ACE));
	}
}
