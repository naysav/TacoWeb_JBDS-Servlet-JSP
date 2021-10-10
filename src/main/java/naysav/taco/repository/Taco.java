package naysav.taco.repository;

// класс Taco хранит информацию о тако,
// созданым пользователем, для записи и чтения из БД TACOS
public class Taco {
	// user хранит логин пользователя, создавшего этот тако,
	// для последующего определения состава корзины этого пользователя
	private String user;

	// id хранит номер собранного тако (для отображения всех тако в том порядке,
	// в котором пользовтель их собирал)
	private int id;

	// класс flapjack хранит название ингредиента "тортилья" (испанская лепешка), его код в БД PRODUCT и цену
	private Product flapjack;

	// класс chicken хранит название ингредиента "курица", его код в БД PRODUCT и цену
	private Product chicken;

	// класс garlic хранит название ингредиента "чеснок", его код в БД PRODUCT и цену
	private Product garlic;

	// класс onion хранит название ингредиента "лук", его код в БД PRODUCT и цену
	private Product onion;

	// класс tomato хранит название ингредиента "помидоры", его код в БД PRODUCT и цену
	private Product tomato;

	// класс haricot хранит название ингредиента "фасоль", его код в БД PRODUCT и цену
	private Product haricot;

	// класс cheese хранит название ингредиента "сыр", его код в БД PRODUCT и цену
	private Product cheese;

	// класс avocado хранит название ингредиента "авокадо", его код в БД PRODUCT и цену
	private Product avocado;

	// totalPrice хранит итоговую цену за весь тако
	// с учетом стоимости каждого ингредиента
	private float totalPrice;

	public Taco() {

	}

	public Taco(String user, Product fl, Product chi, Product garl, Product onn,
	            Product tmt, Product hrct, Product che, Product avcd) {
		this.user = user;
		this.flapjack = (fl == null) ? new Product("P001", "", 0) : fl;
		this.chicken = (chi == null) ? new Product("P002", "", 0) : chi;
		this.garlic = (garl == null) ? new Product("P003", "", 0) : garl;
		this.onion = (onn == null) ? new Product("P004", "", 0) : onn;
		this.tomato = (tmt == null) ? new Product("P005", "", 0) : tmt;
		this.haricot = (hrct == null) ? new Product("P006", "", 0) : hrct;
		this.cheese = (che == null) ? new Product("P007", "", 0) : che;
		this.avocado = (avcd == null) ? new Product("P008", "", 0) : avcd;
		this.totalPrice = this.flapjack.getPrice() + this.chicken.getPrice() + this.garlic.getPrice()
				+ this.onion.getPrice() + this.tomato.getPrice()+ this.haricot.getPrice()
				+ this.cheese.getPrice() + this.avocado.getPrice();
	}

	public Taco(int id, String fl, String chi, String garl, String onn,
	            String tmt, String hrct, String che, String avcd, float totalPrice) {
		this.id = id;
		this.flapjack = new Product("P001", fl, 0);
		this.chicken = new Product("P002", chi, 0);
		this.garlic = new Product("P003", garl, 0);
		this.onion = new Product("P004", onn, 0);
		this.tomato = new Product("P005", tmt, 0);
		this.haricot = new Product("P006", hrct, 0);
		this.cheese = new Product("P007", che, 0);
		this.avocado = new Product("P008", avcd, 0);
		this.totalPrice = totalPrice;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getId() { return id; }

	public void setId(int id) {	this.id = id; }

	public Product getFlapjack() {
		return flapjack;
	}

	public void setFlapjack(Product flapjack) {
		this.flapjack = flapjack;
	}

	public Product getChicken() {
		return chicken;
	}

	public void setChicken(Product chicken) {
		this.chicken = chicken;
	}

	public Product getGarlic() {
		return garlic;
	}

	public void setGarlic(Product garlic) {
		this.garlic = garlic;
	}

	public Product getOnion() {
		return onion;
	}

	public void setOnion(Product onion) {
		this.onion = onion;
	}

	public Product getTomato() {
		return tomato;
	}

	public void setTomato(Product tomato) {
		this.tomato = tomato;
	}

	public Product getHaricot() {
		return haricot;
	}

	public void setHaricot(Product haricot) {
		this.haricot = haricot;
	}

	public Product getCheese() {
		return cheese;
	}

	public void setCheese(Product cheese) {
		this.cheese = cheese;
	}

	public Product getAvocado() {
		return avocado;
	}

	public void setAvocado(Product avocado) {
		this.avocado = avocado;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	// считает общую стоимость тако с учетом выбранных игредиентов
	public void countTotalPrice(float totalPrice) {
		this.totalPrice = flapjack.getPrice() + chicken.getPrice() + garlic.getPrice()
				+ onion.getPrice() + tomato.getPrice()+ haricot.getPrice() + cheese.getPrice() + avocado.getPrice();
	}
}
