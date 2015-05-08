package cool.arch.whaleunit.runtime.impl;

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

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;

import cool.arch.whaleunit.annotation.Logger;
import cool.arch.whaleunit.annotation.LoggerAdapterFactory;
import cool.arch.whaleunit.runtime.api.Container;
import cool.arch.whaleunit.runtime.api.Containers;

@Service
public class ContainersImpl implements Containers {

	private final Map<String, Container> containers = new HashMap<>();

	private final Logger loggerAdapter;

	@Inject
	public ContainersImpl(final LoggerAdapterFactory factory) {
		loggerAdapter = factory.create(getClass());
	}

	@Override
	public void add(final Container container) {
		requireNonNull(container, "container shall not be null");
		containers.put(container.getId(), container);
	}

	@Override
	public void createAll() {
		onAll("creating", Container::create);
	}

	@Override
	public void destroyAll() {
		onAll("destroying", Container::destroy);
	}

	@Override
	public boolean exists(final String name) {
		return containers.containsKey(name);
	}

	@Override
	public void restart(final Collection<String> names) {
		onEach("restarting", Container::restart, names);
	}

	@Override
	public void restart(final String... names) {
		onEach("restarting", Container::restart, names);
	}

	@Override
	public void restartAll() {
		onAll("restarting", Container::restart);
	}

	@Override
	public void start(final Collection<String> names) {
		onEach("starting", Container::start, names);
	}

	@Override
	public void start(final String... names) {
		onEach("starting", Container::start, names);
	}

	@Override
	public void startAll() {
		onAll("starting", Container::start);
	}

	@Override
	public void stop(final Collection<String> names) {
		onEach("stopping", Container::stop, names);
	}

	@Override
	public void stop(final String... names) {
		onEach("stopping", Container::stop, names);
	}

	@Override
	public void stopAll() {
		onAll("stopping", Container::stop);
	}

	private void onAll(final String actionName, final Consumer<Container> consumer) {
		containers.values()
			.stream()
			.peek(container -> loggerAdapter.debug(actionName + ": " + container.getId()))
			.forEach(consumer);
	}

	private void onEach(final String actionName, final Consumer<Container> consumer, final Collection<String> names) {
		names.stream()
			.peek(name -> loggerAdapter.debug(actionName + ": " + name))
			.map(containers::get)
			.forEach(consumer);
	}

	private void onEach(final String actionName, final Consumer<Container> consumer, final String... names) {
		stream(names).peek(name -> loggerAdapter.debug(actionName + ": " + name))
			.map(containers::get)
			.forEach(consumer);
	}

	@Override
	public Container lookup(String name) {
		return containers.get(name);
	}
}
