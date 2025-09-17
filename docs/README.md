# Chuck User Guide

Meet Chuck, your slightly pessimistic but reliable personal assistant chatbot inspired by everyone's favorite round-headed kid from the Peanuts comic strip! Despite his tendency to sigh and say "Good grief!" at your growing task list, Chuck is determined to help you organize and manage your tasks. Whether you have simple todos, important deadlines, or scheduled events, Chuck has got you covered - even if he'll grumble about it!

## Getting Started

Chuck supports three types of tasks:
- **Todo**: Simple tasks without any date
- **Deadline**: Tasks with a due date
- **Event**: Tasks with a start and end date

All dates should be in the format: `yyyy-MM-dd HH:mm` (e.g., `2024-12-25 14:30`)

## Managing Tasks

### Adding a Todo

Create a simple task without any date.

**Usage:** `todo <description>`

**Example:** `todo read book`

```
Rats! Another task, but I've got it covered:

[T][ ] read book

Now you have 1 tasks in the list.
```

### Adding a Deadline

Create a task with a due date.

**Usage:** `deadline <description> /by <date>`

**Example:** `deadline submit assignment /by 2024-12-25 23:59`

```
Sigh... deadlines, deadlines! But don't worry, I've added:

[D][ ] submit assignment (by: Dec 25 2024 11:59PM)

Now you have 2 tasks in the list.
```

### Adding an Event

Create a task with start and end dates.

**Usage:** `event <description> /from <start_date> /to <end_date>`

**Example:** `event team meeting /from 2024-12-20 10:00 /to 2024-12-20 11:00`

```
Good grief, your schedule is filling up! Added this event:

[E][ ] team meeting (from: Dec 20 2024 10:00AM to: Dec 20 2024 11:00AM)

Now you have 3 tasks in the list.
```

## Viewing and Managing Your Tasks

### List All Tasks

View all your tasks.

**Usage:** `list`

```
I can't stand it... here are all your tasks:

1. [T][ ] read book
2. [D][ ] submit assignment (by: Dec 25 2024 11:59PM)
3. [E][ ] team meeting (from: Dec 20 2024 10:00AM to: Dec 20 2024 11:00AM)
```

### Mark Task as Complete

Mark a task as completed.

**Usage:** `mark <task_number>`

**Example:** `mark 1`

### Unmark Task

Mark a completed task as incomplete.

**Usage:** `unmark <task_number>`

**Example:** `unmark 1`

### Delete Task

Remove a task from your list.

**Usage:** `delete <task_number>`

**Example:** `delete 2`

## Task Tagging

### Add Tags to Task

Add tags to organize your tasks.

**Usage:** `tag <task_number> <tag1,tag2,tag3>`

**Example:** `tag 2 urgent,work`

```
Added 2 tag(s) to task 2

[D][ ] submit assignment (by: Dec 25 2024 11:59PM) #urgent #work
```

### Remove Tags from Task

Remove specific tags from a task.

**Usage:** `tag <task_number> -<tag1,tag2>`

**Example:** `tag 2 -urgent`

```
Removed 1 tag(s) from task 2

[D][ ] submit assignment (by: Dec 25 2024 11:59PM) #work
```
## Finding and Filtering Tasks

### Find Tasks

Search for tasks containing specific keywords.

**Usage:** `find <search_term>`

**Example:** `find assignment`

```
Here are the matching tasks in your list:
2. [D][ ] submit assignment (by: Dec 25 2024 11:59PM)
```

### Filter Tasks by Tags

Filter tasks that have specific tags.

**Usage:** `filter <tag1,tag2>`

**Example:** `filter work,urgent`

## Data Management

### Save Tasks

Manually save your current task list.

**Usage:** `save`

### Exit Chuck

Exit the application.

**Usage:** `bye`

## Tips

- Remember to use the `save` command to save your tasks to disk - Chuck doesn't automatically save!
- Task numbers in commands refer to the position shown in the `list` command
- Date format is strict: use `yyyy-MM-dd HH:mm` format for all dates
- Tags help organize tasks and can be used with the `filter` command to quickly find related tasks