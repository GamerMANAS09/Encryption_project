# 🚀 Hybrid Java Encryption System

A work-in-progress Java project combining a custom substitution cipher with AES-GCM encryption. Designed for learning and experimentation, with potential expansion toward production-grade hybrid cryptographic systems.

---

## ✨ Features

- 🔐 Custom reversible substitution + XOR cipher
- 🛡️ AES-256 encryption in GCM mode (authenticated encryption)
- 🔄 Hybrid pipeline chaining both encryption layers
- 📄 Base64-encoded output for portability
- 🖥️ Console-based usage

---

## 📁 Project Structure

- `CustomEncryptor.java`: Implements the substitution + XOR cipher
- `AESEncryptor.java`: AES-GCM encryption using PBKDF2
- `HybridCipher.java`: Orchestrates hybrid encryption/decryption
- `TestHybrid.java`: For testing the code

---

## 🔧 How It Works

1. The input is obfuscated using `CustomEncryptor`
2. The result is encrypted again using AES-GCM via `AESEncryptor`
3. Decryption reverses the process: AES decryption → custom decryption

---

## 🚦 Usage Example

```java
HybridCipher hybrid = new HybridCipher("myStrongPassword");
String encrypted = hybrid.encrypt("Hello, world!");
String decrypted = hybrid.decrypt(encrypted);

System.out.println("Encrypted: " + encrypted);
System.out.println("Decrypted: " + decrypted);
```
---

## 🤖 AI-Assisted Learning

This project was coded with the help of AI tools to understand encryption concepts like XOR operations, data shuffling, block processing, and reversing. AI was also used to improve code clarity through better commenting and structural guidance. All implementation and logic were crafted through hands-on learning and experimentation.
