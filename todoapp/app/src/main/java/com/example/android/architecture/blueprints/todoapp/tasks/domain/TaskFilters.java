/*
 * -\-\-
 * --
 * Copyright (c) 2017-2018 Spotify AB
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */
package com.example.android.architecture.blueprints.todoapp.tasks.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import com.example.android.architecture.blueprints.todoapp.data.Task;
import com.google.common.collect.ImmutableList;
import io.reactivex.Observable;
import io.reactivex.functions.Predicate;
import java.util.HashMap;
import java.util.List;

public class TaskFilters {
  private static final HashMap<TasksFilterType, Predicate<Task>> FILTERS;

  static {
    FILTERS = new HashMap<>();
    FILTERS.put(TasksFilterType.ALL_TASKS, t -> true);
    FILTERS.put(TasksFilterType.ACTIVE_TASKS, t -> !t.details().completed());
    FILTERS.put(TasksFilterType.COMPLETED_TASKS, t -> t.details().completed());
  }

  public static ImmutableList<Task> filterTasks(List<Task> tasks, TasksFilterType filter) {
    return Observable.fromIterable(checkNotNull(tasks))
        .filter(FILTERS.get(filter))
        .toList()
        .map(ImmutableList::copyOf)
        .blockingGet();
  }
}
