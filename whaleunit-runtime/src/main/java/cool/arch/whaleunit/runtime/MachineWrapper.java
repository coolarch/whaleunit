package cool.arch.whaleunit.runtime;

import static cool.arch.whaleunit.runtime.States.CREATED;
import static cool.arch.whaleunit.runtime.States.DECOMISSIONED;
import static cool.arch.whaleunit.runtime.States.ENDED;
import static cool.arch.whaleunit.runtime.States.FAILED;
import static cool.arch.whaleunit.runtime.States.INITIALIZED;
import static cool.arch.whaleunit.runtime.States.STARTED;
import static cool.arch.whaleunit.runtime.States.SUCCEEDED;
import static cool.arch.whaleunit.runtime.model.Alphabet.CLEAN_UP;
import static cool.arch.whaleunit.runtime.model.Alphabet.END;
import static cool.arch.whaleunit.runtime.model.Alphabet.FAILURE;
import static cool.arch.whaleunit.runtime.model.Alphabet.INIT;
import static cool.arch.whaleunit.runtime.model.Alphabet.START;
import static cool.arch.whaleunit.runtime.model.Alphabet.SUCCESS;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;

import javax.inject.Inject;

import cool.arch.stateroom.Context;
import cool.arch.stateroom.Machine;
import cool.arch.whaleunit.runtime.api.Containers;
import cool.arch.whaleunit.runtime.model.Alphabet;
import cool.arch.whaleunit.runtime.model.MachineModel;
import cool.arch.whaleunit.runtime.transform.DecommissionedModelTransformBiFunction;
import cool.arch.whaleunit.runtime.transform.EndModelTransformBiFunction;
import cool.arch.whaleunit.runtime.transform.FailedModelTransformBiFunction;
import cool.arch.whaleunit.runtime.transform.InitializedModelTransformBiFunction;

public class MachineWrapper {

	private final Queue<Alphabet> input = new LinkedList<>();

	private final Machine<MachineModel> machine;

	private Context<MachineModel> context;

	@Inject
	public MachineWrapper(final InitializedModelTransformBiFunction initializedModelTransformBiFunction, final DecommissionedModelTransformBiFunction decommissionedModelTransformBiFunction, final EndModelTransformBiFunction endModelTransformBiFunction, final FailedModelTransformBiFunction failedModelTransformBiFunction, final Containers containers) {
		machine = Machine.builder(MachineModel.class)
			.withStartState(CREATED)
			.withPreEvaluationTransform((state, model) -> model.setInput(input.poll()))
			.haltWhen((state, model) -> model == null || CLEAN_UP.equals(model))
			.withModelSupplier(() -> new MachineModel().setInput(input.poll()))
			.withState(CREATED)
			.to(INITIALIZED, is(INIT), initializedModelTransformBiFunction)
			.withState(INITIALIZED)
			.to(STARTED, is(START), (start, model) -> {
				containers.startAll();

				return model;
			})
			.withState(STARTED)
			.to(SUCCEEDED, is(SUCCESS))
			.to(FAILED, is(FAILURE), failedModelTransformBiFunction)
			.withState(SUCCEEDED)
			.to(ENDED, is(END))
			.withState(FAILED)
			.to(ENDED, is(END), endModelTransformBiFunction)
			.withState(ENDED)
			.to(DECOMISSIONED, is(CLEAN_UP), decommissionedModelTransformBiFunction)
			.build();

		context = machine.create();

	}
	
	public Context<MachineModel> getContext() {
		return context;
	}
	
	public void evaluate() {
		while (!input.isEmpty()) {
			context = machine.evaluate(context);
		}
	}
	
	public void submit(final Alphabet letter) {
		input.add(letter);
	}

	private Predicate<Context<MachineModel>> is(final Alphabet value) {
		return c -> value.equals(c.getModel()
			.getInput());
	}
}
