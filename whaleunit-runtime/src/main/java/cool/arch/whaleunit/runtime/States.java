package cool.arch.whaleunit.runtime;

import cool.arch.stateroom.State;
import cool.arch.whaleunit.runtime.model.MachineModel;

public class States {

	public static final State<MachineModel> CREATED = State.of("Created");

	public static final State<MachineModel> INITIALIZED = State.of("Initialized");

	public static final State<MachineModel> STARTED = State.of("Started");

	public static final State<MachineModel> ENDED = State.of("Ended");

	public static final State<MachineModel> FAILED = State.of("Failed");

	public static final State<MachineModel> SUCCEEDED = State.of("Succeeded");

	public static final State<MachineModel> DECOMISSIONED = State.of("Decomissioned", true);

}
