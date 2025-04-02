# 🌌 Constellation Recognition Project

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Python](https://img.shields.io/badge/Python-3.8%2B-blue)](https://python.org)
[![Java](https://img.shields.io/badge/Java-11%2B-orange)](https://java.com)

Automatic constellation recognition application for night sky observation with interactive interface.

## ✨ Features

- **Automatic recognition** of constellations via pattern matching
- **Precise extraction** of stellar coordinates
- **Visual interface** displaying identified constellations
- **Detailed information** about each constellation (history, mythology)

## Key Features:
1. **Clearly separated code blocks** with syntax highlighting
2. **Interactive copy buttons** with state change
3. **Integrated CSS styling** for better visibility
4. **Minimal JS script** for visual feedback
5. **Distinct sections** for installation/execution/examples

### JS/CSS-free alternative (universally compatible):
```markdown
## 📥 Installation (Copy/Paste)
```

Clone the repository
```bash
git clone https://github.com/youruser/constellation-recognition.git
cd constellation-recognition
```

Install Python dependencies
```bash
pip install -r python/requirements.txt
```

Compile (from java folder)
```bash
cd java
mvn clean package
```

Run
```bash
java -jar target/constellation-recognition.jar
```
graph TD
    A[Night Sky Photo] --> B(Star Extraction - Python)
    B --> C(Stellar Coordinates)
    C --> D(Constellation Recognition - Java)
    D --> E(User Interface - Java Swing)

constellation-recognition/
├── python/                # Image processing and star extraction
│   ├── star_detection.py
│   └── requirements.txt
├── java/                  # Recognition and interface
│   ├── src/
│   │   ├── constellation/
│   │   └── ui/
│   └── pom.xml
├── data/                  # Stellar databases
├── docs/                  # Documentation
└── images/                # Examples and screenshots

---
