package cool.arch.whaleunit.junit4;

/*
 * #%L WhaleUnit - JUnit Support %% Copyright (C) 2015 CoolArch %% Licensed to the Apache
 * Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under the License.
 * #L%
 */

import java.util.LinkedList;
import java.util.List;

import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

import cool.arch.whaleunit.runtime.WhaleUnitRuntime;

final class PostClassStatement extends Statement {

	private final Statement childStatement;

	private final WhaleUnitRuntime runtime;

	public PostClassStatement(Statement childStatement, final WhaleUnitRuntime runtime) {
		this.childStatement = childStatement;
		this.runtime = runtime;
	}

	@Override
	public void evaluate() throws Throwable {
		List<Throwable> errors = new LinkedList<>();

		try {
			childStatement.evaluate();
		} catch (Throwable e) {
			errors.add(e);
		}

		try {
			runtime.onCleanup();
		} catch (Exception e) {
			errors.add(e);
		}

		if (!errors.isEmpty()) {
			throw (errors.size() == 1)
				? errors.get(0)
				: new MultipleFailureException(errors);
		}
	}
}
