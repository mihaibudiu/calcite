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
package org.apache.calcite.adapter.mongodb;

import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rex.RexBuilder;
import org.apache.calcite.runtime.PairList;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Relational expression that uses Mongo calling convention.
 */
public interface MongoRel extends RelNode {
  void implement(Implementor implementor);

  /** Calling convention for relational operations that occur in MongoDB. */
  Convention CONVENTION = new Convention.Impl("MONGO", MongoRel.class);

  /** Callback for the implementation process that converts a tree of
   * {@link MongoRel} nodes into a MongoDB query. */
  class Implementor {
    final PairList<@Nullable String, String> list = PairList.of();
    final RexBuilder rexBuilder;
    @Nullable RelOptTable table;
    @Nullable MongoTable mongoTable;

    public Implementor(RexBuilder rexBuilder) {
      this.rexBuilder = rexBuilder;
    }

    public void add(@Nullable String findOp, String aggOp) {
      list.add(findOp, aggOp);
    }

    public void visitChild(int ordinal, RelNode input) {
      assert ordinal == 0;
      ((MongoRel) input).implement(this);
    }
  }
}
