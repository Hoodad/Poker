package poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

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

	public boolean hasPair(Hand hand) {
		return hasPair(hand,1);
	}	
	
	public boolean hasTwoPairs(Hand hand) {
		return hasPair(hand,2);
	}	

	private List<CardValue> getValues(List<Card> list) {
		List<CardValue> cardValues = new ArrayList<CardValue>();
		for (Card card : list) {
			cardValues.add(card.getValue());
		}
		return cardValues;
	}
	
	public boolean hasPair(Hand hand, int numberOfEquals) {
		HashSet<CardValue> hashSet = new HashSet<>(getValues(hand.getCards()));
		return hashSet.size() == hand.getCards().size() - numberOfEquals;
	}

	public boolean hasTriplets(Hand hand) {
		for (Card card : cards) {
			ArrayList<Card> copy = new ArrayList<Card>(cards);
			ArrayList<Card> uniqueCard = new ArrayList<Card>();
			uniqueCard.add(card);
			copy.retainAll(uniqueCard);
			if(copy.size() == 3)
				return true;
		}
		return false;
	}
}
