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

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import cool.arch.whaleunit.support.io.exception.InvalidResourceException;
import cool.arch.whaleunit.support.io.exception.UnknownResourceException;

public abstract class Resource {

	private final String path;

	private final String nativePath;

	protected Resource(final String path) {
		this.path = requireNonNull(path, "path shall not be null");
		nativePath = path.substring(getPrefix().length() + 1);
	}

	public final InputStream asInputStream() throws UnknownResourceException {
		return loadInputStream();
	}

	public final String asString() throws UnknownResourceException {
		String result = null;

		try (final InputStream stream = asInputStream();
			Scanner scanner = new Scanner(stream, StandardCharsets.UTF_8.name())) {
			result = scanner.useDelimiter("\\A")
				.next();
		} catch (final IOException e) {
			throw new UnknownResourceException("Error reading resource data", e);
		}

		return result;
	}

	protected abstract InputStream loadInputStream() throws UnknownResourceException;

	protected abstract String getPrefix();

	public final String getPath() {
		return nativePath;
	}

	public final String getNativePath() {
		return nativePath;
	}

	public static Resource forPath(final String path) throws InvalidResourceException {
		Resource resource = null;

		if (path.startsWith(ClasspathResource.PREFIX + ":")) {
			resource = new ClasspathResource(path);
		}

		if (path.startsWith(FileResource.PREFIX + ":")) {
			resource = new FileResource(path);
		}

		if (resource == null) {
			throw new InvalidResourceException("Unknown resource type for path: " + path);
		}

		return resource;
	}
}
