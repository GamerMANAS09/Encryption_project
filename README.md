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

## 🤖 AI Assistance

Parts of this project—including the design, encryption logic explanation, and README documentation—were guided and refined with the help of AI tools. This support helped streamline development and improve clarity while maintaining full manual control over the codebase.
