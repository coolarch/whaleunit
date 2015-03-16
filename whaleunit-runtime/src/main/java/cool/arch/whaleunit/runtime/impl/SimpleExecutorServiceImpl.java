package cool.arch.whaleunit.runtime.impl;

/*
 * #%L
 * WhaleUnit - Runtime
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

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jvnet.hk2.annotations.Service;

import cool.arch.whaleunit.runtime.api.SimpleExecutorService;

@Service
public class SimpleExecutorServiceImpl implements SimpleExecutorService {
	
	private final ExecutorService executorService;
	
	public SimpleExecutorServiceImpl() {
		executorService = Executors.newCachedThreadPool();
	}
	
	@Override
	public boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
		return executorService.awaitTermination(timeout, unit);
	}
	
	@Override
	public void execute(final Runnable command) {
		executorService.execute(command);
	}
	
	@Override
	public <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks) throws InterruptedException {
		return executorService.invokeAll(tasks);
	}
	
	@Override
	public <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks, final long timeout, final TimeUnit unit) throws InterruptedException {
		return executorService.invokeAll(tasks, timeout, unit);
	}
	
	@Override
	public <T> T invokeAny(final Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
		return executorService.invokeAny(tasks);
	}
	
	@Override
	public <T> T invokeAny(final Collection<? extends Callable<T>> tasks, final long timeout, final TimeUnit unit) throws InterruptedException,
		ExecutionException, TimeoutException {
		return executorService.invokeAny(tasks, timeout, unit);
	}
	
	@Override
	public boolean isShutdown() {
		return executorService.isShutdown();
	}
	
	@Override
	public boolean isTerminated() {
		return executorService.isTerminated();
	}
	
	@Override
	public void shutdown() {
		executorService.shutdown();
	}
	
	@Override
	public List<Runnable> shutdownNow() {
		return executorService.shutdownNow();
	}
	
	@Override
	public <T> Future<T> submit(final Callable<T> task) {
		return executorService.submit(task);
	}
	
	@Override
	public Future<?> submit(final Runnable task) {
		return executorService.submit(task);
	}
	
	@Override
	public <T> Future<T> submit(final Runnable task, final T result) {
		return executorService.submit(task, result);
	}
}
