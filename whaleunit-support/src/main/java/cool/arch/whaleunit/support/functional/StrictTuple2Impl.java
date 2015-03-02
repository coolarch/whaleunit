package cool.arch.whaleunit.support.functional;

/*
 * #%L
 * WhaleUnit - Support
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

class StrictTuple2Impl<V0, V1> implements Tuple2<V0, V1> {
	
	private final V0 v0;
	
	private final V1 v1;
	
	StrictTuple2Impl(final V0 v0, final V1 v1) {
		this.v0 = v0;
		this.v1 = v1;
	}
	
	@Override
	public V0 item0() {
		return v0;
	}
	
	@Override
	public V1 item1() {
		return v1;
	}
}
