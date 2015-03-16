package cool.arch.whaleunit.runtime.service.impl;

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

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.jvnet.hk2.annotations.Service;

import cool.arch.whaleunit.api.exception.TestManagementException;
import cool.arch.whaleunit.runtime.service.api.MutableConfigService;
import cool.arch.whaleunit.support.io.Resource;
import cool.arch.whaleunit.support.io.exception.InvalidResourceException;
import cool.arch.whaleunit.support.io.exception.UnknownResourceException;

@Service
public class ConfigServiceImpl implements MutableConfigService {
	
	private static final String BASE_CONFIG_PATH = "classpath:/cool/arch/whaleunit/runtime/config_base.properties";
	
	private final Map<String, String> values = new ConcurrentHashMap<>();
	
	public ConfigServiceImpl() {
		addConfigFile(BASE_CONFIG_PATH);
	}
	
	@Override
	public void addConfigFile(final String resourcePath) {
		try (final InputStream inputStream = Resource.forPath(BASE_CONFIG_PATH).asInputStream()) {
			final Properties properties = new Properties();
			properties.load(inputStream);
			properties.forEach((k, v) -> values.put(k.toString(), v.toString()));
		} catch (final IOException e) {
			throw new TestManagementException("Error loading resource " + resourcePath, e);
		} catch (final UnknownResourceException e) {
			throw new TestManagementException("Unknown resource " + resourcePath, e);
		} catch (final InvalidResourceException e) {
			throw new TestManagementException("Invalid resource " + resourcePath, e);
		}
	}
	
	@Override
	public Optional<String> getValue(final String key) {
		return Optional.ofNullable(values.get(key));
	}
}
