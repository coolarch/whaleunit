package cool.arch.whaleunit.junit.docker;

public class ContainerImpl implements Container {
	
	private final String name;
	
	public ContainerImpl(final String name) {
		this.name = name;
	}
	
	@Override
	public void create() {
		System.out.println("create: " + name);
	}

	@Override
	public void start() {
		System.out.println("start: " + name);
	}

	@Override
	public void stop() {
		System.out.println("stop: " + name);
	}

	@Override
	public void destroy() {
		System.out.println("destroy: " + name);
	}

	@Override
	public String getName() {
		return name;
	}
}
