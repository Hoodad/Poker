package poker;

public class Card implements Comparable<Card> {
	private CardSuite suite;
	private CardValue value;
	
	public Card(CardSuite suite, CardValue value) {
		this.suite = suite;
		this.value = value;
	}
	
	public CardSuite getSuite() {
		return suite;
	}
	
	public CardValue getCardValue() {
		return value;
	}
	
	@Override
	public int compareTo(Card other) {
		return value.getValue() - other.getCardValue().getValue();
	}

	@Override
	public String toString() {
		return "Card [suite=" + suite + ", value=" + value + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suite == null) ? 0 : suite.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (suite != other.suite)
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	 
}
