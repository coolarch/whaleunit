package cool.arch.whaleunit.annotation;

/*
 * #%L
 * WhaleUnit - Annotation
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

import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultLoggerAdapter implements LoggerAdapter {
	
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	@Override
	public boolean isDebugEnabled() {
		return LOGGER.isLoggable(Level.FINE);
	}
	
	@Override
	public void debug(final String content) {
		LOGGER.log(Level.FINE, content);
	}
	
	@Override
	public void debug(final String content, final Throwable error) {
		LOGGER.log(Level.FINE, content, error);
	}
	
	@Override
	public boolean isInfoEnabled() {
		return LOGGER.isLoggable(Level.INFO);
	}
	
	@Override
	public void info(final String content) {
		LOGGER.log(Level.INFO, content);
	}
	
	@Override
	public void info(final String content, final Throwable error) {
		LOGGER.log(Level.INFO, content, error);
	}
	
	@Override
	public boolean isWarnEnabled() {
		return LOGGER.isLoggable(Level.WARNING);
	}
	
	@Override
	public void warn(final String content) {
		LOGGER.log(Level.WARNING, content);
	}
	
	@Override
	public void warn(final String content, final Throwable error) {
		LOGGER.log(Level.WARNING, content, error);
	}
	
	@Override
	public boolean isErrorEnabled() {
		return LOGGER.isLoggable(Level.SEVERE);
	}
	
	@Override
	public void error(final String content) {
		LOGGER.log(Level.SEVERE, content);
	}
	
	@Override
	public void error(final String content, final Throwable error) {
		LOGGER.log(Level.SEVERE, content, error);
	}
}
