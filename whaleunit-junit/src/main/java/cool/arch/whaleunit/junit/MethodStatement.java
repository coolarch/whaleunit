package cool.arch.whaleunit.junit;

/*
 * #%L
 * WhaleUnit - JUnit
 * %%
 * Copyright (C) 2015 CoolArch
 * %%
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * #L%
 */

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

class MethodStatement extends Statement {
	
	private final AbstractLifecyleHookRule rule;
	
	private final Statement base;
	
	private final Description description;
	
	MethodStatement(final AbstractLifecyleHookRule rule, final Statement base, final Description description) {
		this.rule = requireNonNull(rule, "rule shall not be null");
		this.base = requireNonNull(base, "base shall not be null");
		this.description = requireNonNull(description, "description shall not be null");
	}
	
	@Override
	public void evaluate() throws Throwable {
		final List<Throwable> errors = new ArrayList<Throwable>();
		
		startingQuietly(description, errors);
		
		try {
			base.evaluate();
			succeededQuietly(description, errors);
		} catch (final org.junit.internal.AssumptionViolatedException e) {
			errors.add(e);
			skippedQuietly(e, description, errors);
		} catch (final Throwable e) {
			errors.add(e);
			failedQuietly(e, description, errors);
		} finally {
			finishedQuietly(description, errors);
		}
		
		MultipleFailureException.assertEmpty(errors);
	}
	
	private void succeededQuietly(final Description description, final List<Throwable> errors) {
		try {
			rule.succeeded(description.getTestClass(), description.getMethodName());
		} catch (final Throwable e) {
			errors.add(e);
		}
	}
	
	private void failedQuietly(final Throwable e, final Description description, final List<Throwable> errors) {
		try {
			rule.failed(description.getTestClass(), description.getMethodName(), e);
		} catch (final Throwable e1) {
			errors.add(e1);
		}
	}
	
	private void skippedQuietly(final org.junit.internal.AssumptionViolatedException e, final Description description, final List<Throwable> errors) {
		try {
			rule.skipped(description.getTestClass(), description.getMethodName());
		} catch (final Throwable e1) {
			errors.add(e1);
		}
	}
	
	private void startingQuietly(final Description description, final List<Throwable> errors) {
		try {
			rule.starting(description.getTestClass(), description.getMethodName());
		} catch (final Throwable e) {
			errors.add(e);
		}
	}
	
	private void finishedQuietly(final Description description, final List<Throwable> errors) {
		try {
			rule.finished(description.getTestClass(), description.getMethodName());
		} catch (final Throwable e) {
			errors.add(e);
		}
	}
}
