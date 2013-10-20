package poker;

import java.util.ArrayList;
import java.util.Collections;
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

	public boolean hasTriplets() {
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
		// TODO: FIX UGLY CODE!
		Collections.sort(cards);
		Card removedAce = null;
		if(getHighestCard().getCardValue() == CardValue.ACE) {
			removedAce = cards.remove(cards.size() - 1);
		}
		int lowest = cards.get(0).getCardValue().getValue();
		for(int i = 1; i < cards.size(); i++) {
			int currentValue = cards.get(i).getCardValue().getValue();
			 if(currentValue - lowest != 1)
				 return false;
			 lowest = currentValue;
		}
		if(removedAce != null){
			return cards.get(0).getCardValue() == CardValue.TWO;
		}
 		return true;
	}
}
