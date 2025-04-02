# 🌌 Constellation Recognition Project

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Python](https://img.shields.io/badge/Python-3.8%2B-blue)](https://python.org)
[![Java](https://img.shields.io/badge/Java-11%2B-orange)](https://java.com)

Automatic constellation recognition application for night sky observation with interactive interface.

## 👾 Features

- **Automatic recognition** of constellations via pattern matching
- **Precise extraction** of stellar coordinates
- **Visual interface** displaying identified constellations
- **Detailed information** about each constellation (history, mythology)

## 👾 Key Features

- **Multi-language Architecture**  
Python for constellation pattern recognition + Java for interactive visualization
- **Advanced Star Matching**  
Precise coordinate analysis using astrometry algorithms
- **Modern JavaFX Interface**  
Interactive sky map with constellation drawing capabilities
- **Lightweight Data Management**  
CSV-based star database for efficient processing
- **Inter-process Communication**  
Robust Python-Java data exchange via csv
- **Visual Constellation Rendering**  
Dynamic drawing of recognized star patterns with mythological information

---

### 📥 Installation (Copy/Paste)

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

## 👾 User Process

- **Star Extraction**  
A(Night Sky Photo) --> B(Star Extraction - Python)

- **Star Coordinates**  
B --> C(Stellar Coordinates)

- **Constellation Recognition**  
C --> D(Constellation Recognition - Java)

- **Display on Interface**  
D --> E(User Interface - JavaFx)  

## 👾 Structure

```markdown
![Texte alternatif](chemin/vers/image.png)
```
---
