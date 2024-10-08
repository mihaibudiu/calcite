/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.calcite.linq4j.test;

import org.apache.calcite.linq4j.Linq4j;
import org.apache.calcite.linq4j.Lookup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link Lookup} and {@code LookupImpl}.
 */
class LookupImplTest {

  private Lookup<Integer, String> impl;

  @BeforeEach
  public void setUp() {
    impl =
        Linq4j.asEnumerable(Linq4jTest.emps)
            .toLookup(Linq4jTest.EMP_DEPTNO_SELECTOR,
                Linq4jTest.EMP_NAME_SELECTOR);
  }

  @Test void testPut() {
    int initSize = impl.size();
    impl.put(99, Linq4j.asEnumerable(new String[]{"A", "B"}));
    assertTrue(impl.containsKey(99));
    assertThat(impl.size() - 1, is(initSize));
  }

  @Test void testContainsValue() {
    List<String> list = new ArrayList<>();
    list.add("C");
    list.add("D");
    List<String> list2 = new ArrayList<>(list);
    impl.put(100, Linq4j.asEnumerable(list));
    assertTrue(impl.containsValue(list));
    assertTrue(impl.containsValue(list2));
  }
}
