package cars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CarServiceImpl implements CarService {

	private List<Car> cars = new ArrayList<>();
	
	@Override
	public List<Car> getCars() {
		return cars;
	}
	
	@Override
	public void createCar(Car car) {
		cars.add(car);
	}
	
	public CarServiceImpl() {
		cars.addAll(Arrays.asList(new Car("A00001", "m01", "blue", "t01", "2017"),
				new Car("A00002", "m02", "black", "t01", "2017"),
				new Car("A00003", "m01", "white", "t01", "2017"),
				new Car("A00004", "m02", "red", "t01", "2017")));
	}
}
