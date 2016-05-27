package cool.arch.whaleunit.runtime.model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import cool.arch.whaleunit.annotation.WhaleUnit;
import cool.arch.whaleunit.api.WhaleUnitContext;

/*
 * #%L WhaleUnit - Runtime %% Copyright (C) 2015 CoolArch %% Licensed to the Apache Software
 * Foundation (ASF) under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. #L%
 */

public final class MachineModel {

	private String currentMethod;

	private Alphabet input;

	private final Queue<Alphabet> queue = new LinkedList<>();

	private Object instance;

	private WhaleUnit annotation;

	private WhaleUnitContext whaleUnitContext;

	public Queue<Alphabet> getQueue() {
		return queue;
	}

	private Class<?> testClass;

	private Set<String> globallyDirtiedContainerNames;

	public WhaleUnit getAnnotation() {
		return annotation;
	}

	public void setAnnotation(WhaleUnit annotation) {
		this.annotation = annotation;
	}

	public String getCurrentMethod() {
		return currentMethod;
	}

	public MachineModel setCurrentMethod(String currentMethod) {
		this.currentMethod = currentMethod;

		return this;
	}

	public Alphabet getInput() {
		return input;
	}

	public MachineModel setInput(Alphabet input) {
		this.input = input;

		return this;
	}

	public Class<?> getTestClass() {
		return testClass;
	}

	public MachineModel setTestClass(Class<?> testClass) {
		this.testClass = testClass;

		return this;
	}

	public Set<String> getGloballyDirtiedContainerNames() {
		return globallyDirtiedContainerNames;
	}

	public void setGloballyDirtiedContainerNames(Set<String> globallyDirtiedContainerNames) {
		this.globallyDirtiedContainerNames = globallyDirtiedContainerNames;
	}

	public MachineModel updateInput() {
		input = queue.poll();

		return this;
	}

	public Object getInstance() {
		return instance;
	}

	public void setInstance(Object instance) {
		this.instance = instance;
	}

	public WhaleUnitContext getWhaleUnitContext() {
		return whaleUnitContext;
	}

	public void setWhaleUnitContext(WhaleUnitContext whaleUnitContext) {
		this.whaleUnitContext = whaleUnitContext;
	}
}
