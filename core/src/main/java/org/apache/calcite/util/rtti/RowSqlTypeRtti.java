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
package org.apache.calcite.util.rtti;

import java.util.Arrays;
import java.util.Map;

/** Runtime type information for a ROW type. */
public class RowSqlTypeRtti extends RuntimeTypeInformation {
  private final Map.Entry<String, RuntimeTypeInformation>[] fieldNames;

  public RowSqlTypeRtti(Map.Entry<String, RuntimeTypeInformation>... fieldNames) {
    super(RuntimeSqlTypeName.ROW);
    this.fieldNames = fieldNames;
  }

  public String getTypeString()  {
    return "ROW";
  }

  // This method is used to serialize the type in Java code implementations,
  // so it should produce a computation that reconstructs the type at runtime
  @Override public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("new RowSqlTypeRtti(");
    boolean first = true;
    for (Map.Entry<String, RuntimeTypeInformation> arg : this.fieldNames) {
      if (!first) {
        builder.append(", ");
      }
      first = false;
      builder.append(arg.toString());
    }
    builder.append(")");
    return builder.toString();
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    RowSqlTypeRtti that = (RowSqlTypeRtti) o;
    return Arrays.equals(fieldNames, that.fieldNames);
  }

  @Override public int hashCode() {
    return Arrays.hashCode(fieldNames);
  }
}
