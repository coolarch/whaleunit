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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Primary configuration annotation for the WhaleUnit framework.
 * <p>
 * This annotation allows for integration of configuration data external to the test class on which this annotation is used.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface WhaleUnit {
	
	/**
	 * Resource path to the WhaleUnit configuration properties file.
	 * @return Resource path to the WhaleUnit configuration properties file
	 */
	String config() default "classpath:/cool/arch/whaleunit/runtime/config_default.properties";
	
	/**
	 * Classes that are annotated with {@link cool.arch.whaleunit.loader.annotation.Container} annotations to define containers.
	 * @return Classes that are annotated with {@link cool.arch.whaleunit.loader.annotation.Container} annotations to define containers
	 */
	Class<?>[] containersFromClasses() default {};
	
	/**
	 * Logger adapter to use with WhaleUnit.
	 * <p>
	 * The default implementation uses java.util.logging based logging.
	 * @return
	 */
	Class<? extends LoggerAdapterFactory> loggerAdapterFactory() default DefaultLoggerAdapterFactory.class;
}
