# Red-Black Tree Dictionary 🌳📖

This project implements a **Red-Black Tree (RBT)** based **English dictionary** application. It's developed as part of Assignment #3 for the *Data Structures* course and focuses on maintaining efficient operations through a self-balancing Binary Search Tree.

## 📘 Description

Red-Black Trees are a type of self-balancing Binary Search Tree that guarantees **O(log n)** time complexity for insertion, deletion, and search operations by maintaining specific balancing rules and color properties.

This dictionary application utilizes an RBT to ensure fast word lookups and updates while keeping the tree balanced after every operation.

---

## 🧠 Features

### 🔍 Core Tree Operations
- **Search** for a word in the dictionary.
- **Insert** a new word while maintaining tree balance using rotations and color changes.
- **Print Tree Height** – the longest path from root to leaf.
- **Print Black Height** – count of black nodes from root to any leaf.
- **Print Tree Size** – total number of nodes in the RBT.

### 📚 Dictionary Application
- **Load Dictionary** from a `dictionary.txt` file (one word per line).
- **Insert Word** with duplicate detection and automatic file update.
- **Look-up Word** with simple YES/NO response.

Each insertion prints:
- ✅ Dictionary size
- 🌲 Tree height
- ⚫ Black height

---

## 🛠️ Usage

1. Ensure you have a `dictionary.txt` file with one word per line.
2. Compile and run the program (if C/C++ or Java), or run directly if using Python.
3. Use the menu-driven interface to:
   - Insert words
   - Check existence of words
   - View tree statistics

---

## 📁 File Structure

```
📦red-black-tree-dictionary
 ┣ 📄main.py / main.c / main.java   # Source code (language dependent)
 ┣ 📄dictionary.txt                 # List of dictionary words
 ┣ 📄README.md                      # This file
```

---

