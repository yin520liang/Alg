/**
 * 
 */
package simplejava.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @title
 * @description
 */
public class Stream {

	public static void main(String[] args) {
		// 示例1
		List<Circle> circles = new ArrayList<>(Arrays.asList(CircleFactory.create(Circle::new),
				CircleFactory.create(Circle::new), CircleFactory.create(Circle::new)));
		System.out.println(circles);

		List<Circle> blues = circles.stream().filter(c -> c.color == Circle.RGB.RED).collect(Collectors.toList());
		System.out.println(blues);

		// 示例2
		int sum = circles.stream().filter(s -> s.color == Circle.RGB.BLUE).mapToInt(s -> s.radius).sum();
		Optional<Circle> firstBlue = circles.stream()
				.filter(s -> s.color == Circle.RGB.BLUE).findFirst();
	}

}

interface CircleFactory {
	Random rand = new Random(47);

	static Circle create(Supplier<Circle> sup) {
		Circle c = sup.get();
		c.x = rand.nextInt(100);
		c.y = rand.nextInt(100);
		c.radius = rand.nextInt(10);
		Circle.RGB[] rgb = Circle.RGB.values();
		c.color = rgb[rand.nextInt(rgb.length)];
		return c;
	}
}

class Circle {
	int x, y;
	int radius;
	RGB color;

	enum RGB {
		RED, BLUE, YELLOW
	}

	public String toString() {
		return String.format("[%d, %d] - %d - %s", x, y, radius, color.name());
	}
}