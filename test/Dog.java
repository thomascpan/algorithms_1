public class Dog extends Animal {
	public Dog(int age) {
		super(age);
		System.out.println("Dog created!");
	}

	public void makeNoise() {
		System.out.println("Bark!");
	}

	public void examine(Object that) {
		System.out.println("this.getClass() = " + this.getClass());
		System.out.println("that.getClass() = " + that.getClass());

		Animal nThat = (Animal) that;
		System.out.println(this.age);
		System.out.println(nThat.age);
	}

	public static void main(String[] args) {
		Dog dog = new Dog(5);
		dog.makeNoise();
		System.out.println(dog.age);

		Animal animal = new Dog(10);
		animal.makeNoise();
		System.out.println(animal.age);

		dog.examine(animal);
	}
}