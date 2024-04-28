package edu.ncsu.csc216.wolf_tasks.model.tasks;

import edu.ncsu.csc216.wolf_tasks.model.util.ISwapList;
import edu.ncsu.csc216.wolf_tasks.model.util.SwapList;

/**
 * Plain old Java object that represents a Task in the WolfTasks system.
 * 
 * @author Jaden Levy
 * @author Chirag Sreedhara
 */
public class Task implements Cloneable {
	/** The name of the task. */
	private String taskName;

	/** The task's description. */
	private String taskDescription;

	/**
	 * Whether the task is a recurring task (whether it should be readded to an
	 * AbstractTaskList upon completion).
	 */
	private boolean recurring;

	/**
	 * Whether the task is an active task (whether it appears on the list of active
	 * tasks).
	 */
	private boolean active;

	/**
	 * The task lists of which this Task is a member.
	 */
	private ISwapList<AbstractTaskList> taskLists;

	/**
	 * Constructs a new task, given a name, description, whether it is recurring and
	 * whether it is active. If the task name or description is null or name is an
	 * empty string, an IllegalArgumentException is thrown.
	 * 
	 * @param taskName    the name to assign to the task
	 * @param taskDetails the description to assign to the task
	 * @param recurring   whether the task is created as a recurring task
	 * @param active      whether the task is created as an active task
	 */
	public Task(String taskName, String taskDetails, boolean recurring, boolean active) {
		setTaskName(taskName);
		setTaskDescription(taskDetails);
		setRecurring(recurring);
		setActive(active);
		taskLists = new SwapList<AbstractTaskList>();
	}

	/**
	 * Gets the Task's name.
	 * 
	 * @return the name of the Task
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * Sets the name of the Task. If the Task name is null or an empty string, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param taskName the name to assign to the Task
	 * @throws IllegalArgumentException if the Task name is null or an empty string
	 */
	public void setTaskName(String taskName) {
		if (taskName == null || taskName.isEmpty()) {
			throw new IllegalArgumentException("Incomplete task information.");
		}
		this.taskName = taskName;
	}

	/**
	 * Gets the Task's description.
	 * 
	 * @return the Task's description
	 */
	public String getTaskDescription() {
		return taskDescription;
	}

	/**
	 * Sets the Task's description. If the Task description is null, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param taskDescription the description to assign to the Task
	 * @throws IllegalArgumentException if the Task name is null
	 */
	public void setTaskDescription(String taskDescription) {
		if (taskDescription == null) {
			throw new IllegalArgumentException("Incomplete task information.");
		}
		this.taskDescription = taskDescription;
	}

	/**
	 * Gets whether the Task is recurring (if it would be added to the end of the
	 * Task list(s) upon completion).
	 * 
	 * @return true if the Task is recurring, false otherwise
	 */
	public boolean isRecurring() {
		return recurring;
	}

	/**
	 * Sets the recurring status for the Task.
	 * 
	 * @param recurring true if the Task is recurring, false otherwise
	 */
	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}

	/**
	 * Gets whether the Task is an active task (whether it would be added to the
	 * Active Tasks list).
	 * 
	 * @return true if the Task is active, false otherwise
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the active status for the Task.
	 * 
	 * @param active true if the Task is active, false otherwise
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Gets the name of the first AbstractTaskList this task belongs to. If the Task
	 * is not part of any AbstractTaskLists, an empty String is returned.
	 * 
	 * @return the first AbstractTaskList this Task is a part of, or a blank String
	 *         if not part of any AbstractTaskList
	 */
	public String getTaskListName() {
		String name = "";
		if (taskLists.size() != 0 && taskLists != null) {
			name = taskLists.get(0).getTaskListName();
		}
		return name;
	}

	/**
	 * Adds the Task to the AbstractTaskList, so long as the AbstractTaskList is not
	 * already registered with the Task. If the parameter is null, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param taskList the TaskList to register with this Task
	 * @throws IllegalArgumentException if the TaskList being attempted to register
	 *                                  with this Task is null
	 */
	public void addTaskList(AbstractTaskList taskList) {
		if (taskList == null) {
			throw new IllegalArgumentException("Incomplete task information.");
		}
		boolean alreadyAdded = false;
		for (int i = 0; i < taskLists.size(); i++) {
			if (taskList.getTaskListName().equals(taskLists.get(i).getTaskListName())) {
				alreadyAdded = true;
				break;
			}
		}
		if (!alreadyAdded) {
			taskLists.add(taskList);
		}
	}

	/**
	 * Completes the Task by notifying all registered AbstractTaskLists. The Task
	 * will be removed from all registered AbstractTaskLists, the count of completed
	 * Tasks will increment by one, and if the Task is recurring, the Task will be
	 * cloned and readded to registered TaskLists.
	 * 
	 * @throws IllegalArgumentException if there are no registered TaskLists associated with the Task
	 */
	public void completeTask() {
		if (isRecurring()) {
			for (int i = 0; i < taskLists.size(); i++) {
				try {
					taskLists.get(i).addTask((Task) clone());
				} catch (CloneNotSupportedException e) {
					throw new IllegalArgumentException("No registered task lists");
				}
			}
		}
		setActive(false);
		for (int i = 0; i < taskLists.size(); i++) {
			taskLists.get(i).completeTask(this);
		}

	}

	/**
	 * Clones the Task by creating a new Task with copies of all of its fields. If
	 * there are no TaskLists registered with the Task, a CloneNotSupportedException
	 * is thrown.
	 * 
	 * @return a clone of the implicit Task with copies of all of the implicit
	 *         Task's fields
	 * @throws CloneNotSupportedException if there are no TaskLists registered with
	 *                                    this Task.
	 */
	public Object clone() throws CloneNotSupportedException {
		if (taskLists.size() == 0) {
			throw new CloneNotSupportedException("Cannot clone.");
		}
		Task cloned = new Task(getTaskName(), getTaskDescription(), isRecurring(), isActive());
		for (int i = 0; i < taskLists.size(); i++) {
			cloned.addTaskList(taskLists.get(i));
		}
		return cloned;
	}

	/**
	 * Gets a String representation of the Task that will be used for file output.
	 * 
	 * @return String representation of the Task for file output
	 */
	public String toString() {
		String returner = "* " + getTaskName();
		if (recurring && active) {
			returner += ",recurring,active\n";
		} else if (recurring) {
			returner += ",recurring\n";
		} else if (active) {
			returner += ",active\n";
		} else {
			returner += "\n";
		}
		returner += getTaskDescription();
		return returner;
	}
}
