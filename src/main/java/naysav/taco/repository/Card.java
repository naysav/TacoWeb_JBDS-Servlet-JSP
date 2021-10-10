package naysav.taco.repository;

// класс Card хранит информацию о банковской карте пользователя,
// с которой производится оплата, для ее отображения в сообщении
// об успешной оплате
public class Card {
	// number хранит номер карты пользователя
	private String number;

	// name хранит имя владельца карты
	private String name;

	// data хранит срок действия карты
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
