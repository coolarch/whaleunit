package cool.arch.whaleunit.junit4;

/*
 * #%L WhaleUnit - JUnit %% Copyright (C) 2015 CoolArch %% Licensed to the Apache Software
 * Foundation (ASF) under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. #L%
 */

import static java.util.Objects.requireNonNull;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

class ClassStatement extends Statement {

	private final AbstractLifecyleHookRule rule;

	private final Statement base;

	private final Description description;

	ClassStatement(final AbstractLifecyleHookRule rule, final Statement base, final Description description) {
		this.rule = requireNonNull(rule, "rule shall not be null");
		this.base = requireNonNull(base, "base shall not be null");
		this.description = requireNonNull(description, "description shall not be null");
	}

	@Override
	public void evaluate() throws Throwable {
		rule.beforeClass(description.getTestClass());

		try {
			base.evaluate();
		} finally {
			rule.afterClass(description.getTestClass());
		}
	}
}
