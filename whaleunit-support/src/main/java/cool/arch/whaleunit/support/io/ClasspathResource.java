package cool.arch.whaleunit.support.io;

/*
 * #%L WhaleUnit - Support %% Copyright (C) 2015 CoolArch %% Licensed to the Apache Software
 * Foundation (ASF) under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. #L%
 */

import java.io.InputStream;

import cool.arch.whaleunit.support.io.exception.UnknownResourceException;

class ClasspathResource extends Resource {

	public static final String PREFIX = "classpath";

	ClasspathResource(final String path) {
		super(path);
	}

	@Override
	public final String getPrefix() {
		return PREFIX;
	}

	@Override
	protected InputStream loadInputStream() throws UnknownResourceException {
		final InputStream stream = getClass().getResourceAsStream(getNativePath());

		if (stream == null) {
			throw new UnknownResourceException("Unknown resource " + getPath());
		}

		return stream;
	}
}
