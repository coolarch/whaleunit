package cool.arch.whaleunit.support.validation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.stream.Collector;

import cool.arch.whaleunit.support.patterns.AbstractBuilder;
import cool.arch.whaleunit.support.patterns.AbstractBuilderImpl;

public final class SequentialValidator<T> {

	private final List<BiPredicate<T, Consumer<String>>> predicates = new LinkedList<>();

	public <R> R validate(final T model, final Collector<? super CharSequence, ?, R> collector) {
		final List<String> errors = new LinkedList<>();

		final Iterator<BiPredicate<T, Consumer<String>>> iterator = predicates.iterator();

		boolean active = true;

		while (active && iterator.hasNext()) {
			active = iterator.next().test(model, errors::add);
		}

		return errors.stream()
			.collect(collector);
	}

	public static <T> Builder<T> builder() {
		return new BuilderImpl<>();
	}

	public interface Builder<T> extends AbstractBuilder<SequentialValidator<T>> {

		Builder<T> withValidator(BiPredicate<T, Consumer<String>> predicate);

	}

	static class BuilderImpl<T> extends AbstractBuilderImpl<SequentialValidator<T>> implements Builder<T> {

		protected BuilderImpl() {
			super(new SequentialValidator<>());
		}

		@Override
		protected Set<String> validate(final SequentialValidator<T> instance) {
			return new HashSet<>();
		}

		@Override
		public Builder<T> withValidator(final BiPredicate<T, Consumer<String>> predicate) {
			if (predicate != null) {
				getInstance().predicates.add(predicate);
			}

			return this;
		}
	}
}
