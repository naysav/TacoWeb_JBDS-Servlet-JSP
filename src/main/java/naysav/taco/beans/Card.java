package naysav.taco.beans;

public class Card {
	private String number;
	private String name;
	private String data;

	public Card(String number, String name, String data) {
		this.number = number;
		this.name = name;
		this.data = data;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
