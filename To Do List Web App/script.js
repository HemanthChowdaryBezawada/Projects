// Retrieve todo from local storage or initialize an empty array
let todo = JSON.parse(localStorage.getItem("todo")) || [];
const todoInput = document.getElementById("todoInput");
const todoList = document.getElementById("todoList");
const todoCount = document.getElementById("todoCount");
const addButton = document.querySelector(".btn");
const deleteButton = document.getElementById("deleteButton");
const themeToggle = document.getElementById("themeToggle");

// Initialize
document.addEventListener("DOMContentLoaded", function () {
  addButton.addEventListener("click", addTask);
  todoInput.addEventListener("keydown", function (event) {
    if (event.key === "Enter") {
      event.preventDefault(); // Prevents default Enter key behavior
      addTask();
    }
  });
  deleteButton.addEventListener("click", deleteAllTasks);
  displayTasks();

  // Load theme from localStorage
  if (localStorage.getItem("theme") === "dark") {
    document.body.classList.add("dark-mode");
    themeToggle.innerHTML = '<i data-feather="sun"></i>';
    feather.replace();
  }

  themeToggle.addEventListener("click", () => {
    document.body.classList.toggle("dark-mode");
    const isDark = document.body.classList.contains("dark-mode");
    themeToggle.innerHTML = isDark
      ? '<i data-feather="sun"></i>'
      : '<i data-feather="moon"></i>';
    feather.replace();
    localStorage.setItem("theme", isDark ? "dark" : "light");
  });
});

function addTask() {
  const newTask = todoInput.value.trim();
  if (newTask !== "") {
    todo.push({ text: newTask, disabled: false });
    saveToLocalStorage();
    todoInput.value = "";
    displayTasks();
  }
}

function displayTasks() {
  todoList.innerHTML = "";

  if (todo.length === 0) {
    const emptyMsg = document.createElement("li");
    emptyMsg.className = "empty-state";
    emptyMsg.textContent = "🎉 No tasks yet! Add your first todo.";
    todoList.appendChild(emptyMsg);
    todoCount.textContent = 0;
    return;
  }

  todo.forEach((item, index) => {
    const li = document.createElement("li");
    li.className = "todo-item" + (item.disabled ? " completed" : "");
    li.setAttribute("tabindex", "0");
    li.setAttribute("aria-checked", item.disabled);

    // Checkbox
    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.className = "todo-checkbox";
    checkbox.id = `input-${index}`;
    checkbox.checked = item.disabled;
    checkbox.setAttribute("aria-label", "Mark as completed");
    checkbox.addEventListener("change", () => toggleTask(index));

    // Task text
    const span = document.createElement("span");
    span.className = "todo-text" + (item.disabled ? " disabled" : "");
    span.textContent = item.text;
    span.title = "Double-click to edit";
    span.addEventListener("dblclick", () => editTask(index));

    // Delete button
    const delBtn = document.createElement("button");
    delBtn.className = "icon-btn delete-btn";
    delBtn.setAttribute("aria-label", "Delete task");
    delBtn.innerHTML = `<i data-feather="trash-2"></i>`;
    delBtn.addEventListener("click", () => deleteTask(index));

    li.appendChild(checkbox);
    li.appendChild(span);
    li.appendChild(delBtn);

    // Animation
    li.style.animation = "fadeIn 0.4s";

    todoList.appendChild(li);
  });

  todoCount.textContent = todo.length;
  feather.replace();
}

function editTask(index) {
  const li = todoList.children[index];
  const span = li.querySelector(".todo-text");
  const input = document.createElement("input");
  input.type = "text";
  input.value = todo[index].text;
  input.className = "edit-input";
  input.addEventListener("keydown", function (e) {
    if (e.key === "Enter") input.blur();
    if (e.key === "Escape") displayTasks();
  });
  input.addEventListener("blur", function () {
    const updatedText = input.value.trim();
    if (updatedText) {
      todo[index].text = updatedText;
      saveToLocalStorage();
    }
    displayTasks();
  });
  span.replaceWith(input);
  input.focus();
}

function toggleTask(index) {
  todo[index].disabled = !todo[index].disabled;
  saveToLocalStorage();
  displayTasks();
}

function deleteTask(index) {
  todo.splice(index, 1);
  saveToLocalStorage();
  displayTasks();
}

function deleteAllTasks() {
  todo = [];
  saveToLocalStorage();
  displayTasks();
}

function saveToLocalStorage() {
  localStorage.setItem("todo", JSON.stringify(todo));
}
