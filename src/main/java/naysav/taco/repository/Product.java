package naysav.taco.repository;

// класс Product хранит информацию об ингредиенте тако
// для чтения из БД PRODUCT и записи в класс Taco
public class Product {
	// code хранит код продукта, по которому происходит его поиск в БД PRODUCT
	private String code;

	// name хранит наименование продукта, под которым он записан в БД PRODUCT
	private String name;

	// price хранит стоимость продукта, определенную в БД PRODUCT
	private float price;

	public Product() {

	}

	public Product(String code, String name, float price) {
		this.code = code;
		this.name = name;
		this.price = price;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
