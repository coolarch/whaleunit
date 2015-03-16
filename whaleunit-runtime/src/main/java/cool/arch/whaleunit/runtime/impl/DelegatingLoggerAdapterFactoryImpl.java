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

import static java.util.Objects.requireNonNull;
import cool.arch.whaleunit.annotation.Logger;
import cool.arch.whaleunit.annotation.LoggerAdapterFactory;
import cool.arch.whaleunit.runtime.api.DelegatingLoggerAdapterFactory;

public class DelegatingLoggerAdapterFactoryImpl implements DelegatingLoggerAdapterFactory {
	
	private LoggerAdapterFactory loggerAdapterFactory;
	
	@Override
	public Logger create(final Class<?> source) {
		return new LazyCreationDelegatingLoggerAdapter(source);
	}
	
	@Override
	public void setLoggerAdapterFactory(final LoggerAdapterFactory loggerAdapterFactory) {
		this.loggerAdapterFactory = requireNonNull(loggerAdapterFactory, "loggerAdapterFactory shall not be null");
	}
	
	public LoggerAdapterFactory getLoggerAdapterFactory() {
		return loggerAdapterFactory;
	}
	
	class LazyCreationDelegatingLoggerAdapter implements Logger {
		
		private final Class<?> source;
		
		private Logger adapter;
		
		private final Object lock = new Object();
		
		LazyCreationDelegatingLoggerAdapter(final Class<?> source) {
			this.source = requireNonNull(source, "source shall not be null");
		}
		
		@Override
		public boolean isDebugEnabled() {
			return getAdapter().isDebugEnabled();
		}
		
		@Override
		public void debug(final String content) {
			getAdapter().debug(content);
		}
		
		@Override
		public void debug(final String content, final Throwable error) {
			getAdapter().debug(content, error);
		}
		
		@Override
		public boolean isInfoEnabled() {
			return getAdapter().isInfoEnabled();
		}
		
		@Override
		public void info(final String content) {
			getAdapter().info(content);
		}
		
		@Override
		public void info(final String content, final Throwable error) {
			getAdapter().info(content, error);
		}
		
		@Override
		public boolean isWarnEnabled() {
			return getAdapter().isWarnEnabled();
		}
		
		@Override
		public void warn(final String content) {
			getAdapter().warn(content);
		}
		
		@Override
		public void warn(final String content, final Throwable error) {
			getAdapter().warn(content, error);
		}
		
		@Override
		public boolean isErrorEnabled() {
			return getAdapter().isErrorEnabled();
		}
		
		@Override
		public void error(final String content) {
			getAdapter().error(content);
		}
		
		@Override
		public void error(final String content, final Throwable error) {
			getAdapter().error(content, error);
		}
		
		public Logger getAdapter() {
			
			synchronized (lock) {
				if (adapter == null) {
					adapter = getLoggerAdapterFactory().create(source);
				}
			}
			
			return adapter;
		}
	}
}
