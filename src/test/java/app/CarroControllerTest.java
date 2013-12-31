package app;

import org.junit.Before;
import org.junit.Test;

public class CarroControllerTest {
	
	private CarroController carroController;
	private DummyCarroRepository repository;
	
	@Before
	public void before(){
		carroController = new CarroController();
		repository = new DummyCarroRepository();
		
		carroController.carroRepository = repository;
	}

	
	@Test(expected = CarroController.CarroNotFoundException.class)
	public void carroDesconhecido(){
		this.carroController.find(100);
	}
}
